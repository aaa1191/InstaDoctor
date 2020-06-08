package com.softgates.instadoctor.ratingview

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import com.softgates.instadoctor.util.ValidationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RatingViewModel (val sharedPreferences: SharedPreferences,
                       application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _message = MutableLiveData<String>()
    val message: LiveData<String?>
        get() = _message

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus?>
        get() = _status





    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String?>
        get() = _msg

    private val _rating = MutableLiveData<String>()
    val rating : LiveData<String?>
        get() = _rating

    private val _navigateActivity = MutableLiveData<Int>()
    val navigateActivity : LiveData<Int?>
        get() = _navigateActivity


    init {
        _msg.value=""
        _navigateActivity.value=0
        Log.e("APIRESPONSE","wishlist api is called...")
    }

    fun complete() {

    }

    fun setRating(ratings:Float)
    {
        _rating.value = ratings.toString()
    }

    fun onTextChangedMsg(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _msg.value= s.toString()
        }
        else
        {
            _msg.value=""
        }
    }


    fun submit()
    {
        when {
            _msg.value!!.isEmpty() -> _message.value="The email field is required."
            !ValidationUtil.isEmailValid(_msg.value.toString()) ->_message.value="The Email address is not valid"
            else -> submitFeedbackApi()
        }
    }

    fun submitFeedbackApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var token=sharedPreferences.getString(Constant.USERTOKEN,"")
            var patientid=sharedPreferences.getString(Constant.PATIENTID,"")
            var docid=sharedPreferences.getString(Constant.DOCID,"")

               Log.e(Constant.APIRESPONSE,"submitfeedback rating response is......"+rating.value.toString())
          //  Log.e(Constant.APIRESPONSE,"addPatientDetail cm response is......"+cm.value.toString())
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e("RESPONSE","email...."+msg.value.toString()+"...pin...")
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.submitFeedback("submit_feedback",token.toString(),patientid.toString(),docid.toString(),rating.value.toString(),msg.value.toString(),"07.06.2020")
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())

                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"registration api response success one one one is......")

                        // _message.value= response.message
                        _navigateActivity.value=1
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
}