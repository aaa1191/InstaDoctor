package com.softgates.instadoctor.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.LoginViewBinding
import com.softgates.instadoctor.databinding.ProfileViewBinding
import com.softgates.instadoctor.loginview.LoginViewDirections

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

        binding.termcondition.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileToTermConditionFragment()
            NavHostFragment.findNavController(this).navigate(action)

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
            val action = ProfileFragmentDirections.actionProfileToSeeDoctorView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.paymentmethod.setOnClickListener {
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}