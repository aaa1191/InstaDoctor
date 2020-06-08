package com.softgates.instadoctor.scheduleappointment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.ScheduleappointmentViewBinding
import com.softgates.instadoctor.util.Constant
import java.time.Month
import java.time.Year
import java.util.*


class ScheduleAppointmentView : Fragment() {

    lateinit var binding: ScheduleappointmentViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager


    val c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<ScheduleappointmentViewBinding>(
            inflater, R.layout.scheduleappointment_view, container, false)
        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)


        binding.schedulbtn.setOnClickListener {
            val action = ScheduleAppointmentViewDirections.actionScheduleAppointmentViewToPaymentSummeryView()
            NavHostFragment.findNavController(this).navigate(action)
        }

  //     val c = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        //  mYear =mYear-18;
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)
        c.set(year, month, day)

        val milliTime: Long = c.getTimeInMillis()
        binding.calender.setDate(milliTime,true,true);
        binding.calender.setOnDateChangeListener { view, year, month, dayOfMonth ->
        Log.e("SELECTEDDATE","selecteddate....."+year+"...month...."+month+"...dayofmonth...."+dayOfMonth)

        }
      /*  binding.calender.setOnDateChangeListener(CalendarView.OnDateChangeListener() { calendarView: CalendarView, i: Int, i1: Int, i2: Int ->
            @Override
            fun onSelectedDayChange(view:CalendarView , year:Int, month:Int, dayOfMonth:Int) {

            }
        });*/
      binding.ten.setOnClickListener {
          sharedPreferences.edit { putString(Constant.CHILDSTATUS,"") }
          binding.ten.setBackgroundResource(R.drawable.time_selected_drawable)
      }
     return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}