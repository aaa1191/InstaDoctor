package com.softgates.instadoctor.contactinformation

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.weightheightview.WeightHeightViewModel

class ContactInformationViewModelFactory (val sharedPreferences: SharedPreferences,
                                          private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactInformationViewModel::class.java)) {
            return ContactInformationViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}