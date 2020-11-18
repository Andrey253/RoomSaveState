package com.boyko.savestate.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_database")
data class ContactEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int?,
        val username: String,
        val phone: String,
)