package com.example.tastetrove.di

import android.content.Context
import com.example.tastetrove.data.pref.UserPreference
import com.example.tastetrove.data.pref.dataStore
import com.example.tastetrove.data.repo.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}