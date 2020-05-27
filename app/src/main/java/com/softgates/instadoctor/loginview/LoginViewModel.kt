package com.softgates.instadoctor.loginview

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

class LoginViewModel (val sharedPreferences: SharedPreferences,
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

    private val _email = MutableLiveData<String>()
    val email : LiveData<String?>
        get() = _email

    private val _navigateActivity = MutableLiveData<Int>()
    val navigateActivity : LiveData<Int?>
        get() = _navigateActivity

    private val _password = MutableLiveData<String>()
    val password : LiveData<String?>
        get() = _password

    init {
        _email.value="mk@gmail.com"
        _password.value="123456"
        _navigateActivity.value=0
        Log.e("APIRESPONSE","wishlist api is called...")
    }

    fun complete() {

    }

    fun onTextChangedEmail(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _email.value= s.toString()
        }
        else
        {
            _email.value=""
        }
    }

    fun onTextChangedPassword(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _password.value= s.toString()
        }
        else
        {
            _password.value=""
        }
    }
    fun login()
    {
        when {
            _email.value!!.isEmpty() -> _message.value="The email field is required."
            !ValidationUtil.isEmailValid(_email.value.toString()) ->_message.value="The Email address is not valid"
            _password.value!!.isEmpty() -> _message.value="The password field is required."
            else -> loginApi()
        }
    }

    fun loginApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e("RESPONSE","email...."+email.value.toString()+"...pin...")
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.login("signin",email.value.toString(),password.value.toString(),"3eada8a873b9122ecc10872e8bd568f97c83ca43d36e3dc53db1367c9af8aedffee181aaa9359b22ed96abca65819b74342c90c8775d8dce32c0b9586d54d8d9","2")
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())

                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"registration api response success one one one is......")
                        // _message.value= response.message
                        //  sharedPreferences.edit { putInt("COUNT",0) }
                        sharedPreferences.edit { putString(Constant.USERTOKEN, response.data!!.token.toString()) }
                        sharedPreferences.edit { putString(Constant.USEREMAIL, response.data!!.patient_email.toString()) }
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