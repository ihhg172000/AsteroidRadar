package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.util.getToday
import com.udacity.asteroidradar.util.getWeekend
import com.udacity.asteroidradar.util.toFormattedString

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllAsteroids(asteroids: List<Asteroid>)

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date = :today ORDER BY close_approach_date")
    fun getTodayAsteroids(
        today: String = getToday().toFormattedString()
    ): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date BETWEEN :today AND :weekend ORDER BY close_approach_date")
    fun getWeekAsteroid(
        today: String = getToday().toFormattedString(),
        weekend: String = getWeekend().toFormattedString()
    ): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_table ORDER BY close_approach_date")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroid_table WHERE close_approach_date < :today")
    suspend fun deleteOldAsteroid(
        today: String = getToday().toFormattedString()
    )
}