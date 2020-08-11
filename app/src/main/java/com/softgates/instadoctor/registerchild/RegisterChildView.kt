package com.softgates.instadoctor.registerchild

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.activity.LoginActivity
import com.softgates.instadoctor.databinding.RegisterchildViewBinding
import com.softgates.instadoctor.recoverpassword.RecoverPasswordViewModel
import com.softgates.instadoctor.recoverpassword.RecoverPasswordViewModelFactory
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.ProgressDialog
import com.softgates.instadoctor.whovisit.WhoVisitViewDirections
import java.lang.String


class RegisterChildView : Fragment() {

    lateinit var binding: RegisterchildViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel : RegisterChildViewModel
    private lateinit var loader: ProgressDialog
    private val textView: TextView? = null
    private val oldLocation = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<RegisterchildViewBinding>(
            inflater, R.layout.registerchild_view, container, false)

        sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = RegisterChildViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterChildViewModel::class.java)

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

      //  binding.viewModel = viewModel


        binding.yearseekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                Log.e("SEEKBAR","onprogress bar...."+i)
                binding.yearstxt.setText(i.toString()+" Years")
                viewModel.setYear(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
        })

        binding.monthseekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                Log.e("SEEKBAR","onprogress bar...."+i)
                binding.monthtxt.setText(i.toString()+" Months")
                viewModel.setMonth(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })


        binding.malebtn.setOnClickListener {

            binding.female.setCardBackgroundColor(resources.getColor(R.color.gray))
            binding.malebtn.setCardBackgroundColor(resources.getColor(R.color.yellow))
            binding.femaletxt.setTextColor(resources.getColor(R.color.yellow))
            binding.maletxt.setTextColor(resources.getColor(android.R.color.white))
            viewModel.setGender("Male")
        }

        binding.female.setOnClickListener {
            binding.malebtn.setCardBackgroundColor(resources.getColor(R.color.gray))
            binding.female.setCardBackgroundColor(resources.getColor(R.color.yellow))
            binding.femaletxt.setTextColor(resources.getColor(android.R.color.white))
            binding.maletxt.setTextColor(resources.getColor(R.color.yellow))
            viewModel.setGender("Female")
        }

        viewModel.navigateActivity.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == 1) {
                    val action = RegisterChildViewDirections.actionRegisterChildViewToSymptomView()
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }
        })

        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }

        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}