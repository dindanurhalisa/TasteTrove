package com.example.tastetrove.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastetrove.data.pref.UserModel
import com.example.tastetrove.data.repo.UserRepository
import com.example.tastetrove.data.response.Result
import com.example.tastetrove.data.response.auth.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun login(email: String, pass: String): LiveData<Result<LoginResponse>> {
        return repository.login(email, pass)
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}