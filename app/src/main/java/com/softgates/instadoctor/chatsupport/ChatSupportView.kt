package com.softgates.instadoctor.chatsupport

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
import com.softgates.instadoctor.databinding.ChatsupportViewBinding
import com.softgates.instadoctor.databinding.RatingviewModelBinding
import com.softgates.instadoctor.ratingview.RatingViewModel
import com.softgates.instadoctor.ratingview.RatingViewModelFactory
import com.softgates.instadoctor.ratingview.Rating_ViewDirections
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.ProgressDialog

class ChatSupportView : Fragment() {

    lateinit var binding: ChatsupportViewBinding
    private lateinit var viewModel : ChatSupportViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var loader: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<ChatsupportViewBinding>(
            inflater, R.layout.chatsupport_view, container, false)
        sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        sharedPreferences.edit { putBoolean(Constant.VIDEODISCONNECT,false)}
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ChatSupportViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatSupportViewModel::class.java)

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

        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }

        binding.submit.setOnClickListener {

        }

        sharedPreferences.edit { putBoolean(com.softgates.instadoctor.util.Constant.DELIVERYBUTTONVISIBLE,false)}

        if(!sharedPreferences.getBoolean(Constant.RATINGBUTTONVISIBLE,false))
        {
            binding.submit.isEnabled = true
        }
        else
        {
            binding.submit.isEnabled = false
        }
        viewModel.navigateActivity.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == 1) {
                    sharedPreferences.edit { putBoolean(Constant.RATINGBUTTONVISIBLE,true)}
                    viewModel.complete()
                    val action = Rating_ViewDirections.actionRatingViewModelToPrescriptionView()
                    NavHostFragment.findNavController(this).navigate(action)                }
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