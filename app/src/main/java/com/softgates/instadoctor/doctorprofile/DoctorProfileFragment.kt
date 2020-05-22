package com.softgates.instadoctor.doctorprofile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.DoctorprofilefragmentBinding

class DoctorProfileFragment : Fragment() {

    lateinit var binding: DoctorprofilefragmentBinding
    private lateinit var viewModel : DoctorProfileViewModel
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<DoctorprofilefragmentBinding>(
            inflater, R.layout.doctorprofilefragment, container, false)

        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences("dd", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = DoctorProfileViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DoctorProfileViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.ratingrecyclerview?.setLayoutManager(linearLayoutManager)

        val adapter = DoctorProfileAdapter(OnClick { data, type, position ->

        })

        viewModel.GetVerblist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

        binding.ratingrecyclerview?.adapter = adapter
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}