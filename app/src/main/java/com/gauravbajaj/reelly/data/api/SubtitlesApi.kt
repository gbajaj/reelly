package com.gauravbajaj.reelly.data.api

import com.gauravbajaj.reelly.data.model.Subtitle
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * API for interacting with the subtitles endpoints.
 *
 * This interface defines the API endpoints to fetch data from the server.
 *
 * @author Gaurav Bajaj
 * @since 1.0
 */
interface SubtitlesApi {
    @GET("subtitles/{id}")
    suspend fun getSubtitles(@Path("subtitleId") id: String): List<Subtitle>
}
