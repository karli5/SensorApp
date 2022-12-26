package com.example.sensorapp

import android.app.Application
import android.hardware.SensorEventListener
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sensorapp.databinding.FragmentSensorsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SensorsViewModel : ViewModel() {
    private val currentDataX = MutableLiveData<Float>()
    private val currentDataY = MutableLiveData<Float>()
    private val currentDataZ = MutableLiveData<Float>()

    private val dataList = MutableLiveData<MutableList<Float>>()

    private val sensorDataList = MutableLiveData<List<SensorData>>()
    val buttonIsStart = MutableLiveData<Boolean>()
    lateinit var eventListener : SensorEventListener
    lateinit var appDb: AppDatabase

    init {
        currentDataX.value = 0f
        currentDataY.value = 0f
        currentDataZ.value = 0f
        buttonIsStart.value = false
    }

    fun initializeEventListener(listener : SensorEventListener) {
        eventListener = listener
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData(x: Float?, sensorName: String?){
        currentDataX.value = x
        if (x != null && sensorName != null) {
            writeData(x, 0f,0f,sensorName)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData(x: Float?, y: Float?, z: Float?, sensorName: String?){
        currentDataX.value = x
        currentDataY.value = y
        currentDataZ.value = z
        if (x != null && y != null && z != null && sensorName != null) {
            writeData(x,y,z,sensorName)
        }

    }

    fun updateDataList(newData: Float, sensorName: String) {
        dataList.value?.add(newData)
    }

    fun updateButton() {
        buttonIsStart.value = !(buttonIsStart.value)!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun writeData(x: Float, y: Float, z: Float, sensorName: String) {
        if(sensorName.isNotEmpty()) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val current = LocalDateTime.now().format(formatter).toString()
            val sensorData = SensorData(null, current, sensorName, x, y, z )
            GlobalScope.launch(Dispatchers.IO)  {
                appDb.sensorDataDao().insert(sensorData)
            }
        }
    }

    private fun readDAllData(): List<SensorData> {
        GlobalScope.launch(Dispatchers.IO) {
            sensorDataList.value = appDb.sensorDataDao().getAll().value
        }
        return sensorDataList.value!!
    }

    fun deleteAllData() {
        GlobalScope.launch{
            appDb.sensorDataDao().deleteAll()
        }
    }

}