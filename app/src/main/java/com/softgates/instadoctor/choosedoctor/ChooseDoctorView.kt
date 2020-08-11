package com.softgates.instadoctor.choosedoctor

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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.ChoosedoclistViewBinding
import com.softgates.instadoctor.databinding.ChoosedoctorViewBinding
import com.softgates.instadoctor.databinding.HomeViewBinding
import com.softgates.instadoctor.home.*
import com.softgates.instadoctor.network.DoctorList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.ProgressDialog

class ChooseDoctorView : Fragment() {

    lateinit var binding: ChoosedoctorViewBinding
    private lateinit var viewModel : ChooseDoctorViewModel
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var loader: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<ChoosedoctorViewBinding>(
            inflater, R.layout.choosedoctor_view, container, false)

        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ChooseDoctorViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChooseDoctorViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.onlinerecyclerview?.setLayoutManager(linearLayoutManager)

        val adapter = ChooseDoctorAdapter(OnClick { data, type, position ->
            Log.e("CHECKDATA","checkdata is called....called..called")
            if(type==1)
            {
                /*   val action = HomeViewDirections.actionHomeToDoctorProfileFragment()
                   action.doctorlist = data as DoctorList
                   NavHostFragment.findNavController(this).navigate(action)
                   viewModel.complete()*/

                /*if(sharedPreferences.getString(Constant.LOGINSIGNUPCHECK,"").equals("1"))
                {*/
                    Log.e("DOCTORLIST","doctorlist is...."+data.toString())
                    val action = ChooseDoctorViewDirections.actionChooseDoctorViewToDoctorProfileFragment()
                    action.doctorlist = data as DoctorList
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()

               /* }
                else
                {
                    Toast.makeText(activity as AppCompatActivity,"Login first", Toast.LENGTH_SHORT).show()
                    (activity as HomeActivity).loginView()
                }*/
                /*    val action = HomeViewDirections.actionHomeToDoctorProfileFragment()
                    action.doctorlist = data as DoctorList
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()*/
            }
            else if (type==2)
            {
                if(data.doc_online_status!!.equals("1"))
                {
                    sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCID,data.id.toString()) }
                    sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCNAME,data.doc_name.toString()) }
                    sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.MEETINGTYPE,com.softgates.instadoctor.util.Constant.MEET) }

                    val action = ChooseDoctorViewDirections.actionChooseDoctorViewToPaymentSummeryView()
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()
                }
               else if(data.doc_online_status!!.equals("0"))
                {
                    sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCID,data.id.toString()) }
                    sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCNAME,data.doc_name.toString()) }
                    sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.MEETINGTYPE,com.softgates.instadoctor.util.Constant.BOOK) }
                    val action = ChooseDoctorViewDirections.actionChooseDoctorViewToScheduleAppointmentView()
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()
                }
                else
                {
                    Log.e("DOCTORLIST","doctorlist is...."+data.toString())
                    val action = ChooseDoctorViewDirections.actionChooseDoctorViewToDoctorProfileFragment()
                    action.doctorlist = data as DoctorList
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()
                }
            }
        })

        viewModel.GetOnlinelist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }

        binding.onlinerecyclerview?.adapter = adapter
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

        binding.loginbtn.setOnClickListener {
            (activity as HomeActivity).loginView()
        }
        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}