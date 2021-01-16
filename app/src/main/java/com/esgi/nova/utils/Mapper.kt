package com.esgi.nova.utils

import com.google.common.base.Defaults
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

inline fun <reified In : Any, reified Out : Any> In.reflectMap() =
    with(Out::class.primaryConstructor) {
        val propertiesByName = In::class.memberProperties.associateBy { it.name }
        callCtrByItem(propertiesByName, this@reflectMap)
    }

inline fun <reified In: Any, reified Out: Any> In.reflectMapNotNull() = this.reflectMap<In, Out>()!!

inline fun <reified In : Any, reified Out : Any> Collection<In>.reflectMapCollection(): Collection<Out> {
    val outCtr = Out::class.primaryConstructor
    val propertiesByName = In::class.memberProperties.associateBy { it.name }
    return this.mapNotNull { item ->
        with(outCtr) {
            callCtrByItem(propertiesByName, item)
        }
    }
}

inline fun <reified In : Any, reified Out : Any> KFunction<Out>?.callCtrByItem(
    propertiesByName: Map<String, KProperty1<In, *>>,
    item: In
): Out? {
    return this?.callBy(parameters.associateWith { parameter ->
        propertiesByName[parameter.name]?.let { property ->
            return@associateWith property.get(item)
        }
        if (!parameter.type.isMarkedNullable) {
            return@associateWith defaultByTypeName(parameter.type)
        } else null

    })
}

inline fun <reified In : Any, reified Out : Any> Array<In>.reflectMapArray(): Array<Out> {
    val outCtr = Out::class.primaryConstructor
    val propertiesByName = In::class.memberProperties.associateBy { it.name }
    return this.mapNotNull { item ->
        with(outCtr) {
            callCtrByItem(propertiesByName, item)
        }
    }.toTypedArray()
}

fun defaultByTypeName(type: KType): Any? {
    val typeName = type.toString()
    return when (typeName.replace("kotlin.","")) {
        "Boolean" -> false
        "String" -> ""
        else -> {
            return if (typeName == "Int" || typeName == "Float" || typeName == "Double" || typeName ==
                "Char" || typeName == "Byte" || typeName == "Short" || typeName == "Long"
            ){
                0
            } else {
                null
            }
        }
    }

}



