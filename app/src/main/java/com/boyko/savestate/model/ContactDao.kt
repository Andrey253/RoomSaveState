package com.boyko.savestate.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {

    @Query("SELECT * from contact_database")
    fun getAll(): List<ContactEntity>

    @Query("SELECT * FROM contact_database WHERE id = :id")
    fun findById(id: Int): ContactEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contacts: List<ContactEntity>)

    @Update
    fun update(contact: ContactEntity)

    @Delete
    fun delete(contact: ContactEntity)
}