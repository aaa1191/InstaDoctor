package com.softgates.instadoctor.videocall

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.anymedicine.AnyMedicineViewDirections
import com.softgates.instadoctor.databinding.FeltwayViewBinding
import com.softgates.instadoctor.databinding.VideocallViewBinding
import com.softgates.instadoctor.feltway.FeltWayViewDirections
import com.softgates.instadoctor.introduction.IntroductionActivity

class VideoCallView : Fragment() {

    lateinit var binding: VideocallViewBinding
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
        binding = DataBindingUtil.inflate<VideocallViewBinding>(
            inflater, R.layout.videocall_view, container, false)

        Handler().postDelayed({
           //    val inten = Intent(this@SplashActivity, HomeActivity::class.java)
            val action = VideoCallViewDirections.actionVideoCallViewToRatingViewModel()
            NavHostFragment.findNavController(this).navigate(action)
        }, 2000)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}