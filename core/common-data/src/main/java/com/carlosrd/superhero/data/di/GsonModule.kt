package com.carlosrd.superhero.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Provides
import javax.inject.Singleton

@dagger.Module
class GsonModule {

    @Singleton
    @Provides
    fun provideGson(): Gson =
        GsonBuilder().create()

}