package com.tilton.aoc2023.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tilton.aoc2023.data.database.model.Preference

@Dao
interface PreferenceDao {
    @Query("SELECT * FROM preference WHERE `key` == :key")
    fun getPreference(key: String): Preference?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(preference: Preference)

    @Delete
    fun delete(preference: Preference)
}
