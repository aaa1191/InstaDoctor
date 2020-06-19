package com.softgates.instadoctor.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.softgates.instadoctor.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static kotlin.text.Typography.times;

public class DemoActivity extends AppCompatActivity {
    ArrayList<String> times = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);
        main();

    }
        public  void main()  {
            //
            // A string of time information
            //
//            String time = "9:00";
          //  String startime = "9:00:00";
            String startime = "12:00 PM";
           // String startime = "9:00";
//            String time1 = "5:00";
            String endtime = "9:00 PM";
           // String endtime = "21:00";
            // Create an instance of SimpleDateFormat with the specified
            // format.
//            DateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
            DateFormat sdf1 = new SimpleDateFormat("hh:mm aa");
            try {
                Date startdate = sdf1.parse(startime);
                System.out.println("Date and Time: " + startdate);

                Date enddate = sdf1.parse(endtime);
                System.out.println("Date and endtime: " + enddate);
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(startdate);

                if (calendar.getTime().before(enddate)){
                    times.add(sdf.format(calendar.getTime()));
                    System.out.println("Initial time:  "+times);

                    while(calendar.getTime().before(enddate)) {

                        calendar.add(Calendar.MINUTE, 30);
                        times.add(sdf.format(calendar.getTime()));
                        System.out.println(times);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int i=0;i<times.size();i++)
            {
                Log.e("DATA","time is....."+times.get(i).toString());
            }
        }

}
