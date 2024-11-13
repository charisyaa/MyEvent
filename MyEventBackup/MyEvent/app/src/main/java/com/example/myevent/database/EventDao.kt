package com.example.myevent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: EventEntity)

    @Update
    fun update(event: EventEntity)

    @Delete
    fun delete(event: EventEntity)

    @Query("SELECT * from evententity WHERE id = :id")
    fun getEventEntityById(id: String): LiveData<List<EventEntity>>
}