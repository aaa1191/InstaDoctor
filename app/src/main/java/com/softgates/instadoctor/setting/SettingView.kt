package com.softgates.instadoctor.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.FeltwayViewBinding
import com.softgates.instadoctor.databinding.SettingViewBinding
import com.softgates.instadoctor.feltway.FeltWayViewDirections

class SettingView  : Fragment() {

    lateinit var binding: SettingViewBinding
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<SettingViewBinding>(
            inflater, R.layout.setting_view, container, false)

     /*   binding.nextarrow.setOnClickListener {
            val action = FeltWayViewDirections.actionFeltWayViewToChooseDoctorView()
            NavHostFragment.findNavController(this).navigate(action)
        }*/
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}