package com.softgates.instadoctor.takeallergieview

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.network.AddValue
import com.softgates.instadoctor.network.SymptomList
import com.softgates.instadoctor.util.ApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class TakeAllergieViewModel (val sharedPreferences: SharedPreferences,
                             application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus?>
        get() = _status

    private val _message = MutableLiveData<String>()
    val message: LiveData<String?>
        get() = _message



    private val _addlist = MutableLiveData<List<AddValue>>()
    val addlist: LiveData<List<AddValue>>
        get() = _addlist

    private val getlist = ArrayList<AddValue>()

    private var _notifyItem = MutableLiveData<Int>()
    val notifyItem: LiveData<Int>
        get() = _notifyItem

    init {
        getlist!!.add(AddValue("Allergi One"))
        getlist!!.add(AddValue("Allergi Two"))
//        getlist!!.add(SymptomList(3,"Headech",0))
//        getlist!!.add(SymptomList(4,"Sore Throat",0))
//        getlist!!.add(SymptomList(5,"Nasal Congestion",0))
//        getlist!!.add(SymptomList(6,"Rash",0))
                _addlist.value = getlist
        Log.e("APIRESPONSE", "wishlist api is called..." )
        //   _symptomlist.value = doctorlist
    }
}