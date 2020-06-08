package com.softgates.instadoctor.takemedicine

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
import com.softgates.instadoctor.anymedicine.AnyMedicineViewDirections
import com.softgates.instadoctor.databinding.AnymedicineViewBinding
import com.softgates.instadoctor.databinding.TakemedicineViewBinding
import com.softgates.instadoctor.selectsymptom.SymptomAdapter
import com.softgates.instadoctor.selectsymptom.SymptomViewModel
import com.softgates.instadoctor.selectsymptom.SymptomViewModelFactory
import com.softgates.instadoctor.util.Constant

class TakeMedicineView : Fragment() {

    lateinit var binding: TakemedicineViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel : TakeMedicineViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<TakemedicineViewBinding>(
            inflater, R.layout.takemedicine_view, container, false)

        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = TakeMedicineViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TakeMedicineViewModel::class.java)

        binding.next.setOnClickListener {
            var addMedName:String=""
            for (data in viewModel.addlist.value!!.indices) {
                Log.e(
                    "FINALPRODUCTPRICE",
                    "price..." + viewModel.addlist.value!!.get(data).addTxt
                )
                    if(addMedName.toString().equals(""))
                    {
                        addMedName=viewModel.addlist.value!!.get(data).addTxt.toString()
                    }
                    else
                    {
                        addMedName=addMedName+","+viewModel.addlist.value!!.get(data).addTxt.toString()
                    }
            }
            Log.e("FINALMEDICATIONNAME","FinalSymptomname is......."+addMedName.toString())
            sharedPreferences.edit { putString(Constant.MEDICATIONNAME,addMedName.toString()) }
            val action = TakeMedicineViewDirections.actionTakeMedicineViewToAnyDrugAllergyView()
            NavHostFragment.findNavController(this).navigate(action)
        }
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.addrecyclerview?.setLayoutManager(linearLayoutManager)
        val adapter = TakeMedicineAdapter(OnClick { data, type, position ->

        })

        viewModel.addlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

        binding.addanother.setOnClickListener {
            for (data in viewModel.addlist.value!!.indices) {
               if(viewModel.addlist.value!!.get(data).addTxt.equals(""))
               {
                   viewModel.addValue()
               }
                else
               {
                   viewModel.addValue()
               }
            }
        }

        viewModel.notifyItem.observe(viewLifecycleOwner, Observer {
            binding.addrecyclerview.adapter?.notifyItemChanged(it)
        })

        binding.addrecyclerview?.adapter = adapter
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}