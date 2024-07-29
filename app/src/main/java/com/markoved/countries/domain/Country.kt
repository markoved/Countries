package com.markoved.countries.domain

data class Country (
    val commonName: String,
    val officialName: String,
    val flagUrl: String,
    val population: Int,
    val capitals: List<String>
)