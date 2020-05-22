package com.softgates.instadoctor.passwordinformation

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.recoverpassword.RecoverPasswordViewModel

class PasswordInformationViewModelFactory (val sharedPreferences: SharedPreferences,
                                           private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordInformationViewModel::class.java)) {
            return PasswordInformationViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}