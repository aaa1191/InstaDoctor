package com.softgates.instadoctor.loginview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.LoginActivity
import com.softgates.instadoctor.createaccount.CreateAccountViewModel
import com.softgates.instadoctor.createaccount.CreateAccountViewModelFactory
import com.softgates.instadoctor.databinding.LoginViewBinding
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.ProgressDialog

class LoginView : Fragment() {

    lateinit var binding: LoginViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel : LoginViewModel
    private lateinit var loader: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<LoginViewBinding>(
            inflater, R.layout.login_view, container, false)

        sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences("dd", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = LoginViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.createaccount.setOnClickListener {
            val action = LoginViewDirections.actionLoginViewToCreateAccountView()
            //    action.propertyView = data!!
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.forgetpassword.setOnClickListener {
            val action = LoginViewDirections.actionLoginViewToRecoverPasswordView()
            //    action.propertyView = data!!
            NavHostFragment.findNavController(this).navigate(action)
        }

        /*binding.loginbtn.setOnClickListener {
            (activity as LoginActivity).logout()
        }*/

        viewModel.message.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(activity as AppCompatActivity,it.toString(), Toast.LENGTH_SHORT).show()
        })

        viewModel.navigateActivity.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == 1) {
                    (activity as LoginActivity).logout()
                }
            }
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

        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}