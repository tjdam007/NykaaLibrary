package com.mjandroiddev.nykaalibrary.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mjandroiddev.nykaalibrary.db.entities.Session
import com.mjandroiddev.nykaalibrary.db.entities.SessionDao

/*
* App Database used for storing session objects.
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
@androidx.room.Database(entities = [Session::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context): Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}