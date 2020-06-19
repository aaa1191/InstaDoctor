package com.softgates.instadoctor.feltway

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.FeltwayViewBinding
import com.softgates.instadoctor.databinding.WhovisitViewBinding
import com.softgates.instadoctor.registerchild.RegisterChildViewDirections
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.whovisit.WhoVisitViewDirections

class FeltWayView : Fragment() {

    lateinit var binding: FeltwayViewBinding
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
        binding = DataBindingUtil.inflate<FeltwayViewBinding>(
            inflater, R.layout.feltway_view, container, false)

        sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)

        binding.nextarrow.setOnClickListener {
            Log.e("SELECTDAYS","select days is....."+binding.days.text.toString())
            if(binding.days.text.toString().equals("0 Days"))
            {
               Toast.makeText(context,"Select days",Toast.LENGTH_SHORT).show()
            }
            else
            {
                sharedPreferences.edit { putString(Constant.FELTDAYS,binding.days.text.toString()) }
                val action = FeltWayViewDirections.actionFeltWayViewToChooseDoctorView()
                NavHostFragment.findNavController(this).navigate(action)
            }

        }


        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                Log.e("SEEKBAR","onprogress bar...."+i)
                binding.days.text=i.toString()+" Days"

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
        })

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}