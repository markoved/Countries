package com.markoved.countries.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalCountry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "common_name") val commonName: String,
    @ColumnInfo(name = "official_name") val officialName: String,
    @ColumnInfo(name = "flag_url") val flagUrl: String,
    @ColumnInfo(name = "population") val population: Int,
    @ColumnInfo(name = "capitals") val capitals: List<String>
)
