package ru.teacherarmy.homework1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.teacherarmy.homework1.data.db.dao.CityDao
import ru.teacherarmy.homework1.data.model.CityEntity

@Database(entities = [CityEntity::class], version = 1)
abstract class CityDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}
