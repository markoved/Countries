package com.markoved.countries.data.entity

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("name")
    val name: Name
)

data class Name(

    @SerializedName("common")
    val common: String,

    @SerializedName("official")
    val official: String
)
