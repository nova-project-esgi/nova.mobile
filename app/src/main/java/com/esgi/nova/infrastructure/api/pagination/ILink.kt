package com.esgi.nova.infrastructure.api.pagination

import com.google.gson.annotations.SerializedName

interface ILink {
    val rel: Relation
    val href: String
    val method: String

    enum class Relation(name: String) {
        @SerializedName("previous")
        PREVIOUS("PREVIOUS"),

        @SerializedName("current")
        CURRENT("CURRENT"),

        @SerializedName("next")
        NEXT("NEXT"),

        @SerializedName("asset")
        ASSET("ASSET")
    }

}