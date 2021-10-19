package com.carlosrd.superhero.data.di

import dagger.Provides
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@dagger.Module
class OkHttpModule {

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(logger: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
      val logger = HttpLoggingInterceptor()
      logger.level = HttpLoggingInterceptor.Level.BODY
      return logger
  }
}