package com.harianugrah.ngampus.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class], version = 2)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "NgampusDb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Warning: Biar cepet aja
                    .build()
            }

            return instance as AppDatabase;
        }
    }
}
