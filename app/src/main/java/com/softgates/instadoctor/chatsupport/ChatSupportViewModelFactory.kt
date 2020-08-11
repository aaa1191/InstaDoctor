package com.softgates.instadoctor.chatsupport

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.contactinformation.ContactInformationViewModel

class ChatSupportViewModelFactory (val sharedPreferences: SharedPreferences,
                                   private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatSupportViewModel::class.java)) {
            return ChatSupportViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}