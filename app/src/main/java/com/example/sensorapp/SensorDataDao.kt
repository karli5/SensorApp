package com.example.sensorapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SensorDataDao {
    @Query("SELECT * FROM dataTable")
    fun getAll(): LiveData<List<SensorData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sensorData : SensorData)

    @Query("DELETE FROM dataTable")
    suspend fun deleteAll()
}