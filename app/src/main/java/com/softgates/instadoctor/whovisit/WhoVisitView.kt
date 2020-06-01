package com.softgates.instadoctor.whovisit

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
import com.softgates.instadoctor.databinding.TermuseViewBinding
import com.softgates.instadoctor.databinding.WhovisitViewBinding
import com.softgates.instadoctor.termcondition.TermConditionFragmentDirections

class WhoVisitView : Fragment() {

    lateinit var binding: WhovisitViewBinding
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
        binding = DataBindingUtil.inflate<WhovisitViewBinding>(
            inflater, R.layout.whovisit_view, container, false)

        binding.childregister.setOnClickListener {
            val action = WhoVisitViewDirections.actionWhoVisitViewToRegisterChildView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.namearrow.setOnClickListener {

            val action = WhoVisitViewDirections.actionWhoVisitViewToSymptomView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}