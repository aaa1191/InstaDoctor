package com.softgates.instadoctor.deliveryrequest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.DeliveryreqViewBinding
import com.softgates.instadoctor.databinding.FaqfragmentViewBinding
import com.softgates.instadoctor.ratingview.Rating_ViewDirections
import com.softgates.instadoctor.selectsymptom.SymptomViewModel
import com.softgates.instadoctor.selectsymptom.SymptomViewModelFactory
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.ProgressDialog
import kotlin.reflect.jvm.internal.impl.load.java.Constant

class DeliveryRequest_View : Fragment() {

    lateinit var binding: DeliveryreqViewBinding
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel : DeliveryRequestModel
    private lateinit var loader: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<DeliveryreqViewBinding>(
            inflater, R.layout.deliveryreq_view, container, false)

        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(com.softgates.instadoctor.util.Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = DeliveryRequestModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliveryRequestModel::class.java)

      /*  binding.request.setOnClickListener {
            val action = DeliveryRequest_ViewDirections.actionDeliveryRequestViewToShareReviewView()
            NavHostFragment.findNavController(this).navigate(action)
        }*/

        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
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

        viewModel.navigateActivity.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == 1) {
                    sharedPreferences.edit { putBoolean(com.softgates.instadoctor.util.Constant.DELIVERYBUTTONVISIBLE,true)}
                    viewModel.complete()
                    val action = DeliveryRequest_ViewDirections.actionDeliveryRequestViewToShareReviewView()
                    NavHostFragment.findNavController(this).navigate(action)              }
            }

        })

        if(!sharedPreferences.getBoolean(com.softgates.instadoctor.util.Constant.DELIVERYBUTTONVISIBLE,false))
        {
            binding.request.isEnabled = true
        }
        else
        {
            binding.request.isEnabled = false
        }
        
        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}