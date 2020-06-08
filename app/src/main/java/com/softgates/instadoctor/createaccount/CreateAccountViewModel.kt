package com.softgates.instadoctor.createaccount

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

class CreateAccountViewModel (val sharedPreferences: SharedPreferences,
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


    private val _email = MutableLiveData<String>()
    val email : LiveData<String?>
        get() = _email


    private val _password = MutableLiveData<String>()
    val password : LiveData<String?>
        get() = _password



    private val _termCondition= MutableLiveData<Boolean>()
    val termCondition: LiveData<Boolean>
        get() = _termCondition

    private val _gender= MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    private val _setDate= MutableLiveData<String>()
    val setDate: LiveData<String>
        get() = _setDate

    private val _navigateActivity = MutableLiveData<Int>()
    val navigateActivity : LiveData<Int?>
        get() = _navigateActivity

    init {
        _name.value=""
        _email.value=""
        _password.value=""
        _setDate.value=""
        _gender.value=""
        _termCondition.value=false
        _navigateActivity.value=0
    }


    fun setTermCondition(value:Boolean)
    {
        _termCondition.value = value
    }

    fun setGender(value:String)
    {
        _gender.value = value
    }

    fun setDate(dob:String)
    {
        _setDate.value= dob.toString()

    }


    fun complete() {

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

    fun setErrormsg(errorTxt:String)
    {
        _message.value=errorTxt.toString()
    }

    fun register()
    {
        when {
            _gender.value!!.isEmpty() -> _message.value="The gender type is required."
            _name.value!!.isEmpty() -> _message.value="The name field is required."
            _email.value!!.isEmpty() -> _message.value="The email field is required."
            !ValidationUtil.isEmailValid(_email.value.toString()) ->_message.value="The Email address is not valid"
            _setDate.value!!.isEmpty() -> _message.value="The Date of birth is required."
            _password.value!!.isEmpty() -> _message.value="The password field is required."
            _password.value!!.length<6 -> _message.value="The password length is less than is required."
            !termCondition.value!! -> _message.value="In order to proceed, you need to accept our Terms and Conditions"
            else -> registerApi()
        }
    }

    fun registerApi()
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
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.register("register",_name.value.toString(),email.value.toString(),password.value.toString(),_gender.value.toString(),_setDate.value.toString(),"android","eyJ0eXAiOiJKV1QiLCJh","2")
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())

                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"registration api response success one one one is......")

                        // _message.value= response.message
                      //  _navigateVerification.value=1
                      _message.value= response.message!!.toString()
                        _navigateActivity.value =1

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