package com.softgates.instadoctor.doctorprofile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.DoctorprofilefragmentBinding
import com.softgates.instadoctor.feltway.FeltWayViewDirections
import com.softgates.instadoctor.network.DoctorList
import kotlin.reflect.jvm.internal.impl.load.java.Constant

class DoctorProfileFragment : Fragment() {

    lateinit var binding: DoctorprofilefragmentBinding
    private lateinit var viewModel : DoctorProfileViewModel
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var doctorlist:DoctorList
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<DoctorprofilefragmentBinding>(
            inflater, R.layout.doctorprofilefragment, container, false)
        doctorlist = DoctorProfileFragmentArgs.fromBundle(arguments!!).doctorlist as DoctorList
        Log.e("RESPONSEDOCTOR","doctorlist....."+doctorlist.toString())
        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(com.softgates.instadoctor.util.Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = DoctorProfileViewModelFactory(sharedPreferences, application,doctorlist)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DoctorProfileViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.ratingrecyclerview?.setLayoutManager(linearLayoutManager)

        (activity as HomeActivity).whiteStatusbar()

        val adapter = DoctorProfileAdapter(OnClick { data, type, position ->
        })

        viewModel.GetRatinglist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }

        binding.meetupbtn.setOnClickListener {
            sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCID,doctorlist.id) }
            sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCNAME,doctorlist.doc_name) }
            sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCPROFILE,doctorlist.doc_image_link) }
            sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.MEETINGTYPE,com.softgates.instadoctor.util.Constant.MEET) }
            val action = DoctorProfileFragmentDirections.actionDoctorProfileFragmentToPaymentSummeryView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.book.setOnClickListener {
            sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCID,doctorlist.id) }
            sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCPROFILE,doctorlist.doc_image_link) }
            sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.DOCNAME,doctorlist.doc_name) }
            sharedPreferences.edit { putString(com.softgates.instadoctor.util.Constant.MEETINGTYPE,com.softgates.instadoctor.util.Constant.BOOK) }
            val action = DoctorProfileFragmentDirections.actionDoctorProfileFragmentToScheduleAppointmentView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.ratingrecyclerview?.adapter = adapter
        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}