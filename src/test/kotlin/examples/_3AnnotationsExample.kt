package kotlin.tym.tparse.examples.annotationsTest

import org.junit.Test
import kotlin.tym.tparse.JsonExclude
import kotlin.tym.tparse.JsonName
import kotlin.tym.tparse.examples.jsonSerializerTest.testJsonSerializer

data class Person(
        @JsonName(name = "first_name") val firstName: String,
        @JsonExclude val age: Int? = null
)

class AnnotationsTest {
    @Test fun test() = testJsonSerializer(
            value = Person("Alice"),
            json = """{"first_name": "Alice"}"""
    )
}