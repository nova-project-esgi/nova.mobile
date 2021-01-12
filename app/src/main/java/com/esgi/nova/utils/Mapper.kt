package com.esgi.nova.utils

import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

inline fun <reified In: Any, reified Out: Any> In.reflectMap() = with(Out::class.primaryConstructor){
    val propertiesByName = In::class.memberProperties.associateBy { it.name }
    this?.callBy(parameters.associateWith { parameter ->
        propertiesByName[parameter.name]?.get(this@reflectMap)
    })
}

inline fun <reified In: Any, reified Out: Any> In.reflectMapNotNull() = this.reflectMap<In, Out>()!!

inline fun <reified In: Any, reified Out: Any> Collection<In>.reflectMapCollection(): Collection<Out>{
    val outCtr = Out::class.primaryConstructor
    val propertiesByName= In::class.memberProperties.associateBy { it.name }
    return this.mapNotNull {item ->
        with(outCtr){
            this?.callBy(parameters.associateWith { parameter ->
                propertiesByName[parameter.name]?.get(item)
            })
        }
    }
}

