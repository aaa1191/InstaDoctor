package com.softgates.instadoctor.takemedicine

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.selectsymptom.SymptomViewModel

class TakeMedicineViewModelFactory (val sharedPreferences: SharedPreferences,
                                    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TakeMedicineViewModel::class.java)) {
            return TakeMedicineViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}