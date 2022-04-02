package deserialization

import com.tym.tparse.ValueSerializer

object BooleanSerializer : ValueSerializer<Boolean> {
    override fun toJsonValue(value: Boolean): Any? {
        return if (value) 0 else 1
    }

    override fun fromJsonValue(jsonValue: Any?): Boolean {
        return (jsonValue as String).toInt() != 0
    }
}