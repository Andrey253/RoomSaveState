package com.boyko.savestate.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDb : RoomDatabase() {

    abstract fun contasctDao(): ContactDao

    companion object {
        private var INSTANCE: ContactDb? = null
        fun getInstance(context: Context): ContactDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    ContactDb::class.java,
                    "roomdb")
                    .build()
            }
            return INSTANCE as ContactDb
        }
    }
}