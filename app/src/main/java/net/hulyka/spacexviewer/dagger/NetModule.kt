package net.hulyka.spacexviewer.dagger

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import net.hulyka.spacexviewer.BuildConfig.DEBUG
import net.hulyka.spacexviewer.R
import net.hulyka.spacexviewer.api.service.APIService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = if (DEBUG) BODY else NONE }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    @UnstableDefault
    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        return Json(JsonConfiguration.Default.copy(strictMode = false, encodeDefaults = false))
            .asConverterFactory("application/json".toMediaType())
    }


    @Provides
    @Singleton
    @Named("serverUrl")
    fun provideServerUrl(context: Context): String {
        return context.getString(R.string.server_url)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        jsonSerialization: Converter.Factory,
        okHttpClient: OkHttpClient,
        @Named("serverUrl")
        serverUrl: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(jsonSerialization)
        .baseUrl(serverUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

}