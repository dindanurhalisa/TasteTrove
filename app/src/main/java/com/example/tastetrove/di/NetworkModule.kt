package com.example.tastetrove.di

import android.content.Context
import com.example.tastetrove.data.network.ApiConfig
import com.example.tastetrove.data.network.NewsApiService
import com.example.tastetrove.util.NewsUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideNewsApiService(@ApplicationContext context: Context): NewsApiService {
        return ApiConfig.getApiService(context, NewsUrl.BASE_URL)
    }

}