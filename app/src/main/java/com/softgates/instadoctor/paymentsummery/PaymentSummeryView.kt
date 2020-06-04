package com.softgates.instadoctor.paymentsummery

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.FeltwayViewBinding
import com.softgates.instadoctor.databinding.PaymentsummeryViewBinding
import com.softgates.instadoctor.doctorprofile.DoctorProfileFragmentDirections
import com.softgates.instadoctor.feltway.FeltWayViewDirections

class PaymentSummeryView : Fragment() {

    lateinit var binding: PaymentsummeryViewBinding
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
        binding = DataBindingUtil.inflate<PaymentsummeryViewBinding>(
            inflater, R.layout.paymentsummery_view, container, false)

        binding.paymentbtn.setOnClickListener {

            Log.e("CHECKDATA","paymetnbutton on click listener")
            val action = PaymentSummeryViewDirections.actionPaymentSummeryViewToThankuPaymentView()
            NavHostFragment.findNavController(this).navigate(action)

        }

        binding.addpaymentcard.setOnClickListener {

            val action = PaymentSummeryViewDirections.actionPaymentSummeryViewToThankuPaymentView()
            NavHostFragment.findNavController(this).navigate(action)

        }


        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}