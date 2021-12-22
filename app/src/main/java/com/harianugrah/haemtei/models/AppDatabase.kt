package com.harianugrah.haemtei.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AuthX::class], version = 3)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun authXDao(): AuthXDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "Haemtei")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Warning: Biar cepet aja
                    .build()
            }

            return instance as AppDatabase;
        }
    }
}
