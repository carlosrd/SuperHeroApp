package com.carlosrd.superhero.data.di

import dagger.Provides

@dagger.Module(includes = [
    GsonModule::class,
    OkHttpModule::class,
    RetrofitModule::class
])
class CommonDataModule