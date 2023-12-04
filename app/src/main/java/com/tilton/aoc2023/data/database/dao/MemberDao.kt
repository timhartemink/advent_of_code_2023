package com.tilton.aoc2023.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tilton.aoc2023.data.database.model.Member

@Dao
interface MemberDao {
    @Query("SELECT * FROM member")
    fun getMembers(): List<Member>

    @Insert
    fun insert(members: List<Member>)

    @Query("DELETE FROM member")
    fun deleteALl()
}
