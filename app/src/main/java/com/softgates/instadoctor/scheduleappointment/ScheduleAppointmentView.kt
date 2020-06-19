package com.softgates.instadoctor.scheduleappointment

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.LoginActivity
import com.softgates.instadoctor.databinding.ScheduleappointmentViewBinding
import com.softgates.instadoctor.mychild.MyChildAdapter
import com.softgates.instadoctor.mychild.MyChildViewDirections
import com.softgates.instadoctor.mychild.OnClicks
import com.softgates.instadoctor.util.Constant
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ScheduleAppointmentView : Fragment() {

    lateinit var binding: ScheduleappointmentViewBinding
    private lateinit var viewModel : ScheduleAppointmentModel
   // private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: GridLayoutManager


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


        val application = requireNotNull(this.activity).application
        sharedPreferences = (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val viewModelFactory =  ScheduleAppointmentModelFactory(sharedPreferences,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScheduleAppointmentModel::class.java)



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
        c[year, month] = day
        val dayOfWeek = c[Calendar.DAY_OF_WEEK]
        Log.e("SELECTEDDATE","initial date is....."+dayOfWeek)

        val milliTime: Long = c.getTimeInMillis()
   binding.calender.setDate(milliTime,true,true);
        binding.calender.setOnDateChangeListener { view, year, month, dayOfMonth ->

            val calendars = Calendar.getInstance()
            calendars[year, month] = dayOfMonth
            val dayOfWeek = calendars[Calendar.DAY_OF_WEEK]

            Log.e("SELECTEDDATE","initial date is....."+dayOfWeek)

            //setDays(dayOfWeek)
          /*  if(dayOfWeek==Calendar.MONDAY)
            {
                Log.e("SELECTEDDATE","select date MONDAY....."+dayOfWeek)
                viewModel.setDays("mon")
            }
            else if(dayOfWeek==Calendar.TUESDAY)
                    {
                        Log.e("SELECTEDDATE","select date TUESDAY....."+dayOfWeek)
                        viewModel.setDays("tue")
                    }    else if(dayOfWeek==Calendar.WEDNESDAY)
                    {
                        Log.e("SELECTEDDATE","select date WEDNESDAY....."+dayOfWeek)
                        viewModel.setDays("wed")
                    }    else if(dayOfWeek==Calendar.THURSDAY)
                    {
                        Log.e("SELECTEDDATE","select date THURSDAY....."+dayOfWeek)
                        viewModel.setDays("thu")
                    }    else if(dayOfWeek==Calendar.FRIDAY)
                    {
                        Log.e("SELECTEDDATE","select date FRIDAY....."+dayOfWeek)
                        viewModel.setDays("fri")
                    }else if(dayOfWeek==Calendar.SATURDAY)
                    {
                        Log.e("SELECTEDDATE","select date FRIDAY....."+dayOfWeek)
                        viewModel.setDays("sat")
                    }else if(dayOfWeek==Calendar.SUNDAY)
                    {
                        Log.e("SELECTEDDATE","select date FRIDAY....."+dayOfWeek)
                        viewModel.setDays("sun")
                    }*/
        }
        viewModel.notifyItem.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                if (it == 1) {
                    setDays(dayOfWeek)
                }
                }
        })

      binding.ten.setOnClickListener {
          sharedPreferences.edit { putString(Constant.CHILDSTATUS,"") }
          binding.ten.setBackgroundResource(R.drawable.time_selected_drawable)
      }

        linearLayoutManager = GridLayoutManager(context,4)
        binding.availibilityrecyclerview?.setLayoutManager(linearLayoutManager)

        val mychildadapter = ScheduleAppointmentAdapter(com.softgates.instadoctor.scheduleappointment.OnClicks { data, type, position ->


        })

        viewModel.Timelist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mychildadapter.submitList(it)
            mychildadapter.notifyDataSetChanged()
        })

        binding.availibilityrecyclerview?.adapter = mychildadapter

        binding.viewModel = viewModel
        return  binding.root
    }

    fun setDays(dayOfWeek:Int)
    {
        if(dayOfWeek==Calendar.MONDAY)
        {
            Log.e("SELECTEDDATE","select date MONDAY....."+dayOfWeek)
            viewModel.setDays("mon")


        }
        else if(dayOfWeek==Calendar.TUESDAY)
        {
            Log.e("SELECTEDDATE","select date TUESDAY....."+dayOfWeek)
            viewModel.setDays("tue")

        }    else if(dayOfWeek==Calendar.WEDNESDAY)
        {
            Log.e("SELECTEDDATE","select date WEDNESDAY....."+dayOfWeek)
            viewModel.setDays("wed")


        }    else if(dayOfWeek==Calendar.THURSDAY)
        {
            Log.e("SELECTEDDATE","select date THURSDAY....."+dayOfWeek)
            viewModel.setDays("thu")


        }    else if(dayOfWeek==Calendar.FRIDAY)
        {
            Log.e("SELECTEDDATE","select date FRIDAY....."+dayOfWeek)
            viewModel.setDays("fri")


        }else if(dayOfWeek==Calendar.SATURDAY)
        {
            Log.e("SELECTEDDATE","select date FRIDAY....."+dayOfWeek)
            viewModel.setDays("sat")
        }else if(dayOfWeek==Calendar.SUNDAY)
        {
            Log.e("SELECTEDDATE","select date FRIDAY....."+dayOfWeek)
            viewModel.setDays("sun")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}