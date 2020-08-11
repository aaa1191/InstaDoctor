package com.softgates.instadoctor.mychild
import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.MyChildList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyChildViewModel (val sharedPreferences: SharedPreferences,
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

    private val _mychildlist = MutableLiveData<List<MyChildList>>()
    val MyChildList: LiveData<List<MyChildList>>
        get() = _mychildlist
    private val getVerblist = ArrayList<MyChildList>()


    init {
        Log.e("APIRESPONSE","wishlist api is called...")
   //     getVerblist!!.add(MyChildList("dffdffdfd","dfdfdfd"))
    //    getVerblist!!.add(MyChildList("dfdf","dfdfd"))
   //     _mychildlist.value = getVerblist
       // getdoctorListApi()
        //hari
        myChildListApi()
    }

    fun complete() {

    }

    fun myChildListApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var patiendid=sharedPreferences.getString(Constant.PATIENTID,"")
            Log.e("MYCHILDLIST","tokenlistapi token response is......"+patiendid.toString())

            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.MyChildList("get_child_list",patiendid.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"MYCHILDLIST api response is......"+response.toString())
                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"getdoctorlist api response success one one one is......")
                        // _message.value= response.message
                        //  sharedPreferences.edit { putInt("COUNT",0) }
                        _mychildlist.value=  response.data!! as MutableList<MyChildList>
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