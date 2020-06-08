package com.softgates.instadoctor.setting

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.ChatList
import com.softgates.instadoctor.network.DoctorList
import com.softgates.instadoctor.network.PrescriptionList
import com.softgates.instadoctor.network.SessionList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SettingViewModel (val sharedPreferences: SharedPreferences,
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

    private val _Chatlist = MutableLiveData<List<ChatList>>()
    val Chatlist: LiveData<List<ChatList>>
        get() = _Chatlist

    private val _Sessionlist = MutableLiveData<List<SessionList>>()
    val Sessionlist: LiveData<List<SessionList>>
        get() = _Sessionlist

    private val _Prescriptionlist = MutableLiveData<List<PrescriptionList>>()
    val Prescriptionlist: LiveData<List<PrescriptionList>>
        get() = _Prescriptionlist

    private val getChatlist = ArrayList<ChatList>()
    private val getSesionlist = ArrayList<SessionList>()
    private val getPrescriptionlist = ArrayList<PrescriptionList>()

    init {
        Log.e("APIRESPONSE", "wishlist api is called...")
        getSesionlist!!.add(SessionList("", "We People"))
        getSesionlist!!.add(SessionList("", "We People"))
        getChatlist!!.add(ChatList("", "We People"))
        getChatlist!!.add(ChatList("", "We People"))
        getPrescriptionlist!!.add(PrescriptionList("", "We People"))
        getPrescriptionlist!!.add(PrescriptionList("", "We People"))
        _Chatlist.value = getChatlist
        _Sessionlist.value = getSesionlist
        _Prescriptionlist.value = getPrescriptionlist
    }

    fun getdoctorListApi()
    {

        var token=sharedPreferences.getString(Constant.USERTOKEN,"")
        var patientid=sharedPreferences.getString(Constant.PATIENTID,"")

        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var symptomname=sharedPreferences.getString(Constant.SYMPTOMNAME,"")
            Log.e("TOKENLISTAPI","tokenlistapi token response is......"+symptomname.toString())

            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.getMedicalHistory("medical_history",token.toString(),patientid.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"getdoctorlist api response is......"+response.toString())
                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"getdoctorlist api response success one one one is......")
                        // _message.value= response.message
                        //  sharedPreferences.edit { putInt("COUNT",0) }
                       // _GetOnlinelist.value=  response.data!! as MutableList<DoctorList>
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