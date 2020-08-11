package com.softgates.instadoctor.whovisit
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
import com.softgates.instadoctor.databinding.TermuseViewBinding
import com.softgates.instadoctor.databinding.WhovisitViewBinding
import com.softgates.instadoctor.util.Constant

class WhoVisitView : Fragment() {

    lateinit var binding: WhovisitViewBinding
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
        binding = DataBindingUtil.inflate<WhovisitViewBinding>(
            inflater, R.layout.whovisit_view, container, false)
        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)

        if(sharedPreferences.getString(Constant.PATIENTGENDER,"").equals("Female"))
        {
            binding.imgone.setImageResource(R.drawable.female_icon)
            binding.patientname.setText("Mrs. "+sharedPreferences.getString(Constant.PATIENTNAME,""))
        }
        else
        {
            binding.imgone.setImageResource(R.drawable.male_icon)
            binding.patientname.setText("Mr. "+sharedPreferences.getString(Constant.PATIENTNAME,""))
        }

        binding.childregister.setOnClickListener {

        }

        binding.selfarrow.setOnClickListener {
            sharedPreferences.edit { putInt(Constant.CHILDSTATUS,0) }
            val action = WhoVisitViewDirections.actionWhoVisitViewToSymptomView()
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.mychildarrow.setOnClickListener {
            sharedPreferences.edit { putInt(Constant.CHILDSTATUS,1) }

            Log.e("NOOFPATIENT","no of patient....."+sharedPreferences.getInt(Constant.NOOFPATIENTCHILD,0))
            if(sharedPreferences.getInt(Constant.NOOFPATIENTCHILD,0)==0)
            {
                val action = WhoVisitViewDirections.actionWhoVisitViewToRegisterChildView2()
                NavHostFragment.findNavController(this).navigate(action)

            }
            else
            {
                val action = WhoVisitViewDirections.actionWhoVisitViewToMyChildView()
                NavHostFragment.findNavController(this).navigate(action)
            }

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