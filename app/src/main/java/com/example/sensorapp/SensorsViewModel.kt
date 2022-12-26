package com.example.sensorapp

import android.hardware.Sensor
import android.hardware.SensorEventListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SensorsViewModel : ViewModel() {
    private val currentDataX = MutableLiveData<Float>()
    private val currentDataY = MutableLiveData<Float>()
    private val currentDataZ = MutableLiveData<Float>()

    private val dataList = MutableLiveData<MutableList<Float>>()
    val buttonIsStart = MutableLiveData<Boolean>()
    lateinit var eventListener : SensorEventListener

    init {
        currentDataX.value = 0f
        currentDataY.value = 0f
        currentDataZ.value = 0f
        buttonIsStart.value = false
    }

    fun initializeEventListener(listener : SensorEventListener) {
        eventListener = listener
    }
    fun updateData(x: Float?){
        currentDataX.value = x
    }
    fun updateData(x: Float?, y: Float?, z: Float?){
        currentDataX.value = x
        currentDataY.value = y
        currentDataZ.value = z
    }

    fun updateDataList(newData: Float) {
        dataList.value?.add(newData)
    }

    fun updateButton() {
        buttonIsStart.value = !(buttonIsStart.value)!!
    }
}