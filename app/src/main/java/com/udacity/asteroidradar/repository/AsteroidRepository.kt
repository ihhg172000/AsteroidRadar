package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.network.RetrofitClient
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    val allAsteroids = database.asteroidDao().getAllAsteroids()
    val todayAsteroid = database.asteroidDao().getTodayAsteroids()
    val weekAsteroids = database.asteroidDao().getWeekAsteroid()

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroids = parseAsteroidsJsonResult(
                    JSONObject(
                        RetrofitClient.nasaApi.getAsteroids()
                    )
                )
                database.asteroidDao().insertAllAsteroids(asteroids)
            } catch (e: Exception) {

            }
        }
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                _pictureOfDay.postValue(RetrofitClient.nasaApi.getPictureOfDay())
            } catch (e: Exception) {

            }
        }
    }

    suspend fun deleteOldAsteroids() = database.asteroidDao().deleteOldAsteroid()
}