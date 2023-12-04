package com.tilton.aoc2023.data.database

import com.tilton.aoc2023.data.database.model.Member
import com.tilton.aoc2023.data.database.model.Preference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Source(
    private val appDatabase: AppDatabase,
    private val dispatcher: CoroutineDispatcher
) {
    @Inject
    constructor(appDatabase: AppDatabase) : this(appDatabase, Dispatchers.IO)

    suspend fun getMembers(): List<Member> = withContext(dispatcher) {
        appDatabase.memberDao().getMembers()
    }

    suspend fun storeMembers(members: List<Member>) = withContext(dispatcher) {
        val memberDao = appDatabase.memberDao()
        memberDao.deleteALl()
        memberDao.insert(members)
    }

    suspend fun getLastFetchTime(): Long = withContext(dispatcher) {
        appDatabase.preferenceDao().getPreference(PREF_KEY_LAST_FETCH_TIME)?.value?.toLong() ?: -1L
    }

    suspend fun storeLastFetchTime(timeInMillis: Long) = withContext(dispatcher) {
        appDatabase.preferenceDao().insert(Preference(PREF_KEY_LAST_FETCH_TIME, timeInMillis.toString()))
    }

    suspend fun getEvent(): String? = withContext(dispatcher) {
        appDatabase.preferenceDao().getPreference(PREF_KEY_EVENT)?.value
    }

    suspend fun putEvent(event: String) = withContext(dispatcher) {
        appDatabase.preferenceDao().insert(Preference(PREF_KEY_EVENT, event))
    }

    companion object {
        private const val PREF_KEY_LAST_FETCH_TIME = "last_fetch_time"
        private const val PREF_KEY_EVENT = "event"
    }
}
