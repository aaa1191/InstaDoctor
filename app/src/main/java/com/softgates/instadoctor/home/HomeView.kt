package com.softgates.instadoctor.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.activity.LoginActivity
import com.softgates.instadoctor.databinding.HomeViewBinding
import com.softgates.instadoctor.network.DoctorList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.ProgressDialog

class HomeView : Fragment() {

    lateinit var binding: HomeViewBinding
    private lateinit var viewModel : HomeViewModel
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var offlinelinearLayoutManager: LinearLayoutManager
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var loader: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<HomeViewBinding>(
            inflater, R.layout.home_view, container, false)
        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        offlinelinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.onlinerecyclerview?.setLayoutManager(linearLayoutManager)
        binding.offlinerecyclerview?.setLayoutManager(offlinelinearLayoutManager)
        Log.e("DOCTORLIST","sharedpreference value is called....."+sharedPreferences.getString(Constant.LOGINSIGNUPCHECK,"0"))
        val adapter = HomeAdapter(OnClick { data, type, position ->
            Log.e("CHECKDATA","checkdata is called....called..called")
            if(type==1)
            {
             /*   val action = HomeViewDirections.actionHomeToDoctorProfileFragment()
                action.doctorlist = data as DoctorList
                NavHostFragment.findNavController(this).navigate(action)
                viewModel.complete()*/
               /* if(sharedPreferences.getString(Constant.LOGINSIGNUPCHECK,"").equals("1"))
                {
                    Log.e("DOCTORLIST","doctorlist is...."+data.toString())
                  //  val action = HomeViewDirections.actionHomeToDoctorProfileFragment()
                  //  action.doctorlist = data as DoctorList
                  //  NavHostFragment.findNavController(this).navigate(action)
                  //  viewModel.complete()
                }
                else
                {
                    Toast.makeText(activity as AppCompatActivity,"Login first", Toast.LENGTH_SHORT).show()
                    (activity as HomeActivity).loginView()

                }
                */
            /*    val action = HomeViewDirections.actionHomeToDoctorProfileFragment()
                action.doctorlist = data as DoctorList
                NavHostFragment.findNavController(this).navigate(action)
                viewModel.complete()*/
            }
        })

        viewModel.GetOnlinelist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
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

            Log.e("DOCTORLIST","sharedpreference value is called....."+sharedPreferences.getString(Constant.LOGINSIGNUPCHECK,"0"))

            if(sharedPreferences.getString(Constant.LOGINSIGNUPCHECK,"0").equals("1"))
            {

              Log.e("DOCTORLIST","doctorlist is called called called called....")


                  val action = HomeViewDirections.actionHomeToWhoVisitView()
               //   action.doctorlist = data as DoctorList
                  NavHostFragment.findNavController(this).navigate(action)
                //  viewModel.complete()
            }
            else
            {
           //     Toast.makeText(activity as AppCompatActivity,"Login first", Toast.LENGTH_SHORT).show()
                (activity as HomeActivity).loginView()
            }

        //    (activity as HomeActivity).loginView()
        }
        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}