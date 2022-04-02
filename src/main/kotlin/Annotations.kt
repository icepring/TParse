package com.tym.tparse

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class JsonExclude

@Target(AnnotationTarget.PROPERTY)
annotation class JsonName(val name: String)

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class HeadKey(val key: String)

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class BodyKey(val key: String)

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class Exclude()

interface ValueSerializer<T> {
    fun toJsonValue(value: T): Any?
    fun fromJsonValue(jsonValue: Any?): T
}

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class DeserializeInterface(val targetClass: KClass<out Any>)

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class CustomSerializer(val serializerClass: KClass<out ValueSerializer<*>>)