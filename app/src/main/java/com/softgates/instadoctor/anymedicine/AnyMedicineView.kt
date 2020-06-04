package com.softgates.instadoctor.anymedicine

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
import com.softgates.instadoctor.databinding.AnymedicineViewBinding
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

        binding.nomedicinebtn.setOnClickListener {
            val action = AnyMedicineViewDirections.actionAnyMedicineViewToAnyDrugAllergyView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.yesmedicinebtn.setOnClickListener {
            val action = AnyMedicineViewDirections.actionAnyMedicineViewToTakeMedicineView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}