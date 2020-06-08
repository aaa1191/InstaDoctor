package com.softgates.instadoctor.selectsymptom

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.SymptomViewBinding
import com.softgates.instadoctor.util.Constant
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
        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
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

        viewModel.GetSymptomlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

        binding.searchrecyclerview?.adapter = adapter

        binding.next.setOnClickListener {
            var symptomName:String=""
            for (data in viewModel.GetSymptomlist.value!!.indices) {
                Log.e("FINALPRODUCTPRICE","price..."+viewModel.GetSymptomlist.value!!.get(data).tick)
                if(viewModel.GetSymptomlist.value!!.get(data).tick==1)
                {
                    if(symptomName.toString().equals(""))
                    {
                        symptomName=viewModel.GetSymptomlist.value!!.get(data).symptom_name.toString()
                    }
                    else
                    {
                        symptomName=symptomName+","+viewModel.GetSymptomlist.value!!.get(data).symptom_name.toString()
                    }
                }
            }
            if(symptomName.toString().equals(""))
            {
                Toast.makeText(context,"Select atleast one sympton",Toast.LENGTH_SHORT).show()
            }
            else
            {
                     val action = SymptomViewDirections.actionSymptomViewToFeltWayView()
                    sharedPreferences.edit { putString(Constant.SYMPTOMNAME,symptomName.toString()) }
                     NavHostFragment.findNavController(this).navigate(action)
            }
            Log.e("FINALPRODUCTPRICE","FinalSymptomname is......."+symptomName.toString())
        }
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}