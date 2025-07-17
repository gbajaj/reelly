package com.gauravbajaj.reelly.data.repository

import android.content.Context
import com.gauravbajaj.reelly.data.api.SubtitlesApi
import com.gauravbajaj.reelly.data.model.Subtitle
import com.gauravbajaj.reelly.data.model.sampleSubtitles
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository for fetching user data.
 *
 * This class provides methods to fetch a list of users or a single user by their ID.
 * It uses a [SubtitlesApi] to make network requests and [Moshi] for JSON parsing.
 * For development purposes, it currently uses a local JSON file ([R.raw.users]) to provide fake user data.
 *
 * @property subtitlesApi The API service for user-related network calls.
 * @property context The application context, used to access resources.
 * @property moshi The Moshi instance for JSON serialization and deserialization.
 */
@Singleton
class SubtitlesRepository @Inject constructor(
    private val subtitlesApi: SubtitlesApi,
    @ApplicationContext
    private val context: Context,
    private val moshi: Moshi
) {
    fun getSubtitles(): Flow<List<Subtitle>> = flow {
        try {
//            emit(subtitlesApi.getSubtitles("someid"))
            emit(getFakeSubtitleData())

        } catch (e: Exception) {
            throw Exception("Failed to fetch users", e)
        }
    }

    private fun getFakeSubtitleData(): List<Subtitle> = sampleSubtitles
}
