package com.softgates.instadoctor.prescription

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.mychild.MyChildViewModel

class PrescriptionViewModelFactory (val sharedPreferences: SharedPreferences,
                                    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrescriptionViewModel::class.java)) {
            return PrescriptionViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}