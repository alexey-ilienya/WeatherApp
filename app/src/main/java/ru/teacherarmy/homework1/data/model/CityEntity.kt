package ru.teacherarmy.homework1.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity (
    @PrimaryKey(autoGenerate = true) var id: Int,

    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?
)