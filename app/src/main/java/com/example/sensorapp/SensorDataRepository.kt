package com.example.sensorapp
import androidx.lifecycle.LiveData
class SensorDataRepository(private val sensorDataDao: SensorDataDao) {

    val readAllData: LiveData<List<SensorData>> = sensorDataDao.getAll()

    suspend fun addData(data : SensorData) {
        sensorDataDao.insert(data)
    }

}