package com.wsa.shows.di

import com.wsa.shows.network.api.ApiHelper
import com.wsa.shows.network.api.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class ApiHelperModule {
    @Binds
    abstract fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper
}