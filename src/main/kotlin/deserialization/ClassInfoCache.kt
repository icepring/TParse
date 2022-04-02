package com.tym.tparse.deserialization

import com.tym.tparse.*
import com.tym.tparse.serialization.getSerializer
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaType

class ClassInfoCache {


    private val cacheData = mutableMapOf<KClass<*>, ClassInfo<*>>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(cls: KClass<T>): ClassInfo<T> =
        cacheData.getOrPut(cls) { ClassInfo(cls) } as ClassInfo<T>
}

class ClassInfo<T : Any>(cls: KClass<T>) {
    private val className = cls.qualifiedName
    private var constructor = cls.primaryConstructor

    private val jsonNameToParamMap = hashMapOf<String, KParameter?>()
    private val paramToSerializerMap = hashMapOf<KParameter?, ValueSerializer<out Any?>>()
    private val jsonNameToDeserializeClassMap = hashMapOf<String, Class<out Any>?>()

    init {
        if (constructor == null) {
            val constructors = cls.constructors
            constructor = constructors.first()
        }

        cls.declaredMemberProperties.forEach {
            cacheDataForParameter(cls, it)
        }
    }

    private fun cacheDataForParameter(cls: KClass<*>, param: KProperty1<T, *>) {
        val paramName = param.name

        val name = param.findAnnotation<HeadKey>()?.key
            ?: param.findAnnotation<BodyKey>()?.key
            ?: paramName

        val index = cls.declaredMemberProperties.indexOf(param)
        jsonNameToParamMap[name] = constructor?.parameters?.get(index)

        val deserializeClass = param.findAnnotation<DeserializeInterface>()?.targetClass?.java
        jsonNameToDeserializeClassMap[name] = deserializeClass

        val valueSerializer = param.getSerializer()
            ?: serializerForType(param.javaClass)
            ?: return
        paramToSerializerMap[constructor?.parameters?.get(index)] = valueSerializer
    }

    fun getConstructorParameter(propertyName: String): KParameter? = jsonNameToParamMap[propertyName].apply {
        this ?: println("Constructor parameter $propertyName is not found for class $className")
    }

    fun getDeserializeClass(propertyName: String) = jsonNameToDeserializeClassMap[propertyName]

    fun deserializeConstructorArgument(param: KParameter, value: Any?): Any? {
        val serializer = paramToSerializerMap[param]
        if (serializer != null) return serializer.fromJsonValue(value)

        validateArgumentType(param, value)
        return value
    }

    private fun validateArgumentType(param: KParameter, value: Any?) {
        if (value == null && !param.type.isMarkedNullable) {
            throw TParseException("Received null value for non-null parameter ${param.name}")
        }
        if (value != null && value.javaClass != param.type.javaType) {
            throw TParseException(
                "Type mismatch for parameter ${param.name}: " +
                        "expected ${param.type.javaType}, found ${value.javaClass}"
            )
        }
    }

    fun createInstance(arguments: Map<KParameter, Any?>): T {
        ensureAllParametersPresent(arguments)
        return constructor!!.callBy(arguments)
    }

    private fun ensureAllParametersPresent(arguments: Map<KParameter, Any?>) {
        for (param in constructor!!.parameters) {
            if (arguments[param] == null && !param.isOptional && !param.type.isMarkedNullable) {
                throw TParseException("Missing value for parameter ${param.name}")
            }
        }
    }
}

class TParseException(message: String) : Exception(message)
