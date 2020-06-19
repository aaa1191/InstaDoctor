package com.softgates.instadoctor.scheduleappointment

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softgates.instadoctor.mychild.MyChildViewModel

class ScheduleAppointmentModelFactory (val sharedPreferences: SharedPreferences,
                                       private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleAppointmentModel::class.java)) {
            return ScheduleAppointmentModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}