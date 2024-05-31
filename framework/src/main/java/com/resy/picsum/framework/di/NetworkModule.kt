package com.resy.picsum.framework.di

import com.resy.picsum.framework.api.service.PicsumApiService
import com.resy.picsum.framework.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * The dependency module which provides everything related to network.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides the logging interceptor for HTTP requests and responses.
     *
     * It basically provides a BODY level in DEBUG, and a none level otherwise.
     *
     * @return the logging interceptor for HTTP requests and responses
     */
    @Provides
    @Named("logging")
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        return interceptor
    }

    /**
     * Provides the HTTP client to be used by Retrofit.
     *
     * @param loggingInterceptor the logging interceptor for HTTP requests and responses
     *
     * @return the HTTP client to be used by Retrofit.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("logging") loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                }
            }
            .build()

    /**
     * Provides the Retrofit instance to be used in the application.
     *
     * @param okHttpClient the HTTP client to be used by Retrofit
     *
     * @return the Retrofit instance to be used in the application
     */
    @Provides
    @Singleton
    fun getRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit
    }

    /**
     * Provides the API service to access Picsum to be used in the application.
     *
     * @param retrofit the Retrofit instance used in the application.
     *
     * @return the API service to access Picsum to be used in the application
     */
    @Provides
    @Singleton
    fun providePicsumApiService(
        retrofit: Retrofit
    ): PicsumApiService {
        return retrofit.create(PicsumApiService::class.java)
    }
}
