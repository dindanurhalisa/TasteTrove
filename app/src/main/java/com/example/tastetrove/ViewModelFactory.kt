package com.example.tastetrove

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tastetrove.di.Injection
import com.example.tastetrove.data.repo.UserRepository
import com.example.tastetrove.view.login.LoginViewModel
import com.example.tastetrove.view.main.MainViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context) = ViewModelFactory(Injection.provideRepository(context))
    }
}