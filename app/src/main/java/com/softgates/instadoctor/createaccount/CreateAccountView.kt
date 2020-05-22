package com.softgates.instadoctor.createaccount

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.CreateAccountViewBinding
import com.softgates.instadoctor.databinding.LoginViewBinding
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.ProgressDialog
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

            binding.maletxt.setTextColor(resources.getColor(R.color.green))
            binding.femaletxt.setTextColor(resources.getColor(android.R.color.white))
            viewModel.setGender("Male")


        }

        binding.female.setOnClickListener {
            binding.maletxt.setTextColor(resources.getColor(android.R.color.white))
            binding.femaletxt.setTextColor(resources.getColor(R.color.green))
            viewModel.setGender("Female")
        }

        binding.viewModel = viewModel

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}