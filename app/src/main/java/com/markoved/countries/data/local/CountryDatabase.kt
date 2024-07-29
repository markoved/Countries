package com.markoved.countries.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalCountry::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}