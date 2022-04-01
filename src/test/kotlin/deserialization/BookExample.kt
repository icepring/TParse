package deserialization

import kotlin.tym.tparse.BodyKey
import kotlin.tym.tparse.HeadKey
import kotlin.tym.tparse.deserialization.deserialize

data class Author(@HeadKey("H.1") val name: String ,@BodyKey("B.1") val age:Int)
data class Book(val title: String, val author: Author)

fun main(args: Array<String>) {
    val json = """{"title": "Catch-22", "author": {"name": "J. Heller"}}"""
    val tele = """H.1=250
        |B.1=12
    """.trimMargin()
    println(deserialize<Author>(tele))
}
