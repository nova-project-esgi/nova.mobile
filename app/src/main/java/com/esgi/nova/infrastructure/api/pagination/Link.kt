package com.esgi.nova.infrastructure.api.pagination

data class Link( val rel: Relation, val href: String,  val method: String){

    enum class Relation {
        PREVIOUS,
        CURRENT,
        NEXT,
        ASSET
    }

}