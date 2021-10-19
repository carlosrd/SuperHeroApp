package com.carlosrd.superhero.data.characters.framework.di

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.carlosrd.superhero.data.characters.datasource.remote.model.ApiKeysDTO
import com.carlosrd.superhero.data.characters.di.annotation.MarvelPublicKey
import dagger.Provides
import javax.inject.Singleton

@dagger.Module
class ApiKeysModule {

    @Singleton
    @Provides
    fun provideMarvelPublicKey(context: Context) : ApiKeysDTO {
        val ai: ApplicationInfo = context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        return ApiKeysDTO(
            ai.metaData["keyValue"].toString(),
            ai.metaData["keyValue2"].toString(),
        )

    }

}