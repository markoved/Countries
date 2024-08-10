package com.markoved.countries.data.mapper

import com.markoved.countries.data.local.LocalCountry
import com.markoved.countries.domain.entity.Country

class LocalCountryToDomainMapper: (LocalCountry) -> Country {
    override fun invoke(remoteCountry: LocalCountry): Country = Country(
        commonName = remoteCountry.commonName,
        officialName = remoteCountry.officialName,
        flagUrl = remoteCountry.flagUrl,
        population = remoteCountry.population,
        capitals = remoteCountry.capitals
    )
}
