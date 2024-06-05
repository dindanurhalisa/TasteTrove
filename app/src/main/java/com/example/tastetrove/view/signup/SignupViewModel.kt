package com.example.tastetrove.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tastetrove.data.repo.UserRepository
import com.example.tastetrove.data.response.Result
import com.example.tastetrove.data.response.SignupResponse

class SignupViewModel (private val repository: UserRepository) : ViewModel() {
    fun signup(name: String, email: String, pass: String): LiveData<Result<SignupResponse>> =
        repository.signup(name, email, pass)
}