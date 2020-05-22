package com.softgates.instadoctor.passwordinformation

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

class PasswordInformationViewModel (val sharedPreferences: SharedPreferences,
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





    private val _oldPassword = MutableLiveData<String>()
    val oldPassword : LiveData<String?>
        get() = _oldPassword

    private val _newPassword = MutableLiveData<String>()
    val newPassword : LiveData<String?>
        get() = _newPassword

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword : LiveData<String?>
        get() = _confirmPassword

    private val _navigateActivity = MutableLiveData<Int>()
    val navigateActivity : LiveData<Int?>
        get() = _navigateActivity


    init {
        _oldPassword.value=""
        _newPassword.value=""
        _confirmPassword.value=""
        _navigateActivity.value=0
        Log.e("APIRESPONSE","wishlist api is called...")
    }







    fun complete() {

    }

    fun onTextChangedOldPass(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _oldPassword.value= s.toString()
        }
        else
        {
            _oldPassword.value=""
        }
    }

    fun onTextChangedNewPass(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _newPassword.value= s.toString()
        }
        else
        {
            _newPassword.value=""
        }
    }

    fun onTextChangedConfirmPass(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _confirmPassword.value= s.toString()
        }
        else
        {
            _confirmPassword.value=""
        }
    }


    fun changepassword()
    {
        val matchPassword : Boolean =(newPassword.value == confirmPassword.value)
        Log.e("CHECKDATA","changepassword view......"+sharedPreferences.getString(Constant.USERTOKEN,""))
        when {
            oldPassword.value!!.isEmpty() -> _message.value="Old Password field is mandatory"
            newPassword.value!!.isEmpty() -> _message.value="New Password field is mandatory"
            newPassword.value!!.length<6 -> _message.value="New Password field length is minimum six"
            confirmPassword.value!!.isEmpty() -> _message.value="Confirm Password field is mandatory"
            !matchPassword ->  _message.value="Confirm Password does not match"
            else -> changepasswordApi()
        }
    }

    fun changepasswordApi()
    {

        var token=sharedPreferences.getString(Constant.USERTOKEN,"")
        var email=sharedPreferences.getString(Constant.USERTOKEN,"")

        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.changepassword("change_password",oldPassword.value.toString(),newPassword.value.toString(),email.toString(),token.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"registration api response is......"+response.toString())

                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"registration api response success one one one is......")

                        // _message.value= response.message
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