package com.softgates.instadoctor.takeallergieview

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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.TakeallergieViewBinding
import com.softgates.instadoctor.databinding.TakemedicineViewBinding
import com.softgates.instadoctor.takemedicine.*
import com.softgates.instadoctor.util.Constant

class TakeAllergieView : Fragment() {

    lateinit var binding: TakeallergieViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel : TakeAllergieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<TakeallergieViewBinding>(
            inflater, R.layout.takeallergie_view, container, false)

        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = TakeAllergieViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TakeAllergieViewModel::class.java)

        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }

        binding.next.setOnClickListener {
            var addAllergyName:String=""
            for (data in viewModel.addlist.value!!.indices) {
                Log.e(
                    "FINALPRODUCTPRICE",
                    "price..." + viewModel.addlist.value!!.get(data).addTxt
                )
                if(addAllergyName.toString().equals(""))
                {
                    addAllergyName=viewModel.addlist.value!!.get(data).addTxt.toString()
                }
                else
                {
                    addAllergyName=addAllergyName+","+viewModel.addlist.value!!.get(data).addTxt.toString()
                }
            }
            Log.e("FINALMEDICATIONNAME","FinalSymptomname is......."+addAllergyName.toString())
            sharedPreferences.edit { putString(Constant.ALLERGYNAME,addAllergyName.toString()) }
            val action = TakeAllergieViewDirections.actionTakeAllergieViewToWeightHeightView()
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

        binding.addrecyclerview?.adapter = adapter


        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}