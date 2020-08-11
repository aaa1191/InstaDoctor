package com.softgates.instadoctor.anymedicine

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
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.AnymedicineViewBinding
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.whovisit.WhoVisitViewDirections

class AnyMedicineView : Fragment() {

    lateinit var binding: AnymedicineViewBinding
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
        binding = DataBindingUtil.inflate<AnymedicineViewBinding>(
            inflater, R.layout.anymedicine_view, container, false)

        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)

        binding.nomedicinebtn.setOnClickListener {
            sharedPreferences.edit { putInt(Constant.MEDICATION,0) }

            val action = AnyMedicineViewDirections.actionAnyMedicineViewToAnyDrugAllergyView()
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.yesmedicinebtn.setOnClickListener {
            sharedPreferences.edit { putInt(Constant.MEDICATION,1) }
            val action = AnyMedicineViewDirections.actionAnyMedicineViewToTakeMedicineView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}