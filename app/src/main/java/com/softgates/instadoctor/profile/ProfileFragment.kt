package com.softgates.instadoctor.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.LoginViewBinding
import com.softgates.instadoctor.databinding.ProfileViewBinding
import com.softgates.instadoctor.loginview.LoginViewDirections
import com.softgates.instadoctor.util.Constant

class ProfileFragment : Fragment() {

    lateinit var binding: ProfileViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<ProfileViewBinding>(
            inflater, R.layout.profile_view, container, false)
        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)

        binding.termcondition.setOnClickListener {
           /* val action = ProfileFragmentDirections.actionProfileToTermConditionFragment()
            NavHostFragment.findNavController(this).navigate(action)*/

            val action = ProfileFragmentDirections.actionProfileToWebView(Constant.TERMCONDITION)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.logouttxt.setOnClickListener {
            sharedPreferences.edit().clear().commit()
            (activity as HomeActivity).loginView()
        }

        binding.customersupport.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileToCustomerSupportView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.changepassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileToPasswordinformationView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.contactinformation.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileToContactinformationView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.aboutinstadoctor.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileToWebView(Constant.ABOUTUS)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.paymentmethod.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileToAddPaymentView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.employe.setOnClickListener {
            Toast.makeText(context as AppCompatActivity,resources.getString(R.string.commingsoon),Toast.LENGTH_LONG).show()
            //      val action = ProfileFragmentDirections.actionProfileToRegisterChildView()
       //     NavHostFragment.findNavController(this).navigate(action)
        }

        binding.insurance.setOnClickListener {
            Toast.makeText(context as AppCompatActivity,resources.getString(R.string.commingsoon),Toast.LENGTH_LONG).show()
        }

        binding.privacypolicy.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileToWebView(Constant.PRIVACYPOLICY)
            NavHostFragment.findNavController(this).navigate(action)
        }
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}