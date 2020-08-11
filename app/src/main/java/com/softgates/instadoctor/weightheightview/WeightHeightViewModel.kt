package com.softgates.instadoctor.weightheightview

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeightHeightViewModel (val sharedPreferences: SharedPreferences,
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

    private val _navigateActivity = MutableLiveData<Int>()
    val navigateActivity : LiveData<Int?>
        get() = _navigateActivity

    private val _kg = MutableLiveData<Int>()
    val kg : LiveData<Int?>
        get() = _kg

    private val _cm = MutableLiveData<Int>()
    val cm : LiveData<Int?>
        get() = _cm

    init {
        _kg.value=25
        _cm.value=80
        _navigateActivity.value=0
    }

    fun complete() {

    }



    fun setKg(kg:Int)
    {
        _kg.value=kg
    }

    fun setCm(cm:Int)
    {
        _cm.value=cm
    }

    fun next()
    {
        when {
            _kg.value!! ==0 -> _message.value="The weight is required."
            _cm.value!! ==0 -> _message.value="The height is required."
            // !ValidationUtil.isEmailValid(_email.value.toString()) ->_message.value="The Email address is not valid"
            else ->patientDetailApi()
        }
    }

    fun patientDetailApi()
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
            var childstatus=sharedPreferences.getInt(Constant.CHILDSTATUS,0)
            var noofdays=sharedPreferences.getString(Constant.FELTDAYS,"")
            var medication=sharedPreferences.getInt(Constant.MEDICATION,0)
            var allergy=sharedPreferences.getInt(Constant.ALLERGY,0)
            var symptomname=sharedPreferences.getString(Constant.SYMPTOMNAME,"")
            var appid=sharedPreferences.getString(Constant.APPID,"")

            var childid:String=""
            var medicationName:String=""
            var allergyname:String=""
            if(childstatus==1)
            {
                childid = sharedPreferences.getInt(Constant.CHILDID,0).toString()
            }
            if(medication==1)
            {
                medicationName = sharedPreferences.getString(Constant.MEDICATIONNAME,"").toString()

            }
            if(allergy==1)
            {
                allergyname = sharedPreferences.getString(Constant.ALLERGYNAME,"").toString()

            }

            Log.e(Constant.APIRESPONSE,"addPatientDetail token response is......"+token.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail patientid response is......"+patientid.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail childstatus response is......"+childstatus.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail noofdays response is......"+noofdays.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail childid response is......"+childid.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail medication response is......"+medication.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail medicationName response is......"+medicationName.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail allergy response is......"+allergy.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail allergyname response is......"+allergyname.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail kg response is......"+kg.value.toString())
            Log.e(Constant.APIRESPONSE,"addPatientDetail cm response is......"+cm.value.toString())


            Log.e(Constant.APIRESPONSE,"getdoctorlist token response is......"+token.toString())
            Log.e(Constant.APIRESPONSE,"getdoctorlist app id response is......"+appid.toString())
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                //Log.e("RESPONSE","email...."+email.value.toString()+"...pin...")
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.addPatientDetail("submit_patient_details",token.toString(),patientid.toString(),childstatus.toString(),childid.toString(),noofdays.toString(),medication.toString(),medicationName.toString(),allergy.toString(),allergyname.toString(),kg.value.toString(),cm.value.toString(),appid.toString(),symptomname.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())
                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"registration api response success one one one is......")
                        // _message.value= response.message
                        _message.value= response.message!!.toString()
                        _navigateActivity.value=1
                    }
                    else
                    {
                        _navigateActivity.value=1

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