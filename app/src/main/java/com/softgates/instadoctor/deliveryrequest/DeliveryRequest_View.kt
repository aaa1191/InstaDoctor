package com.softgates.instadoctor.deliveryrequest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.DeliveryreqViewBinding
import com.softgates.instadoctor.databinding.FaqfragmentViewBinding
import com.softgates.instadoctor.ratingview.Rating_ViewDirections
import com.softgates.instadoctor.selectsymptom.SymptomViewModel
import com.softgates.instadoctor.selectsymptom.SymptomViewModelFactory

class DeliveryRequest_View : Fragment() {

    lateinit var binding: DeliveryreqViewBinding
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel : DeliveryRequestModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<DeliveryreqViewBinding>(
            inflater, R.layout.deliveryreq_view, container, false)

        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences("dd", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = DeliveryRequestModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliveryRequestModel::class.java)

        binding.request.setOnClickListener {
            val action = DeliveryRequest_ViewDirections.actionDeliveryRequestViewToShareReviewView()
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}