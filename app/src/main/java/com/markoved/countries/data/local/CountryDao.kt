package com.markoved.countries.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountryDao {

    @Query("SELECT * FROM localcountry")
    fun getAll(): List<LocalCountry>

    @Query("SELECT * FROM localcountry WHERE " +
            "common_name LIKE '%' || :name || '%' OR " +
            "official_name LIKE '%' || :name || '%'")
    fun findByName(name: String): List<LocalCountry>

    @Insert
    fun insertAll(countries: List<LocalCountry>)

    @Query("DELETE FROM localcountry")
    fun clear()
}