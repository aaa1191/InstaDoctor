package com.softgates.instadoctor.doctorprofile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.network.HomeList

class DoctorProfileViewModel (val sharedPreferences: SharedPreferences,
                              application: Application
) : AndroidViewModel(application) {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String?>
        get() = _message

    private val _GetVerblist = MutableLiveData<List<HomeList>>()
    val GetVerblist: LiveData<List<HomeList>>
        get() = _GetVerblist
    private val getVerblist = ArrayList<HomeList>()

    init {
        Log.e("APIRESPONSE","wishlist api is called...")
        getVerblist!!.add(HomeList(0,"dffdffdfd","dfdfdfd"))
        getVerblist!!.add(HomeList(1,"dfdf","dfdfd"))
        getVerblist!!.add(HomeList(2,"dfdf","dfdfd"))
        _GetVerblist.value = getVerblist
    }

    fun complete() {

    }
}