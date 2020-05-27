package com.softgates.instadoctor.createaccount

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.LoginActivity
import com.softgates.instadoctor.databinding.CreateAccountViewBinding
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.ProgressDialog
import com.softgates.instadoctor.util.ValidationUtil
import java.util.*


class CreateAccountView : Fragment() {

    lateinit var binding: CreateAccountViewBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager

    val c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)

    private lateinit var viewModel : CreateAccountViewModel
    private lateinit var loader: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<CreateAccountViewBinding>(
            inflater, R.layout.create_account_view, container, false)
        sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences("dd", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = CreateAccountViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateAccountViewModel::class.java)
        binding.dateofbirtheditxt.setOnClickListener {
            val dpd = DatePickerDialog((activity as AppCompatActivity), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.setDate(""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth)
                Log.e("MONTHDATE","monthdate iss......."+"" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year)
                var date:String= ""+ dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                Log.e("RECHARGE","monthdate iss......."+"" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year)
                binding.dateofbirtheditxt.setText(""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth)
           }, year, month, day)
            dpd.show()
           // dpd.getDatePicker().setMinDate(c.timeInMillis)
        }
        viewModel.message.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

             Toast.makeText(activity as AppCompatActivity,it.toString(), Toast.LENGTH_SHORT).show()

        })
        viewModel.status.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                ApiStatus.LOADING -> {
                    loader.setLoading(true)
                }
                ApiStatus.ERROR -> {
                    loader.setLoading(false)
                }
                ApiStatus.DONE -> {
                    loader.setLoading(false)
                }
            }
        })


        binding.accepttermcondition.setOnClickListener {
            if(binding.accepttermcondition.isChecked)
            {
                viewModel.setTermCondition(true)
            }
            else
            {
                viewModel.setTermCondition(false)
            }
        }

        binding.malebtn.setOnClickListener {

            binding.malebtn.setCardBackgroundColor(resources.getColor(R.color.gray))
            binding.female.setCardBackgroundColor(resources.getColor(R.color.yellow))
            binding.maletxt.setTextColor(resources.getColor(R.color.yellow))
            binding.femaletxt.setTextColor(resources.getColor(android.R.color.white))
            viewModel.setGender("Male")
        }

        binding.female.setOnClickListener {
            binding.female.setCardBackgroundColor(resources.getColor(R.color.gray))
            binding.malebtn.setCardBackgroundColor(resources.getColor(R.color.yellow))
            binding.maletxt.setTextColor(resources.getColor(android.R.color.white))
            binding.femaletxt.setTextColor(resources.getColor(R.color.yellow))
            viewModel.setGender("Female")
        }

        viewModel.navigateActivity.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                if (it == 1) {
                    Log.e("LOGINACTIVITY","login activity calss is called")
                    (activity as LoginActivity).logout()
                }
            }
        })

        binding.createaccount.setOnClickListener {
            if(viewModel.gender.value!!.isEmpty())
            {
                viewModel.setErrormsg("The gender type is required.")
            }
            else if(viewModel.name.value!!.isEmpty())
            {
                binding.nameedittxt.setError("The name field is required.");
                binding.name.clearFocus()
            }
            else if(viewModel.email.value!!.isEmpty())
            {
                binding.emailedittxt.setError("The email field is required.");
                binding.email.clearFocus()
            }
            else if(!ValidationUtil.isEmailValid(viewModel.email.value.toString()))
            {
                binding.emailedittxt.setError("The Email address is not valid");
                binding.email.clearFocus()
            }
            else if(viewModel.setDate.value!!.isEmpty())
            {
                viewModel.setErrormsg("The Date of birth is required.")
            }
            else if(viewModel.password.value!!.isEmpty())
            {
                binding.passwordtxt.setError("The password field is required.");
                binding.password.clearFocus()
            }
            else if(viewModel.password.value!!.length<6)
            {
                binding.passwordtxt.setError("The password length is less than is required.");
                binding.password.clearFocus()
            }
            else if(!viewModel.termCondition.value!!)
            {
                viewModel.setErrormsg("In order to proceed, you need to accept our Terms and Conditions")
            }
            else
            {
                viewModel.registerApi()
            }
        }

        binding.name.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.nameedittxt.error = null

            }
        })

        binding.email.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.emailedittxt.error = null

            }
        })
        binding.password.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.passwordtxt.error = null
            }
        })

        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}