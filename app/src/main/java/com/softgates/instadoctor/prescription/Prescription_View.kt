package com.softgates.instadoctor.prescription

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.PaymentsummeryViewBinding
import com.softgates.instadoctor.databinding.PrescriptionViewBinding
import com.softgates.instadoctor.mychild.*
import com.softgates.instadoctor.paymentsummery.PaymentSummeryViewDirections
import com.softgates.instadoctor.util.Constant

class Prescription_View : Fragment() {

    lateinit var binding: PrescriptionViewBinding
    private lateinit var viewModel : PrescriptionViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<PrescriptionViewBinding>(
            inflater, R.layout.prescription_view, container, false)

        binding.deliverymedicine.setOnClickListener {
            val action = Prescription_ViewDirections.actionPrescriptionViewToDeliveryRequestView()
            NavHostFragment.findNavController(this).navigate(action)
        }
        val application = requireNotNull(this.activity).application
        sharedPreferences = (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val viewModelFactory =  PrescriptionViewModelFactory(sharedPreferences,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PrescriptionViewModel::class.java)
        binding.viewModel = viewModel
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.prescriptionrecyclerview?.setLayoutManager(linearLayoutManager)

        val mychildadapter = PrescriptionListAdapter(OnClicks { data, type, position ->
            if(type==1)
            {
                viewModel.setVisible(position)
            }
        })

        viewModel.prescriptionlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mychildadapter.submitList(it)
            mychildadapter.notifyDataSetChanged()
        })

        binding.prescriptionrecyclerview?.adapter = mychildadapter

        viewModel.notifyItem.observe(viewLifecycleOwner, Observer {
            binding.prescriptionrecyclerview.adapter?.notifyItemChanged(it)
        })

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}