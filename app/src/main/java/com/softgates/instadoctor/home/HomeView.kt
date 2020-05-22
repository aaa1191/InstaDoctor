package com.softgates.instadoctor.home

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.HomeViewBinding

class HomeView: Fragment() {

    lateinit var binding: HomeViewBinding
    private lateinit var viewModel : HomeViewModel
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var offlinelinearLayoutManager: LinearLayoutManager
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<HomeViewBinding>(
            inflater, R.layout.home_view, container, false)

        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences("dd", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        offlinelinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.onlinerecyclerview?.setLayoutManager(linearLayoutManager)
        binding.offlinerecyclerview?.setLayoutManager(offlinelinearLayoutManager)

        val adapter = HomeAdapter(OnClick { data, type, position ->
            Log.e("CHECKDATA","checkdata is called....called..called")
            if(type==1)
            {

                this.findNavController().navigate(
                    //  CategoryViewDirections.actionCategoryView2ToSubCategoryView())
                    HomeViewDirections.actionHomeToDoctorProfileFragment())
                viewModel.complete()
            }
        })

        viewModel.GetVerblist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
        binding.onlinerecyclerview?.adapter = adapter
        binding.offlinerecyclerview?.adapter = adapter
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}