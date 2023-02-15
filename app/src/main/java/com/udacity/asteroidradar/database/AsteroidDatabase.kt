package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.model.Asteroid

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao

    companion object {
        @Volatile
        private var instance: AsteroidDatabase? = null
        fun getInstance(context: Context): AsteroidDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AsteroidDatabase::class.java,
                    "asteroid_database"
                ).build()
            }.also { instance = it }
        }
    }
}