package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository

class RefreshAsteroidsWorker(
    context: Context,
    parameters: WorkerParameters
) :  CoroutineWorker(context, parameters){
    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)
        try {
            repository.deleteOldAsteroids()
            repository.refreshAsteroids()
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }
    companion object {
        const val WORK_NAME = "RefreshAsteroidWorker"
    }
}