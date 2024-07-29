package com.markoved.countries.data.mapper

import com.markoved.countries.data.local.LocalCountry
import com.markoved.countries.data.network.RemoteCountry

class RemoteCountryToLocalCountryMapper: (RemoteCountry) -> LocalCountry {
    override fun invoke(remoteCountry: RemoteCountry): LocalCountry = LocalCountry(
        commonName = remoteCountry.name.common,
        officialName = remoteCountry.name.official,
        flagUrl = remoteCountry.flag.png,
        population = remoteCountry.population,
        capitals = remoteCountry.capitals ?: emptyList()
    )
}