package com.softgates.instadoctor.paymentsummery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams
import com.softgates.instadoctor.paymentsummery.PaymentSummeryViewModelFactory
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.LoginActivity
import com.softgates.instadoctor.databinding.FeltwayViewBinding
import com.softgates.instadoctor.databinding.PaymentsummeryViewBinding
import com.softgates.instadoctor.databinding.RatingviewModelBinding
import com.softgates.instadoctor.paymentsummery.PaymentSummeryViewDirections
import com.softgates.instadoctor.paymentsummery.PaymentSummeryViewModel
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.ProgressDialog

class PaymentSummeryView : Fragment() {

    lateinit var binding: PaymentsummeryViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel : PaymentSummeryViewModel
    private lateinit var loader: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<PaymentsummeryViewBinding>(
            inflater, R.layout.paymentsummery_view, container, false)

        sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = PaymentSummeryViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PaymentSummeryViewModel::class.java)

        viewModel.message.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(activity as AppCompatActivity,it.toString(), Toast.LENGTH_SHORT).show()
        })

        viewModel.status.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                ApiStatus.LOADING -> {
                    loader.setLoading(true)
                }
                ApiStatus.ERROR -> {
                    loader.setLoading(false)
                }
                ApiStatus.DONE -> {
                    loader.setLoading(false)
                }
            }
        })

        binding.paymentbtn.setOnClickListener {
            goPayment()
        }

        viewModel.notifyItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == 1) {
                  binding.viewModel = viewModel
                }
            }
        })

        binding.addpaymentcard.setOnClickListener {

        }

        viewModel.navigateActivity.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == 1) {
                   /* val action = PaymentSummeryViewDirections.actionPaymentSummeryViewToThankuPaymentView()
                    NavHostFragment.findNavController(this).navigate(action)*/
                    paymentsuccess()
                    viewModel.complete()
                }
            }
        })
        return  binding.root
    }

    fun goPayment() {
        val intent = Intent(context as AppCompatActivity, PayTabActivity::class.java)
        intent.putExtra(PaymentParams.MERCHANT_EMAIL, "web@unclefluffy.com")
        intent.putExtra(
            PaymentParams.SECRET_KEY,
            "ZBIIs0CaG9jIknCvgk17toMEf5GEbiyaUhSmivxYEQGZeivvDwC7VoMbusQGLpRFGK2YfAWUqDTFWoaAVNHRct2GWTnHienYYpWd"
        )//Add your Secret Key Here
        intent.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH)
        intent.putExtra(PaymentParams.TRANSACTION_TITLE, "Test Paytabs android library")
  //      intent.putExtra(PaymentParams.AMOUNT, 1.0)
        intent.putExtra(PaymentParams.AMOUNT, viewModel.totalPay.value.toString().toDouble())

        intent.putExtra(PaymentParams.CURRENCY_CODE, "AED")
        intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733")
        intent.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com")
        intent.putExtra(PaymentParams.ORDER_ID, viewModel.orderId.value.toString())
  //      intent.putExtra(PaymentParams.ORDER_ID, "123456")
      //  intent.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2")

        /*
        //
        Billing Address
        intent.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_BILLING, "Manama")
        intent.putExtra(PaymentParams.STATE_BILLING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_BILLING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_BILLING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Shipping Address
        intent.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.STATE_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_SHIPPING,
            "00973"
        )
        */
        //Put Country Phone code if Postal code not available '00973'
        //Payment Page Style
        intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#4cb089")
        //Tokenization
        intent.putExtra(PaymentParams.IS_TOKENIZATION, true)
        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Toast.makeText(context,"Payment Success",Toast.LENGTH_LONG).show()
            Log.e("Tag", data!!.getStringExtra(PaymentParams.RESPONSE_CODE))
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID))
            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN)!!.isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN))
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL))
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD))
            }
            viewModel.createAppointmentApi()
        }
        else
        {
            Toast.makeText(context,"Payment Failed",Toast.LENGTH_LONG).show()
        }
    }

    fun paymentsuccess()
    {
        //  Log.e("CHECKDATA","paymetnbutton on click listener")
          val action = PaymentSummeryViewDirections.actionPaymentSummeryViewToThankuPaymentView()
         NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }
}