package com.softgates.instadoctor.selectsymptom

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.SymptomViewBinding
import com.softgates.instadoctor.whovisit.WhoVisitViewDirections

class SymptomView : Fragment() {

    lateinit var binding: SymptomViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel : SymptomViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<SymptomViewBinding>(
            inflater, R.layout.symptom_view, container, false)
        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences("dd", Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = SymptomViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SymptomViewModel::class.java)

        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchrecyclerview?.setLayoutManager(linearLayoutManager)

        val adapter = SymptomAdapter(OnClick { data, type, position ->

            if(type==1)
            {
                viewModel.addClick(data,1,position)
            }
        })

        viewModel.notifyItem.observe(viewLifecycleOwner, Observer {
            binding.searchrecyclerview.adapter?.notifyItemChanged(it)
        })

        viewModel.symptomlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

        binding.searchrecyclerview?.adapter = adapter

        binding.next.setOnClickListener {

            val action = SymptomViewDirections.actionSymptomViewToFeltWayView()
            NavHostFragment.findNavController(this).navigate(action)

        }



        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}