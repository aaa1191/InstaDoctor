package com.softgates.instadoctor.home

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.DoctorList
import com.softgates.instadoctor.network.HomeList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel (val sharedPreferences: SharedPreferences,
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

    private val _GetOnlinelist = MutableLiveData<List<DoctorList>>()
    val GetOnlinelist: LiveData<List<DoctorList>>
        get() = _GetOnlinelist

    private val _GetOfflinelist = MutableLiveData<List<DoctorList>>()
    val GetOfflinelist: LiveData<List<DoctorList>>
        get() = _GetOfflinelist

    private val getVerblist = ArrayList<HomeList>()

    init {
        Log.e("APIRESPONSE","wishlist api is called...")
        getVerblist!!.add(HomeList(0,"dffdffdfd","dfdfdfd"))
        getVerblist!!.add(HomeList(1,"dfdf","dfdfd"))
        getVerblist!!.add(HomeList(2,"dfdf","dfdfd"))
      //  _GetOnlinelist.value = getVerblist
        getdoctorListApi()
    }

    fun complete() {

    }

    fun getdoctorListApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var token=sharedPreferences.getString(Constant.USERTOKEN,"")
            Log.e(Constant.APIRESPONSE,"getdoctorlist token response is......"+token.toString())

            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.getDoctorList("get_doctor",token.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"getdoctorlist api response is......"+response.toString())

                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"getdoctorlist api response success one one one is......")
                        // _message.value= response.message
                        //  sharedPreferences.edit { putInt("COUNT",0) }
                        _GetOnlinelist.value=  response.data!! as MutableList<DoctorList>
                     //   _GetOfflinelist.value=  response.data!!.get(0).offline_doctor_array as MutableList<DoctorList>
                           //   _message.value= response.message!!.toString()
                    }
                    else
                    {
                        _message.value= response.message!!.toString()
                    }
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"getdoctorlist api failure is......"+e.toString())
                }
            }
        }
    }
}