package com.softgates.instadoctor.deliveryrequest

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
import com.softgates.instadoctor.databinding.DeliveryreqViewBinding
import com.softgates.instadoctor.databinding.FaqfragmentViewBinding
import com.softgates.instadoctor.ratingview.Rating_ViewDirections

class DeliveryRequest_View : Fragment() {

    lateinit var binding: DeliveryreqViewBinding
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
        binding = DataBindingUtil.inflate<DeliveryreqViewBinding>(
            inflater, R.layout.deliveryreq_view, container, false)

        binding.request.setOnClickListener {
            val action = DeliveryRequest_ViewDirections.actionDeliveryRequestViewToShareReviewView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}