package com.softgates.instadoctor.scheduleappointment

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.AvailabilityList
import com.softgates.instadoctor.network.MyChildList
import com.softgates.instadoctor.network.ScheduleList
import com.softgates.instadoctor.network.SymptomList
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.InstaDoctorApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.text.Typography.times

class ScheduleAppointmentModel (val sharedPreferences: SharedPreferences,
                                application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus?>
        get() = _status

    private val _message = MutableLiveData<String>()
    val message: LiveData<String?>
        get() = _message

    private val _displaytime = MutableLiveData<ScheduleList>()
    val displaytime: LiveData<ScheduleList?>
        get() = _displaytime
    private val getdisplaytime = ArrayList<ScheduleList>()


    private val _myavailabilitylist = MutableLiveData<List<AvailabilityList>>()
    val Myavailabilitylist: LiveData<List<AvailabilityList>>
        get() = _myavailabilitylist

    private val _timelist = MutableLiveData<List<ScheduleList>>()
    val Timelist: LiveData<List<ScheduleList>>
        get() = _timelist
    private val timelist = ArrayList<ScheduleList>()


    private val getVerblist = ArrayList<MyChildList>()

    private var _notifyItem = MutableLiveData<Int>()
    val notifyItem: LiveData<Int>
        get() = _notifyItem

    private var _notifyRecyclerviewItem = MutableLiveData<Int>()
    val notifyRecyclerviewItem: LiveData<Int>
        get() = _notifyRecyclerviewItem

    init {
        Log.e("APIRESPONSE","wishlist api is called...")
       // availabilityApi()
        val input: String = "sun,mon,tue"
        println("=== String into List ===")
        var result: List<String> = input.split(",").map { it.trim() }
        var timeam:String="12PM"
        timeam = timeam.replace("PM","")
        Log.e("FINALTIME","timeam value value timeam pm pm pm pm pm....."+timeam.toString())
        getVerblist!!.add(MyChildList("dffdffdfd","dfdfdfd"))
        //    getVerblist!!.add(MyChildList("dfdf","dfdfd"))
        //     _mychildlist.value = getVerblist

        result.forEach {
            println(it)
            Log.e("LISTDATA","list data is....."+it)
            if(it.equals("sun"))
            {
                getdisplaytime!!.add(ScheduleList("dffdffdfd"))
                //    getVerblist!!.add(MyChildList("dfdf","dfdfd"))
                //     _mychildlist.value = getVerblist
            }
        }
    //    setTime()
        availabilityApi()
    }

    fun selectTime(product: ScheduleList, index:Int)
    {

       for(i in _timelist.value!!.indices)
       {
           if(_timelist.value!!.get(i).tick == 1)
           {
               _timelist.value!!.get(i).tick=0
           }
       }

        Log.e("UPDATETICK","update tick is called......"+ _timelist.value!!.get(index).tick)
        if(_timelist.value!!.get(index).tick==0)
        {
            _timelist.value!!.get(index).tick=1
        }
        else{
            _timelist.value!!.get(index).tick=0
        }
        _timelist.value=  _timelist.value
        _notifyRecyclerviewItem.value=index
    }


    fun complete() {

    }



    fun availabilityApi()
    {

        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var token=sharedPreferences.getString(Constant.USERTOKEN,"")
            var docid=sharedPreferences.getString(Constant.DOCID,"")

            Log.e(Constant.APIRESPONSE,"availabilityApi token response is......"+token.toString())
            Log.e(Constant.APIRESPONSE,"availabilityApi docid response is......"+docid.toString())


            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = InstaDoctorApi.retrofitService.availability("get_availablity",token.toString(),docid.toString())
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"availabilityApi api response is......"+response.toString())
                    if(response.status == Constant.SUCCEESSSTATUSTWOHUNDRED)
                    {
                        Log.e(Constant.APIRESPONSE,"availabilityApi api response success one one one is......")
                        // _message.value= response.message
                        //  sharedPreferences.edit { putInt("COUNT",0) }
                        _myavailabilitylist.value=  response.data!! as MutableList<AvailabilityList>

                        if(_myavailabilitylist!!.value!!.size>0)
                        {
                            _notifyItem.value=1
                        }
/*
                            for (data in _myavailabilitylist.value!!.indices) {
                                 val activedays: String =
                                     _myavailabilitylist.value!!.get(data).active_days!!
                                println("=== String into List ===")
                                var resultactivedays: List<String> =
                                    activedays.split(",").map { it.trim() }
                                resultactivedays.forEach {
                                    println(it)
                                    Log.e("LISTDATA", "list data is....." + it)

                                    var startampm : String="AM"
                                    var endampm : String="AM"
                                    Log.e("LISTDATA", "list data is....."+startampm)


                                    if (it.equals("sun")) {

                                        var startTime:String=_myavailabilitylist.value!!.get(data).start_time!!

                                   //     setTime(_myavailabilitylist.value!!.get(data).start_time!!,_myavailabilitylist.value!!.get(data).end_time!!)
                                          if(startTime.contains("AM", ignoreCase = true))
                                          {
                                              startTime = startTime.replace("AM","")
                                              Log.e("STARTTIME","start time value is true true true......"+startTime)
                                          }
                                          else
                                          {
                                              startampm="PM"
                                              startTime = startTime.replace("PM","")
                                              Log.e("STARTTIME","start time value is true true true......"+startTime)
                                          }

                                        var endTime:String=_myavailabilitylist.value!!.get(data).end_time!!

                                    }
                                }
                            }
*/
                    }
                    else
                    {
                        _message.value= response.message!!.toString()
                    }
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"availabilityApi api failure is......"+e.toString())
                }
            }
        }
    }


    fun  setDays(days:String)
    {
     //   Log.e("FINALSELECTDAYS","days is called...."+days.toString())
        timelist.clear()
        if(_myavailabilitylist.value!=null)
        for (data in _myavailabilitylist.value!!.indices) {
            val activedays: String =
                _myavailabilitylist.value!!.get(data).active_days!!
         //   Log.e("FINALSELECTDAYS","active days....."+activedays.toString())
            var resultactivedays: List<String> =
                activedays.split(",").map { it.trim() }
            resultactivedays.forEach {

                Log.e("FINALSELECTDAYS","it equals....."+it.toString()+"...match days.."+days)

                if (it.equals(days,true)) {
                    var startTime:String=_myavailabilitylist.value!!.get(data).start_time!!
                    var endtime:String=_myavailabilitylist.value!!.get(data).end_time!!
                    Log.e("FINALSELECTDAYS","startTime startTime....."+startTime.toString())
                    Log.e("FINALSELECTDAYS","endtime endtime....."+endtime.toString())
                    val sdf1: DateFormat = SimpleDateFormat("hh:mm aa")
                    try {
                        val startdate = sdf1.parse(startTime)
                        println("Date and Time: $startdate")
                        val enddate = sdf1.parse(endtime)
                        println("Date and endtime: $enddate")
                        val sdf = SimpleDateFormat("hh:mm a")
                        val calendar = GregorianCalendar.getInstance()
                        calendar.time = startdate
                        if (calendar.time.before(enddate)) {
                            Log.e("FINALSELECTDAYS","add add time....."+sdf.format(calendar.time))

                            timelist.add(ScheduleList(sdf.format(calendar.time)))
                            println("Initial time:  $times")
                            while (calendar.time.before(enddate)) {
                                calendar.add(Calendar.MINUTE, 30)
                                timelist.add(ScheduleList(sdf.format(calendar.time)))
                                println(times)
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
        }

        }
        // val startime = "12:00 PM"
   //     val startime = startTime.toString()
      //     val endtime = "9:00 PM"
       // val endtime = endTime.toString()


        for (i in timelist.indices) {
            Log.e("CALENDERDATA", "time is....." + timelist.get(i).toString())
        }

        _timelist.value = timelist
    }
}