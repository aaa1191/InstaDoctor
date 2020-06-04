package com.softgates.instadoctor.thankupayment

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
import com.softgates.instadoctor.databinding.PaymentsummeryViewBinding
import com.softgates.instadoctor.databinding.ThankupaymentViewBinding
import com.softgates.instadoctor.databinding.ThankuscreenViewBinding
import com.softgates.instadoctor.paymentsummery.PaymentSummeryViewDirections

class ThankuPaymentView : Fragment() {

    lateinit var binding: ThankupaymentViewBinding
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
        binding = DataBindingUtil.inflate<ThankupaymentViewBinding>(
            inflater, R.layout.thankupayment_view, container, false)

        binding.thankupayment.setOnClickListener {

            val action = ThankuPaymentViewDirections.actionThankuPaymentViewToAnyMedicineView()
            NavHostFragment.findNavController(this).navigate(action)

        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}