package com.markoved.countries.domain.entity

data class Country (
    val commonName: String,
    val officialName: String,
    val flagUrl: String,
    val population: Int,
    val capitals: List<String>
)
