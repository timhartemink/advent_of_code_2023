package com.tilton.aoc2023.di

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Room
import com.tilton.aoc2023.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val DEFAULT_TIMEOUT_MILLIS = 10_000L
    private const val DATABASE_NAME = "aoc2023_ps"

    @Provides
    fun getHttpClient(): HttpClient = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                }
            )
        }
        install(HttpTimeout) {
            requestTimeoutMillis = DEFAULT_TIMEOUT_MILLIS
            connectTimeoutMillis = DEFAULT_TIMEOUT_MILLIS
            socketTimeoutMillis = DEFAULT_TIMEOUT_MILLIS
        }
    }

    @Provides
    fun getAssetsManager(@ApplicationContext context: Context): AssetManager = context.assets

    @Provides
    fun getDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .build()
}
