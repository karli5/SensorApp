package com.example.sensorapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SensorData :: class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sensorDataDao() : SensorDataDao

    companion object {

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context : Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}