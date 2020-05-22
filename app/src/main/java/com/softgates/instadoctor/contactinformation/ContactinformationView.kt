package com.softgates.instadoctor.contactinformation

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.ContactinformationViewBinding
import com.softgates.instadoctor.databinding.RecoverpasswordViewBinding

class ContactinformationView : Fragment() {

    lateinit var binding: ContactinformationViewBinding
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
        binding = DataBindingUtil.inflate<ContactinformationViewBinding>(
            inflater, R.layout.contactinformation_view, container, false)

        binding.camerabtn.setOnClickListener {
            showDialog()
        }

        binding.editaddress.setOnClickListener {
            updateAddressDialog()
        }

        binding.emailedittext.onRightDrawableClicked {
           // it.text.clear()
            updateEmailDialog()
        }

        return  binding.root
    }

    fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
        this.setOnTouchListener { v, event ->
            var hasConsumed = false
            if (v is EditText) {
                if (event.x >= v.width - v.totalPaddingRight) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        onClicked(this)
                    }
                    hasConsumed = true
                }
            }
            hasConsumed
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun showDialog() {
        val dialog = Dialog(activity as AppCompatActivity,R.style.CustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.popupwindow)
       // val resident: RelativeLayout = dialog.findViewById(R.id.resident) as RelativeLayout
        dialog.getWindow()!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
       /* resident.setOnClickListener {
            dialog.hide()
        }*/
        //IFSALEOW
        dialog.show()
    }

    fun updateAddressDialog() {
        val dialog = Dialog(activity as AppCompatActivity,R.style.CustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.updateaddress_dialog)
        dialog.getWindow()!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);

        //IFSALEOW
        dialog.show()
    }

    fun updateEmailDialog() {
        val dialog = Dialog(activity as AppCompatActivity,R.style.CustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.updateemail_dialog)
        dialog.getWindow()!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
        //IFSALEOW
        dialog.show()
    }

}




