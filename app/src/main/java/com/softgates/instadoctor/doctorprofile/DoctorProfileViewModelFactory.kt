package com.softgates.instadoctor.doctorprofile

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.home.HomeViewModel
import com.softgates.instadoctor.network.DoctorList

class DoctorProfileViewModelFactory (val sharedPreferences: SharedPreferences,
                                     private val application: Application, private val doctorlist: DoctorList
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoctorProfileViewModel::class.java)) {
            return DoctorProfileViewModel(sharedPreferences,application,doctorlist) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}