package com.tym.tparse.deserialization

import java.io.BufferedReader
import java.io.Reader

class Parser(reader: Reader, val rootObject: JsonObject) {
    private var l: JsonObject? = null
    private lateinit var propertyName: String
    private val lexer = Lexer(reader)
    private val lineLexer = LineLexer(BufferedReader(reader))
    private var listFlag = mutableMapOf<String, JsonObject?>()

    fun parse() {
        expect(Token.LBRACE)
        parseObjectBody(rootObject)
        if (lexer.nextToken() != null) {
            throw IllegalArgumentException("Too many tokens")
        }
    }

    fun parseTele() {
        parseLine(rootObject)
    }

    private fun parseObjectBody(jsonObject: JsonObject?) {
        parseCommaSeparated(Token.RBRACE) { token ->
            if (token !is Token.StringValue) {
                throw MalformedException("Unexpected token $token")
            }

            val propName = token.value
            expect(Token.COLON)
            parsePropertyValue(jsonObject, propName, nextToken())
        }
    }

    private fun parseArrayBody(currentObject: JsonObject?, propName: String) {
        parseCommaSeparated(Token.RBRACKET) { token ->
            parsePropertyValue(currentObject, propName, token)
        }
    }

    private fun parseCommaSeparated(stopToken: Token, body: (Token) -> Unit) {
        var expectComma = false
        while (true) {
            var token = nextToken()
            if (token == stopToken) return
            if (expectComma) {
                if (token != Token.COMMA) throw MalformedException("Expected comma")
                token = nextToken()
            }

            body(token)

            expectComma = true
        }
    }

    private fun parsePropertyValue(currentObject: JsonObject?, propName: String, token: Token) {
        when (token) {
            is Token.ValueToken ->
                currentObject?.setSimpleProperty(propName, token.value)

            Token.LBRACE -> {
                val childObj = currentObject?.createObject(propName)
                parseObjectBody(childObj)
            }

            Token.LBRACKET -> {
                val childObj = currentObject?.createArray(propName)
                parseArrayBody(childObj, propName)
            }

            else ->
                throw MalformedException("Unexpected token $token")
        }
    }

    private fun parseLine(jsonObject: JsonObject) {
        parseLine { token ->
            if (token.size < 2) {
                throw MalformedException("Unexpected token size ${token.size}")
            }
            parsePropertyValue(jsonObject, token)
        }
    }

    private fun parseLine(body: (List<Token.StringValue>) -> Unit) {
        while (true) {
            val token: List<Token.StringValue>? = nextTToken()
            if (token == null) {
                listFlag.clear()
                return
            }
            body(token)
        }
    }

    private fun parsePropertyValue(currentObject: JsonObject, token: List<Token.StringValue>) {

        if (listFlag.isNotEmpty()) {
            listFlag.forEach { (_, u) ->
                val propertyName = token[0].value.substring(0, token[0].value.indexOfFirst { it.toString() == "_" })
                if (this.propertyName == propertyName) {
                    val l = u?.createObject(token[0].value)
                    l?.setSimpleProperty(propertyName, token[1].value)
                    this.l = l
                } else {
                    this.l?.setSimpleProperty(propertyName, token[1].value)
                }
            }
        }

        if (token[0].value == "B.1_0") {
            val childObj = currentObject.createArray("B.")
            listFlag[token[0].value] = childObj

            l = childObj?.createObject(token[0].value)
            propertyName = token[0].value.substring(0, token[0].value.indexOfFirst { it.toString() == "_" })
            l?.setSimpleProperty(propertyName, token[1].value)
        }

        currentObject.setSimpleProperty(token[0].value, token[1].value)
    }

    private fun expect(token: Token) {
        if (lexer.nextToken() != token) {
            throw IllegalArgumentException("$token expected")
        }
    }

    private fun nextToken(): Token = lexer.nextToken() ?: throw IllegalArgumentException("Premature end of data")
    private fun nextTToken(): List<Token.StringValue>? = lineLexer.nextToken()
}
