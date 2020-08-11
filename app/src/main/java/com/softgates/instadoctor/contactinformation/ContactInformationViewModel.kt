package com.softgates.instadoctor.contactinformation

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.AddValue
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import com.softgates.instadoctor.util.ValidationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContactInformationViewModel (val sharedPreferences: SharedPreferences,
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

    private val _addlist = MutableLiveData<List<AddValue>>()
    val addlist: LiveData<List<AddValue>>
        get() = _addlist

    private val getlist = ArrayList<AddValue>()

    private var _notifyItem = MutableLiveData<Int>()
    val notifyItem: LiveData<Int>
        get() = _notifyItem

    init {
        getlist!!.add(AddValue("Medicine One"))
        getlist!!.add(AddValue("Medicine Two"))
//        getlist!!.add(SymptomList(3,"Headech",0))
//        getlist!!.add(SymptomList(4,"Sore Throat",0))
//        getlist!!.add(SymptomList(5,"Nasal Congestion",0))
//        getlist!!.add(SymptomList(6,"Rash",0))
        _addlist.value = getlist
        Log.e("APIRESPONSE", "wishlist api is called..." )
        //   _symptomlist.value = doctorlist
    }


    fun login()
    {
        Log.e("CONTACTINFORMATIONAPI","address img img information api is.....")

    }

    fun addValue()
    {
        getlist!!.add(AddValue(""))
        _notifyItem.value=getlist!!.size
    }

    fun contactinformationApi(address:String,addresstwo:String,city:String,state:String,zipcode:String,email:String,phoneno:String,password:String)
    {
        Log.e("CONTACTINFORMATIONAPI","address information api is....."+address.toString())
        Log.e("CONTACTINFORMATIONAPI","addresstwo information api is....."+addresstwo.toString())
        Log.e("CONTACTINFORMATIONAPI","city information api is....."+city.toString())
        Log.e("CONTACTINFORMATIONAPI","state information api is....."+state.toString())
        Log.e("CONTACTINFORMATIONAPI","zipcode information api is....."+zipcode.toString())
        Log.e("CONTACTINFORMATIONAPI","email information api is....."+email.toString())
        Log.e("CONTACTINFORMATIONAPI","phone information api is....."+phoneno.toString())
        Log.e("CONTACTINFORMATIONAPI","password information api is....."+password.toString())
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var token=sharedPreferences.getString(Constant.USERTOKEN,"")
            var oldemail=sharedPreferences.getString(Constant.USEREMAIL,"")
            Log.e("CONTACTINFORMATIONAPI","token information api is....."+token.toString())
            Log.e("CONTACTINFORMATIONAPI","oldemail information api is....."+oldemail.toString())
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.contactinformation("change_contact",token.toString(),oldemail.toString(),email.toString(),phoneno.toString(),address.toString(),addresstwo.toString(),city.toString(),state.toString(),zipcode.toString(),password)
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())

                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"registration api response success one one one is......")
                        // _message.value= response.message
                        //  sharedPreferences.edit { putInt("COUNT",0) }
                        sharedPreferences.edit { putString(Constant.USEREMAIL, response.data!!.patient_email.toString()) }
                        sharedPreferences.edit { putString(Constant.USERPHONE, response.data!!.phone.toString()) }
                        sharedPreferences.edit { putString(Constant.USERADDRESSONE, response.data!!.address.toString()) }
                        sharedPreferences.edit { putString(Constant.USERADDRESSTWO, response.data!!.address2.toString()) }
                        sharedPreferences.edit { putString(Constant.USERCITY, response.data!!.city.toString()) }
                        sharedPreferences.edit { putString(Constant.USERSTATE, response.data!!.state.toString()) }
                        sharedPreferences.edit { putString(Constant.USERZIPCODE, response.data!!.zipcode.toString()) }
                    //mm    _navigateActivity.value=1
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