package com.softgates.instadoctor.paymentsummery

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.GetTransactionList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import com.softgates.instadoctor.util.ZoomApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PaymentSummeryViewModel(val sharedPreferences: SharedPreferences,
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

    private val _totalPay = MutableLiveData<String>()
    val totalPay : LiveData<String?>
        get() = _totalPay

    private val _orderId = MutableLiveData<String>()
    val orderId : LiveData<String?>
        get() = _orderId

    private val _navigateActivity = MutableLiveData<Int>()
    val navigateActivity : LiveData<Int?>
        get() = _navigateActivity

    private val _GetTransactionlist = MutableLiveData<List<GetTransactionList>>()
    val GetTransactionlist: LiveData<List<GetTransactionList>>
        get() = _GetTransactionlist


    private var _notifyItem = MutableLiveData<Int>()
    val notifyItem: LiveData<Int>
        get() = _notifyItem


    init {
        _msg.value=""
        _totalPay.value="0"
        _navigateActivity.value=0
        getTransactionApi()
    }

    fun complete() {
        _navigateActivity.value=0

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




    fun getTransactionApi()
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

            Log.e(Constant.APIRESPONSE,"getTransactionApi token response is......"+token.toString())
            Log.e(Constant.APIRESPONSE,"getTransactionApi patientid response is......"+patientid.toString())
            Log.e(Constant.APIRESPONSE,"getTransactionApi docid response is......"+docid.toString())

            //  Log.e(Constant.APIRESPONSE,"addPatientDetail cm response is......"+cm.value.toString())
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e("RESPONSE","email...."+msg.value.toString()+"...pin...")
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.getTransaction("get_pre_details",token.toString(),docid.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())

                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"getTransactionApi api response success one one one is......")
                        _message.value= response.message!!.toString()
                        _GetTransactionlist.value=  response.data!! as MutableList<GetTransactionList>
                        _orderId.value=  _GetTransactionlist.value!!.get(0).order_id!!

                     var totalprice =   _GetTransactionlist.value!!.get(0).doc_fee!!.toDouble().plus(_GetTransactionlist.value!!.get(0).vat!!.toDouble()).plus(_GetTransactionlist.value!!.get(0).service_charges!!.toDouble())
                        Log.e(Constant.APIRESPONSE,"totalprice api response is......"+totalprice.toString())
                        _totalPay.value=totalprice.toString()
                        _notifyItem.value= 1
                        // _message.value= response.message
                    }
                    else
                    {
                        _message.value= response.message!!.toString()
                    }
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"getTransactionApi api failure is......"+e.toString())
                }
            }
        }
    }


    fun createAppointmentApi()
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
            var meetingtype=sharedPreferences.getString(Constant.MEETINGTYPE,"")
            var childstatus=sharedPreferences.getInt(Constant.CHILDSTATUS,0)
            var childid:String=""

            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dubai"));
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            val date = SimpleDateFormat("yyyy-MM-dd")
            val time = SimpleDateFormat("hh:mm:a")
            Log.e("Los angeles time   ","date is...."+ date.format(calendar.time))
            Log.e("Los angeles time   ","time is...."+ time.format(calendar.time))
            var appDate:String=date.format(calendar.time)
            var bookingDate=""
            var  appTime=""
            if(childstatus==1)
            {
                childid = sharedPreferences.getInt(Constant.CHILDID,0).toString()
            }

            if(meetingtype.equals(Constant.MEET))
            {
                 bookingDate=time.format(calendar.time)
                 appTime=time.format(calendar.time)

            }
            else
            {
                 bookingDate=sharedPreferences.getString(Constant.BOOKINGDATE,"").toString()
                 appTime=sharedPreferences.getString(Constant.BOOKINGTIME,"").toString()
            }

            Log.e(Constant.APIRESPONSE,"createAppointmentApi token response is......"+token.toString())
            Log.e(Constant.APIRESPONSE,"createAppointmentApi patientid response is......"+patientid.toString())
            Log.e(Constant.APIRESPONSE,"createAppointmentApi docid response is......"+docid.toString())
            Log.e(Constant.APIRESPONSE,"createAppointmentApi meeting type response is......"+meetingtype.toString())

            //  Log.e(Constant.APIRESPONSE,"addPatientDetail cm response is......"+cm.value.toString())
            _status.value = ApiStatus.LOADING
       coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e("RESPONSE","email...."+msg.value.toString()+"...pin...")
                var getPropertiesDeferred = ZoomApi.retrofitService.createAppointment("create_appointment",token.toString(),appDate.toString(),appTime.toString(),docid.toString(),patientid.toString(),bookingDate.toString(),childstatus.toString(),childid.toString(),meetingtype.toString(),orderId.value.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())

                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"getTransactionApi api response success one one one is......")

                        sharedPreferences.edit { putString(Constant.ZOOMID,response.data!!.get(0).id!!.toString()) }
                        sharedPreferences.edit { putString(Constant.ZOOMPASSWORD, response.data!!.get(0).password!!.toString()) }
                        sharedPreferences.edit { putString(Constant.APPID, response.data!!.get(0).app_id!!.toString()) }

                        _message.value= response.message
                        _navigateActivity.value=1

                    }
                    else
                    {
                        _message.value= response.message!!.toString()
                    }
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"getTransactionApi api failure is......"+e.toString())
                }
            }
        }
    }
}