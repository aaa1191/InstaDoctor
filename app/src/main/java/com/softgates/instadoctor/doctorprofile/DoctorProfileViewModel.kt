package com.softgates.instadoctor.doctorprofile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.DoctorList
import com.softgates.instadoctor.network.DoctorReviewList
import com.softgates.instadoctor.network.HomeList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DoctorProfileViewModel (val sharedPreferences: SharedPreferences,
                              application: Application, doctorlist:DoctorList
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

    private val _doctorlist = MutableLiveData<DoctorList>()
    val doctorlist: LiveData<DoctorList?>
        get() = _doctorlist

    private val _GetRatinglist = MutableLiveData<List<DoctorReviewList>>()
    val GetRatinglist: LiveData<List<DoctorReviewList>>
        get() = _GetRatinglist


    init {
        Log.e("APIRESPONSE","wishlist api is called..."+doctorlist.toString())
        _doctorlist.value = doctorlist
        doctorReviewApi()
    }

    fun doctorReviewApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            _status.value = ApiStatus.LOADING
           var token=sharedPreferences.getString(Constant.USERTOKEN,"")
         //   var token="e9ac614fc80c9ffcd84d022d309243f1a50e956599a6172a84ecfb2fec45ac330b2dce40f5eb2ac8cf0003fb8391984033fd29ad42c0062efb5d62385369c0aa"
            Log.e(Constant.APIRESPONSE,"getdoctorlist token response is......"+token.toString())

            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.doctorreview("get_rating_list",_doctorlist.value!!.id.toString(),token.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"reviewlist api response is......"+response.toString())
                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"registration api response success one one one is......")
                        // _message.value= response.message
                        _GetRatinglist.value=  response.data!! as MutableList<DoctorReviewList>
                        _message.value= response.message!!.toString()
                    }
                    else
                    {
                        _message.value= response.message!!.toString()
                    }
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"registration api failure is......"+e.toString())
                }
            }
        }
    }

    fun complete() {

    }
}