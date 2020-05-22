package com.softgates.instadoctor.termcondition

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
import com.softgates.instadoctor.databinding.ProfileViewBinding
import com.softgates.instadoctor.databinding.TermconditionViewBinding

class TermConditionFragment : Fragment() {

    lateinit var binding: TermconditionViewBinding
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
        binding = DataBindingUtil.inflate<TermconditionViewBinding>(
            inflater, R.layout.termcondition_view, container, false)

        binding.termuse.setOnClickListener {

            val action = TermConditionFragmentDirections.actionTermConditionFragmentToTermUseView()
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.visitpromotionarrow.setOnClickListener {
            val action = TermConditionFragmentDirections.actionTermConditionFragmentToWhoVisitView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}