package com.softgates.instadoctor.doctorprofile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.DoctorprofilefragmentBinding
import com.softgates.instadoctor.network.DoctorList

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
        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences("dd", Context.MODE_PRIVATE)
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
        binding.ratingrecyclerview?.adapter = adapter
        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}