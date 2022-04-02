package com.tym.tparse.exercise

import org.junit.Ignore
import org.junit.Test
import com.tym.tparse.DateFormat
import com.tym.tparse.deserialization.deserialize
import com.tym.tparse.serialization.serialize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertEquals

data class Person(
        val name: String,
        @DateFormat("dd-MM-yyyy") val birthDate: Date
)

@Ignore
class DateFormatTest {
    private val value = Person("Alice", SimpleDateFormat("dd-MM-yyyy").parse("13-02-1987"))
    private val json = """{"birthDate": "13-02-1987", "name": "Alice"}"""

    @Test fun testSerialization() {
        assertEquals(json, serialize(value))
    }

    @Test fun testDeserialization() {
        assertEquals(value, deserialize(json))
    }
}
