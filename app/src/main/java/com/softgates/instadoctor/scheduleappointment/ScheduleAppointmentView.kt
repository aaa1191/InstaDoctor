package com.softgates.instadoctor.scheduleappointment

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
import com.softgates.instadoctor.databinding.ScheduleappointmentViewBinding
import com.softgates.instadoctor.databinding.WhovisitViewBinding
import com.softgates.instadoctor.seecdoctor.SeeDoctorViewDirections
import com.softgates.instadoctor.whovisit.WhoVisitViewDirections

class ScheduleAppointmentView : Fragment() {

    lateinit var binding: ScheduleappointmentViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<ScheduleappointmentViewBinding>(
            inflater, R.layout.scheduleappointment_view, container, false)


        binding.schedulbtn.setOnClickListener {
            val action = ScheduleAppointmentViewDirections.actionScheduleAppointmentViewToThankuScreenView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}