package com.softgates.instadoctor.anydrugallergy

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
import com.softgates.instadoctor.anymedicine.AnyMedicineViewDirections
import com.softgates.instadoctor.databinding.AnydrugalergyViewBinding
import com.softgates.instadoctor.databinding.AnymedicineViewBinding

class AnyDrugAllergyView : Fragment() {

    lateinit var binding: AnydrugalergyViewBinding
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
        binding = DataBindingUtil.inflate<AnydrugalergyViewBinding>(
            inflater, R.layout.anydrugalergy_view, container, false)


        binding.nomedicinebtn.setOnClickListener {
            val action = AnyDrugAllergyViewDirections.actionAnyDrugAllergyViewToWeightHeightView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.yesmedicinebtn.setOnClickListener {
            val action = AnyDrugAllergyViewDirections.actionAnyDrugAllergyViewToTakeAllergieView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}