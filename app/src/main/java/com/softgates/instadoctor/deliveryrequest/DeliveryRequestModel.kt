package com.softgates.instadoctor.deliveryrequest

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.SymptomList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import com.softgates.instadoctor.util.ValidationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DeliveryRequestModel (val sharedPreferences: SharedPreferences,
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

    private val _GetSymptomlist = MutableLiveData<List<SymptomList>>()
    val GetSymptomlist: LiveData<List<SymptomList>>
        get() = _GetSymptomlist

    private val _mobile = MutableLiveData<String>()
    val mobile : LiveData<String?>
        get() = _mobile

    private val _address = MutableLiveData<String>()
    val address : LiveData<String?>
        get() = _address

    init {
        getlist!!.add(SymptomList(1, "Cold", 0))
        getlist!!.add(SymptomList(2, "Cough", 0))
        getlist!!.add(SymptomList(3, "Headech", 0))
        getlist!!.add(SymptomList(4, "Sore Throat", 0))
        getlist!!.add(SymptomList(5, "Nasal Congestion", 0))
        getlist!!.add(SymptomList(6, "Rash", 0))
        _symptomlist.value = getlist
        Log.e("APIRESPONSE", "wishlist api is called...")
        //   _symptomlist.value = doctorlist
        getSymtomApi()
    }

    fun onTextChangedMobile(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _mobile.value= s.toString()
        }
        else
        {
            _mobile.value=""
        }
    }

    fun onTextChangedAddress(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _address.value= s.toString()
        }
        else
        {
            _address.value=""
        }
    }

    fun login()
    {
        when {
            _mobile.value!!.isEmpty() -> _message.value="The mobile field is required."
          //  !ValidationUtil.isEmailValid(_email.value.toString()) ->_message.value="The Email address is not valid"
            else -> getSymtomApi()
        }
    }

    fun addClick(product: SymptomList, type: Int, index: Int) {
        Log.e("UPDATETICK", "update tick is called......" + _GetSymptomlist.value!!.get(index).tick)
        if (_GetSymptomlist.value!!.get(index).tick == 0) {
            _GetSymptomlist.value!!.get(index).tick = 1
        } else {
            _GetSymptomlist.value!!.get(index).tick = 0
        }
        _GetSymptomlist.value = _GetSymptomlist.value
        _notifyItem.value = index
    }

    fun getSymtomApi() {
        if (!Constant.connected(context)) {
            _message.value = context.resources.getString(R.string.nointernet)
        } else {
            _status.value = ApiStatus.LOADING
            //   var token=sharedPreferences.getString(Constant.USERTOKEN,"")
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred =
                    InstaDoctorApi.retrofitService.getSymptoms("get_symptoms")
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(
                        Constant.APIRESPONSE,
                        "reviewlist api response is......" + response.toString()
                    )
                    if (response.status == Constant.SUCCEESSSTATUSTWOHUNDRED) {
                        Log.e(
                            Constant.APIRESPONSE,
                            "registration api response success one one one is......"
                        )
                        // _message.value= response.message
                        _GetSymptomlist.value = response.data!! as MutableList<SymptomList>
                        _message.value = response.message!!.toString()
                    } else {
                        _message.value = response.message!!.toString()
                    }
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value = "Api Failure " + e.message
                    Log.e(Constant.APIRESPONSE, "registration api failure is......" + e.toString())
                }
            }
        }
    }
}