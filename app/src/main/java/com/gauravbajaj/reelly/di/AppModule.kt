package com.gauravbajaj.reelly.di

import android.content.Context
import com.gauravbajaj.reelly.data.api.SubtitlesApi
import com.gauravbajaj.reelly.data.repository.SubtitlesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides dependencies for the application.
 * This module is installed in the [SingletonComponent], meaning that all provided dependencies
 * will have a singleton scope and live as long as the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.example.com/"

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideSubtitlesApi(retrofit: Retrofit): SubtitlesApi =
        retrofit.create(SubtitlesApi::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(
        subtitlesApi: SubtitlesApi,
        @ApplicationContext context: Context,
        moshi: Moshi
    ): SubtitlesRepository =
        SubtitlesRepository(subtitlesApi, context, moshi)
}
