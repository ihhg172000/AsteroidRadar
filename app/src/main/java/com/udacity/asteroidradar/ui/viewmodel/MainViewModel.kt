package com.udacity.asteroidradar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.util.AsteroidFilter
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)

    private val asteroidFilter = MutableLiveData(AsteroidFilter.ALL)
    val asteroids = Transformations.switchMap(asteroidFilter) {
        when (it!!) {
            AsteroidFilter.ALL -> repository.allAsteroids
            AsteroidFilter.TODAY -> repository.todayAsteroid
            AsteroidFilter.WEEK -> repository.weekAsteroids
        }
    }

    val pictureOfDay = repository.pictureOfDay

    init {
        refreshAsteroids()
        refreshPictureOfDay()
    }

    private fun refreshAsteroids() {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
    }

    private fun refreshPictureOfDay() {
        viewModelScope.launch {
            repository.refreshPictureOfDay()
        }
    }

    fun updateAsteroidFilter(asteroidFilter: AsteroidFilter) {
        this.asteroidFilter.value = asteroidFilter
    }
}