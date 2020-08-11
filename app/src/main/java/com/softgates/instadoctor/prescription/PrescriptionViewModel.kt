package com.softgates.instadoctor.prescription

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.MyChildList
import com.softgates.instadoctor.network.PrescriptionList
import com.softgates.instadoctor.network.PrescriptionListModel
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PrescriptionViewModel (val sharedPreferences: SharedPreferences,
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

    private val _docname = MutableLiveData<String>()
    val docname: LiveData<String?>
        get() = _docname

    private val _date = MutableLiveData<String>()
    val date: LiveData<String?>
        get() = _date

    private val _prescriptionlist = MutableLiveData<List<PrescriptionList>>()
    val prescriptionlist: LiveData<List<PrescriptionList>>
        get() = _prescriptionlist
    private val getVerblist = ArrayList<PrescriptionList>()

    private var _notifyItem = MutableLiveData<Int>()
    val notifyItem: LiveData<Int>
        get() = _notifyItem

    init {
        Log.e("APIRESPONSE","wishlist api is called...")
        _docname.value=""
        _notifyItem.value=0
        //     getVerblist!!.add(MyChildList("dffdffdfd","dfdfdfd"))
        //    getVerblist!!.add(MyChildList("dfdf","dfdfd"))
        //     _mychildlist.value = getVerblist
        // getdoctorListApi()
        //hari
        myPrescriptionListApi()
    }

    fun complete() {

    }

    fun setVisible(position:Int)
    {
        if(_prescriptionlist.value!!.get(position).visibility==0)
        {
            _prescriptionlist.value!!.get(position).visibility=1
        }
        else{
            _prescriptionlist.value!!.get(position).visibility=0
        }
        _prescriptionlist.value=  _prescriptionlist.value
        _notifyItem.value=position
    }

    fun myPrescriptionListApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var appid=sharedPreferences.getString(Constant.APPID,"")
            Log.e("MYCHILDLIST","tokenlistapi token response is......"+appid.toString())

            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.MyPrescriptionList("get_prescription",appid.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"MYCHILDLIST api response is......"+response.toString())
                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"getdoctorlist api response success one one one is......")
                        // _message.value= response.message
                        //  sharedPreferences.edit { putInt("COUNT",0) }
                        _prescriptionlist.value=  response.data!! as MutableList<PrescriptionList>
                        if( _prescriptionlist.value!!.size>0)
                        {
                            _docname.value= "By: Dr."+_prescriptionlist.value!!.get(0).doc_name
                            _date.value= _prescriptionlist.value!!.get(0).pre_date
                            _notifyItem.value=1

                        }
                        else
                        {
                            _docname.value=""
                        }

                        //   _GetOfflinelist.value=  response.data!!.get(0).offline_doctor_array as MutableList<DoctorList>
                        //   _message.value= response.message!!.toString()
                    }
                    else
                    {
                        _docname.value=""
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