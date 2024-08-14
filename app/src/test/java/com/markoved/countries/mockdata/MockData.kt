package com.markoved.countries.mockdata

import com.markoved.countries.data.network.Flag
import com.markoved.countries.data.network.Name
import com.markoved.countries.data.network.RemoteCountry
import com.markoved.countries.domain.entity.Country

val getAllCountriesMockData = listOf(
    Country(
        commonName = "France",
        officialName = "French Republic",
        flagUrl = "",
        population = 67391582,
        capitals = listOf("Paris")
    ),
    Country(
        commonName = "Germany",
        officialName = "Federal Republic of Germany",
        flagUrl = "",
        population = 83240525,
        capitals = listOf("Berlin")
    )
)

val searchCountriesMockData = listOf(
    Country(
        commonName = "Germany",
        officialName = "Federal Republic of Germany",
        flagUrl = "",
        population = 83240525,
        capitals = listOf("Berlin")
    )
)

val remoteCountriesMockData = listOf(
    RemoteCountry(
        name = Name(
            common = "France",
            official = "French Republic"
        ),
        flag = Flag(""),
        population = 67391582,
        capitals = listOf("Paris")
    ),
    RemoteCountry(
        name = Name(
            common = "Germany",
            official = "Federal Republic of Germany"
        ),
        flag = Flag(""),
        population = 83240525,
        capitals = listOf("Berlin")
    )
)
