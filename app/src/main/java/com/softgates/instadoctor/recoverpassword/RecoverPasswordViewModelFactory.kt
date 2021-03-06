package com.softgates.instadoctor.recoverpassword

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.loginview.LoginViewModel

class RecoverPasswordViewModelFactory (val sharedPreferences: SharedPreferences,
                                       private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecoverPasswordViewModel::class.java)) {
            return RecoverPasswordViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}