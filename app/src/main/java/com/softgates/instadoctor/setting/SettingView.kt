package com.softgates.instadoctor.setting

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
import com.softgates.instadoctor.databinding.SettingViewBinding
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.ProgressDialog

class SettingView  : Fragment() {

    lateinit var binding: SettingViewBinding
    private lateinit var vi: View
    private lateinit var viewModel : SettingViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var chatlinearLayoutManager: LinearLayoutManager
    lateinit var sessionlinearLayoutManager: LinearLayoutManager
    lateinit var prescriptionlinearLayoutManager: LinearLayoutManager
    private lateinit var loader: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<SettingViewBinding>(
            inflater, R.layout.setting_view, container, false)
        val application = requireNotNull(this.activity).application
        sharedPreferences = (activity as AppCompatActivity).getSharedPreferences("DD", Context.MODE_PRIVATE)
        val viewModelFactory =  SettingViewModelFactory(sharedPreferences,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingViewModel::class.java)
        binding.viewModel = viewModel
        chatlinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        sessionlinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        prescriptionlinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.sessionrecyclerview?.setLayoutManager(sessionlinearLayoutManager)
        binding.chathistoryrecyclerview?.setLayoutManager(chatlinearLayoutManager)
        binding.prescriptionrecyclerview?.setLayoutManager(prescriptionlinearLayoutManager)

        val chatadapter = ChatAdapter(OnClick { data, type, position ->

        })

        val sessionadapter = SessionAdapter(OnClicks { data, type, position ->

        })

        val prescriptionadapter = PrescriptionAdapter(OnClickss { data, type, position ->

        })

        viewModel.Chatlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            chatadapter.submitList(it)
            chatadapter.notifyDataSetChanged()
        })

        viewModel.Sessionlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            sessionadapter.submitList(it)
            sessionadapter.notifyDataSetChanged()
        })
        viewModel.Prescriptionlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            prescriptionadapter.submitList(it)
            prescriptionadapter.notifyDataSetChanged()
        })

        binding.chathistoryrecyclerview?.adapter = chatadapter
        binding.sessionrecyclerview?.adapter = sessionadapter
        binding.prescriptionrecyclerview?.adapter = prescriptionadapter

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
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }
}