package com.softgates.instadoctor.takeallergieview

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
import com.softgates.instadoctor.databinding.TakeallergieViewBinding
import com.softgates.instadoctor.databinding.TakemedicineViewBinding
import com.softgates.instadoctor.takemedicine.TakeMedicineViewDirections

class TakeAllergieView : Fragment() {

    lateinit var binding: TakeallergieViewBinding
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
        binding = DataBindingUtil.inflate<TakeallergieViewBinding>(
            inflater, R.layout.takeallergie_view, container, false)

        binding.next.setOnClickListener {
            val action = TakeAllergieViewDirections.actionTakeAllergieViewToWeightHeightView()
            NavHostFragment.findNavController(this).navigate(action)
        }
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}