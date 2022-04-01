package kotlin.tym.tparse.examples.jsonSerializerTest

import kotlin.tym.tparse.deserialization.deserialize
import kotlin.tym.tparse.serialization.serialize
import kotlin.test.assertEquals

inline fun <reified T: Any> testJsonSerializer(value: T, json: String) {

    assertEquals(json, serialize(value))

    assertEquals(value, deserialize(json))
}