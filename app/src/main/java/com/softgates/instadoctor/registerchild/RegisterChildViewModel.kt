package com.softgates.instadoctor.registerchild

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
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

class RegisterChildViewModel (val sharedPreferences: SharedPreferences,
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


    private val _name = MutableLiveData<String>()
    val name : LiveData<String?>
        get() = _name

    private val _navigateActivity = MutableLiveData<Int>()
    val navigateActivity : LiveData<Int?>
        get() = _navigateActivity

    private val _year = MutableLiveData<Int>()
    val year : LiveData<Int?>
        get() = _year

    private val _month = MutableLiveData<Int>()
    val month : LiveData<Int?>
        get() = _month

    private val _gender= MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    init {
        _name.value=""
        _year.value=0
        _month.value=0
        _navigateActivity.value=0
    }

    fun complete() {

    }

    fun setGender(value:String)
    {
        _gender.value = value
    }

    fun onTextChangedName(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _name.value= s.toString()
        }
        else
        {
            _name.value=""
        }
    }

    fun setYear(year:Int)
    {
        _year.value=year
    }

    fun setMonth(month:Int)
    {
        _month.value=month
    }

    fun addchild()
    {
        when {
            _name.value!!.isEmpty() -> _message.value="The name field is required."
            _year.value!! ==0 -> _message.value="The year is required."
            _month.value!! ==0 -> _message.value="The month is required."
            // !ValidationUtil.isEmailValid(_email.value.toString()) ->_message.value="The Email address is not valid"
            else -> registerChildApi()
        }
    }

    fun registerChildApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var token=sharedPreferences.getString(Constant.USERTOKEN,"")
            val email=sharedPreferences.getString(Constant.USEREMAIL,"")
            Log.e(Constant.APIRESPONSE,"addChild token response is......"+token.toString())
            Log.e(Constant.APIRESPONSE,"addChild name response is......"+name.value.toString())
            Log.e(Constant.APIRESPONSE,"addChild year response is......"+year.value.toString())
            Log.e(Constant.APIRESPONSE,"addChild month response is......"+month.toString())
            Log.e(Constant.APIRESPONSE,"addChild email response is......"+email.toString())
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                //Log.e("RESPONSE","email...."+email.value.toString()+"...pin...")
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.registerChild("add_child",token.toString(),name.value.toString(),year.value.toString(),month.value.toString(),email.toString(),"2020-07-07",gender.value.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())
                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"registration child api response success one one one is......"+response.toString())
                        sharedPreferences.edit { putInt(Constant.CHILDID,response.data!!.get(0).child_id!!.toInt()) }
                        sharedPreferences.edit { putInt(Constant.NOOFPATIENTCHILD, response.data!!.get(0).child_count!!.toInt()) }

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
                    Log.e(Constant.APIRESPONSE,"registration child api failure is......"+e.toString())
                }
            }
        }
    }
}