package com.markoved.countries.data.entity

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("name")
    val name: Name,

    @SerializedName("flags")
    val flag: Flag,

    @SerializedName("capital")
    val capitals: List<String>,

    @SerializedName("population")
    val population: Int
)

data class Flag(
    @SerializedName("png")
    val png: String
)

data class Name(

    @SerializedName("common")
    val common: String,

    @SerializedName("official")
    val official: String
)
