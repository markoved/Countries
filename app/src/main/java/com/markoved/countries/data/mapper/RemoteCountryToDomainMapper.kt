package com.markoved.countries.data.mapper

import com.markoved.countries.data.network.RemoteCountry
import com.markoved.countries.domain.Country

class RemoteCountryToDomainMapper: (RemoteCountry) -> Country {
    override fun invoke(remoteCountry: RemoteCountry): Country = Country(
        commonName = remoteCountry.name.common,
        officialName = remoteCountry.name.official,
        flagUrl = remoteCountry.flag.png,
        population = remoteCountry.population,
        capitals = remoteCountry.capitals ?: emptyList()
    )
}