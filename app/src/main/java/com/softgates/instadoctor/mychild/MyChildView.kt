package com.softgates.instadoctor.mychild
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
import com.softgates.instadoctor.databinding.MychildViewBinding
import com.softgates.instadoctor.databinding.PaymentsummeryViewBinding
import com.softgates.instadoctor.util.Constant

class MyChildView : Fragment() {

    lateinit var binding: MychildViewBinding
    private lateinit var viewModel : MyChildViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<MychildViewBinding>(
            inflater, R.layout.mychild_view, container, false)

        val application = requireNotNull(this.activity).application
        sharedPreferences = (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val viewModelFactory =  MyChildViewModelFactory(sharedPreferences,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyChildViewModel::class.java)
        binding.viewModel = viewModel
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.mychildrecyclerview?.setLayoutManager(linearLayoutManager)

        val mychildadapter = MyChildAdapter(OnClicks { data, type, position ->
            Log.e("CHILDCLICK","id is...."+data.child_id)
            Log.e("CHILDCLICK","name is...."+data.child_name)
            sharedPreferences.edit { putInt(Constant.CHILDID, data.child_id!!.toInt()) }
            val action = MyChildViewDirections.actionMyChildViewToSymptomView()
            NavHostFragment.findNavController(this).navigate(action)
        })

        viewModel.MyChildList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mychildadapter.submitList(it)
            mychildadapter.notifyDataSetChanged()
        })

        binding.mychildrecyclerview?.adapter = mychildadapter

        binding.addchild.setOnClickListener {

            val action = MyChildViewDirections.actionMyChildViewToRegisterChildView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED","onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }

        /* binding.paymentbtn.setOnClickListener {
             Log.e("CHECKDATA","paymetnbutton on click listener")
             val action = PaymentSummeryViewDirections.actionPaymentSummeryViewToThankuPaymentView()
             NavHostFragment.findNavController(this).navigate(action)
         }

         binding.addpaymentcard.setOnClickListener {
             val action = PaymentSummeryViewDirections.actionPaymentSummeryViewToThankuPaymentView()
             NavHostFragment.findNavController(this).navigate(action)
         }*/
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}