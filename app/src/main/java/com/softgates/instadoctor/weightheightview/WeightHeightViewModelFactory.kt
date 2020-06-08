package com.softgates.instadoctor.weightheightview

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.takeallergieview.TakeAllergieViewModel

class WeightHeightViewModelFactory (val sharedPreferences: SharedPreferences,
                                    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeightHeightViewModel::class.java)) {
            return WeightHeightViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}