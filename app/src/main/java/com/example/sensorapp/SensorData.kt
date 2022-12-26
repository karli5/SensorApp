package com.example.sensorapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataTable")
data class SensorData(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "sensor_name") val sensorName: String,
    @ColumnInfo(name = "x_component") val x: Float,
    @ColumnInfo(name = "y_component") val y: Float,
    @ColumnInfo(name = "z_component") val z: Float
    )
