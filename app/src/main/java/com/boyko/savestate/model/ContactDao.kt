package com.boyko.savestate.model

import androidx.room.*

@Dao
interface ContactDao {

    @Query("SELECT * from contact_database")
    fun getAll(): List<ContactEntity>

    @Query("SELECT * FROM contact_database WHERE id = :id")
    fun findById(id: Int): ContactEntity

    @Query("DELETE FROM contact_database")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contacts: List<ContactEntity>)

    @Update
    fun update(contact: ContactEntity)

    @Delete
    fun delete(contact: ContactEntity)
}