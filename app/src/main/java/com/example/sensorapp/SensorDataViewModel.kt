package com.example.sensorapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SensorDataViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<SensorData>>
    private val repository: SensorDataRepository

    init {
        val sensorDao = AppDatabase.getDatabase(application).sensorDataDao()
        repository = SensorDataRepository(sensorDao)
        readAllData = repository.readAllData
    }

    fun addData(sensorData: SensorData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(sensorData)
        }
    }
}