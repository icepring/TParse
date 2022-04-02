package com.tym.tparse.examples.jsonSerializerTest

import com.tym.tparse.deserialization.deserialize
import com.tym.tparse.serialization.serialize
import kotlin.test.assertEquals

inline fun <reified T: Any> testJsonSerializer(value: T, json: String) {

    assertEquals(json, serialize(value))

    assertEquals(value, deserialize(json))
}