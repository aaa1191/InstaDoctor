package com.softgates.instadoctor.customersuport

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.CustomersupportviewBinding


class CustomerSupportView : Fragment() {

    lateinit var binding: CustomersupportviewBinding
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
        binding = DataBindingUtil.inflate<CustomersupportviewBinding>(
            inflater, R.layout.customersupportview, container, false)
       binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }
        binding.chat.setOnClickListener {
            Toast.makeText(context as AppCompatActivity,resources.getString(R.string.commingsoon),Toast.LENGTH_LONG).show()
        }

        binding.phonecalling.setOnClickListener {

           /* val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:0377778888")
            startActivity(callIntent)*/

            if (Build.VERSION.SDK_INT < 23) {
                phoneCall()
            } else {
                if (ActivityCompat.checkSelfPermission(
                        activity as AppCompatActivity,
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    phoneCall()
                } else {
                    val PERMISSIONS_STORAGE =
                        arrayOf(Manifest.permission.CALL_PHONE)
                    //Asking request Permissions
                    ActivityCompat.requestPermissions(  activity as AppCompatActivity, PERMISSIONS_STORAGE, 9)
                }
            }


        }

        return  binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
                                            , grantedResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        var permissionGranted = false

        when(requestCode){
            9 ->
                permissionGranted = grantedResults[0]== PackageManager.PERMISSION_GRANTED;

          /*  if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
                    PackageManager.PERMISSION_GRANTED){

                }else {
                }*/
        }

        if(permissionGranted){
            phoneCall();
        }else {
            Toast.makeText(activity as AppCompatActivity, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


    fun phoneCall()
    {
        if (ActivityCompat.checkSelfPermission(
                activity as AppCompatActivity,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:+971 508800842")
            (activity as AppCompatActivity).startActivity(callIntent)
        } else {
            Toast.makeText(activity as AppCompatActivity, "You don't assign permission.", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}