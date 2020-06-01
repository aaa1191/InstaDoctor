package com.softgates.instadoctor.selectsymptom

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.network.SymptomList
import com.softgates.instadoctor.util.ApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SymptomViewModel (val sharedPreferences: SharedPreferences,
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



    private val _symptomlist = MutableLiveData<List<SymptomList>>()
    val symptomlist: LiveData<List<SymptomList>>
        get() = _symptomlist

    private val getlist = ArrayList<SymptomList>()

    private var _notifyItem = MutableLiveData<Int>()
    val notifyItem: LiveData<Int>
        get() = _notifyItem

    init {


        getlist!!.add(SymptomList(1,"Cold",0))
        getlist!!.add(SymptomList(2,"Cough",0))
        getlist!!.add(SymptomList(3,"Headech",0))
        getlist!!.add(SymptomList(4,"Sore Throat",0))
        getlist!!.add(SymptomList(5,"Nasal Congestion",0))
        getlist!!.add(SymptomList(6,"Rash",0))
        _symptomlist.value = getlist
        Log.e("APIRESPONSE", "wishlist api is called..." )
     //   _symptomlist.value = doctorlist
    }

    fun addClick(product: SymptomList,type:Int,index:Int)
    {
        Log.e("UPDATETICK","update tick is called......"+ _symptomlist.value!!.get(index).tick)
        if(_symptomlist.value!!.get(index).tick==0)
        {
            _symptomlist.value!!.get(index).tick=1
        }
        else{
            _symptomlist.value!!.get(index).tick=0
        }
        _symptomlist.value=  _symptomlist.value
        _notifyItem.value=index
    }
}