package com.softgates.instadoctor.joinsession

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
import com.softgates.instadoctor.databinding.FeltwayViewBinding
import com.softgates.instadoctor.databinding.JointsessionViewBinding
import com.softgates.instadoctor.feltway.FeltWayViewDirections

class JoinSessionView : Fragment() {

    lateinit var binding: JointsessionViewBinding
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
        binding = DataBindingUtil.inflate<JointsessionViewBinding>(
            inflater, R.layout.jointsession_view, container, false)

        binding.nextarrow.setOnClickListener {

            val action = JoinSessionViewDirections.actionJoinSessionViewToVideoCalling()
            NavHostFragment.findNavController(this).navigate(action)

        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}