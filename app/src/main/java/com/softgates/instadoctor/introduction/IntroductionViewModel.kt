package com.softgates.instadoctor.introduction

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.network.PropertiesImages

class IntroductionViewModel (val sharedPreferences: SharedPreferences,
                             application: Application
) : AndroidViewModel(application) {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String?>
        get() = _message



    // runnning out adapter
    private val _sliderimg = MutableLiveData<List<PropertiesImages>>()
    val sliderimg: LiveData<List<PropertiesImages>>
        get() = _sliderimg
    var sliderimage: MutableList<PropertiesImages>? =  mutableListOf()




    init {

        sliderimage!!.add(PropertiesImages("A"))
        sliderimage!!.add(PropertiesImages("B"))
        sliderimage!!.add(PropertiesImages("C"))
      //  sliderimage!!.add(PropertiesImages("D"))

        _sliderimg.value =  sliderimage


    }


    fun complete() {

    }
}