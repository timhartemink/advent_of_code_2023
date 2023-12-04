package com.tilton.aoc2023.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tilton.aoc2023.data.database.dao.MemberDao
import com.tilton.aoc2023.data.database.dao.PreferenceDao
import com.tilton.aoc2023.data.database.model.Member
import com.tilton.aoc2023.data.database.model.Preference

@Database(entities = [Preference::class, Member::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun preferenceDao(): PreferenceDao
    abstract fun memberDao(): MemberDao
}
