package com.jyotirmay.mapapplicationdemo.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jyotirmay.mapapplicationdemo.BuildConfig
import com.jyotirmay.mapapplicationdemo.data.remote.AqiApiService
import com.jyotirmay.mapapplicationdemo.data.remote.BookApiService
import com.jyotirmay.mapapplicationdemo.data.remote.GeocodingApiService
import com.jyotirmay.mapapplicationdemo.data.remote.MockBookApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 30L

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    @Named("aqi")
    fun provideAqiRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.AQICN_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    @Named("geocoding")
    fun provideGeocodingRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BDC_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideAqiApiService(@Named("aqi") retrofit: Retrofit): AqiApiService =
        retrofit.create(AqiApiService::class.java)

    @Provides
    @Singleton
    fun provideGeocodingApiService(@Named("geocoding") retrofit: Retrofit): GeocodingApiService =
        retrofit.create(GeocodingApiService::class.java)

    @Provides
    @Singleton
    fun provideBookApiService(): BookApiService = MockBookApiService()
}
