package com.tym.tparse.examples.simple

import org.junit.Test
import com.tym.tparse.deserialization.deserialize
import com.tym.tparse.serialization.serialize
import kotlin.test.assertEquals

data class Person(val name: String, val age: Int)

class PersonTest {
    @Test fun test() {
        val person = Person("Alice", 29)
        val json = """{"age": 29, "name": "Alice"}"""

        assertEquals(json, serialize(person))
        assertEquals(person, deserialize(json))
    }
}