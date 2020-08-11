package com.softgates.instadoctor.mychild

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyChildViewModelFactory (val sharedPreferences: SharedPreferences,
                               private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyChildViewModel::class.java)) {
            return MyChildViewModel(sharedPreferences,application) as T
}

throw IllegalArgumentException("Unknown ViewModel class")

}

}