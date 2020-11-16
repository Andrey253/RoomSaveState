//package com.boyko.savestate
//
//import android.app.Application
//import androidx.room.Room
//import com.boyko.savestate.model.ContactDb
//
//class DbApp : Application() {
//
//    val room: ContactDb  = Room
//        .databaseBuilder(this, ContactDb::class.java, "contact_database")
//        .build()
//}