package com.softgates.instadoctor.recoverpassword

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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.ForgetpasswordViewBinding
import com.softgates.instadoctor.databinding.RecoverpasswordViewBinding
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.ProgressDialog

class RecoverPasswordView  : Fragment() {

    lateinit var binding: RecoverpasswordViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel : RecoverPasswordViewModel
    private lateinit var loader: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<RecoverpasswordViewBinding>(
            inflater, R.layout.recoverpassword_view, container, false)
        sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences("dd", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = RecoverPasswordViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecoverPasswordViewModel::class.java)

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

        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}