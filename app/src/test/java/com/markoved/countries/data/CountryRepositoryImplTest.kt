package com.markoved.countries.data

import com.markoved.countries.data.local.CountryLocalDataSource
import com.markoved.countries.data.mapper.LocalCountryToDomainMapper
import com.markoved.countries.data.mapper.RemoteCountryToDomainMapper
import com.markoved.countries.data.mapper.RemoteCountryToLocalCountryMapper
import com.markoved.countries.data.network.CountryRemoteDataSource
import com.markoved.countries.mockdata.remoteCountriesMockData
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

class CountryRepositoryImplTest {

    private val localDataSourceMock = mock<CountryLocalDataSource>()
    private val remoteDataSourceMock = mock<CountryRemoteDataSource>()
    private val remoteCountryToDomainMapper = RemoteCountryToDomainMapper()
    private val localCountryToDomainMapper = LocalCountryToDomainMapper()
    private val remoteCountryToLocalCountryMapper = RemoteCountryToLocalCountryMapper()

    private val repository = CountryRepositoryImpl(
        localDataSourceMock,
        remoteDataSourceMock,
        remoteCountryToDomainMapper,
        localCountryToDomainMapper,
        remoteCountryToLocalCountryMapper
    )

    @Test
    fun getAllCountries_remoteDataSourceReturnsNonEmptyResult_localDataUpdated() = runTest {
        whenever(remoteDataSourceMock.getAllCountries()).thenReturn(remoteCountriesMockData)
        repository.getAllCountries()
        verify(remoteDataSourceMock).getAllCountries()
        verify(localDataSourceMock).insertAllCountries(any())
        verify(localDataSourceMock, never()).getAllCountries()
    }

    @Test
    fun getAllCountries_remoteDataSourceReturnsEmptyResult_localDataReturned() = runTest {
        whenever(remoteDataSourceMock.getAllCountries()).thenReturn(emptyList())
        whenever(localDataSourceMock.getAllCountries()).thenReturn(emptyList())
        repository.getAllCountries()
        verify(remoteDataSourceMock).getAllCountries()
        verify(localDataSourceMock).getAllCountries()
        verify(localDataSourceMock, never()).insertAllCountries(any())
    }

    @Test
    fun getCountriesByName_remoteDataSourceReturnsNonEmptyResult_remoteDataReturned() = runTest {
        val name = "Swe"
        whenever(remoteDataSourceMock.getCountries(any())).thenReturn(remoteCountriesMockData)
        repository.getCountriesByName(name)
        verify(remoteDataSourceMock).getCountries(name)
        verifyNoInteractions(localDataSourceMock)
    }

    @Test
    fun getCountriesByName_remoteDataSourceReturnsEmptyResult_localDataReturned() = runTest {
        val name = "Nor"
        whenever(remoteDataSourceMock.getCountries(any())).thenReturn(emptyList())
        whenever(localDataSourceMock.getCountriesByName(any())).thenReturn(emptyList())
        repository.getCountriesByName(name)
        verify(localDataSourceMock).getCountriesByName(name)
    }
}
