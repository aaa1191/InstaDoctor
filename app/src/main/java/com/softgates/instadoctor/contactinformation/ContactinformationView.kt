package com.softgates.instadoctor.contactinformation

import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.LoginActivity
import com.softgates.instadoctor.databinding.ContactinformationViewBinding
import com.softgates.instadoctor.databinding.RecoverpasswordViewBinding
import com.softgates.instadoctor.takemedicine.TakeMedicineViewModel
import com.softgates.instadoctor.takemedicine.TakeMedicineViewModelFactory
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.ProgressDialog
import com.softgates.instadoctor.util.ValidationUtil
import java.io.File

class ContactinformationView : Fragment() {

    lateinit var binding: ContactinformationViewBinding
    private lateinit var viewModel : ContactInformationViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager

    //Our variables
    private var mImageView: ImageView? = null
    private var mUri: Uri? = null
    //Our widgets
    private lateinit var btnCapture: Button
    private lateinit var btnChoose : Button
    //Our constants
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2

    var address:String =""
    var addresstwo:String =""
    var city:String =""
    var state:String =""
    var zipcode:String =""
    private lateinit var loader: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<ContactinformationViewBinding>(
            inflater, R.layout.contactinformation_view, container, false)


        sharedPreferences =   (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ContactInformationViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactInformationViewModel::class.java)

        binding.camerabtn.setOnClickListener {
            showDialog()
        }

        binding.emailedittext.setText(sharedPreferences.getString(Constant.USEREMAIL,""))
        binding.phoneedittext.setText(sharedPreferences.getString(Constant.USERPHONE,""))

        binding.editaddress.setOnClickListener {
            updateAddressDialog()
        }

       /* binding.emailedittext.onRightDrawableClicked {
           // it.text.clear()
            updateEmailDialog()
        }*/

        binding.savecontactinformation.setOnClickListener {
           // it.text.clear()
             updateEmailDialog()
            //check permission at runtime
         /*   val checkSelfPermission = ContextCompat.checkSelfPermission(context as AppCompatActivity,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                //Requests permissions to be granted to this application at runtime
                ActivityCompat.requestPermissions(context as AppCompatActivity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            else{
                openGallery()
            }*/
        }

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
        loader = ProgressDialog(view.context)
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

        val addressone: TextInputEditText = dialog.findViewById(R.id.addrssone) as TextInputEditText
        val addresstwotxt: TextInputEditText = dialog.findViewById(R.id.addressedittxt) as TextInputEditText
        val citytxt: TextInputEditText = dialog.findViewById(R.id.cityedittxt) as TextInputEditText
        val statetxt: TextInputEditText = dialog.findViewById(R.id.stateedittxt) as TextInputEditText
        val zipcodetxt: TextInputEditText = dialog.findViewById(R.id.zipcodeditxt) as TextInputEditText
        val save: RelativeLayout = dialog.findViewById(R.id.save) as RelativeLayout

        addressone.setText(sharedPreferences.getString(Constant.USERADDRESSONE,""))
        addresstwotxt.setText(sharedPreferences.getString(Constant.USERADDRESSTWO,""))
        citytxt.setText(sharedPreferences.getString(Constant.USERCITY,""))
        statetxt.setText(sharedPreferences.getString(Constant.USERSTATE,""))
        zipcodetxt.setText(sharedPreferences.getString(Constant.USERZIPCODE,""))

        save.setOnClickListener {
            if(addressone.text.toString().equals(""))
            {
                Toast.makeText(context,"Address is required",Toast.LENGTH_SHORT).show()
            }
            address = addressone.text.toString()
            addresstwo = addresstwotxt.text.toString()
            city = citytxt.text.toString()
            state = statetxt.text.toString()
            zipcode = zipcodetxt.text.toString()
            dialog.hide()
        }

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

        val password: TextInputEditText = dialog.findViewById(R.id.passwordedittxt) as TextInputEditText
        val verifypassword: RelativeLayout = dialog.findViewById(R.id.verifypassword) as RelativeLayout

        verifypassword.setOnClickListener {

            if(binding.emailedittext.text.toString().isEmpty())
            {
                Toast.makeText(context,"Email field is required",Toast.LENGTH_SHORT).show()
            }
            else if(!ValidationUtil.isEmailValid(binding.emailedittext.text.toString()))
            {
                Toast.makeText(context,"The Email address is not valid",Toast.LENGTH_SHORT).show()
            }
            else if(binding.phoneedittext.text.toString().isEmpty())
            {
                Toast.makeText(context,"Phone field is required",Toast.LENGTH_SHORT).show()
            }
            else if(password.text.toString().isEmpty())
            {
                Toast.makeText(context,"Password field is required",Toast.LENGTH_SHORT).show()
            }
            else
            {
                viewModel.contactinformationApi(address.toString(),addresstwo.toString(),city.toString(),state.toString(),zipcode.toString(),binding.emailedittext.text.toString(),binding.phoneedittext.text.toString(),password.text.toString())
                dialog.hide()
            }
        }
        //IFSALEOW
        dialog.show()
    }
//    //image uploadmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//    private fun initializeWidgets() {
//        btnCapture = findViewById(R.id.btnCapture)
//        btnChoose = findViewById(R.id.btnChoose)
//        mImageView = findViewById(R.id.mImageView)
//    }

    private fun show(message: String) {
        Toast.makeText(context as AppCompatActivity,message,Toast.LENGTH_SHORT).show()
    }

    private fun capturePhoto(){
        val capturedImage = File(context!!.externalCacheDir, "My_Captured_Photo.jpg")
        if(capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        mUri = if(Build.VERSION.SDK_INT >= 24){
            FileProvider.getUriForFile(context as AppCompatActivity, "info.camposha.kimagepicker.fileprovider",
                capturedImage)
        } else {
            Uri.fromFile(capturedImage)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
        startActivityForResult(intent, OPERATION_CAPTURE_PHOTO)
    }

    private fun openGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }

    private fun renderImage(imagePath: String?){
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            mImageView?.setImageBitmap(bitmap)
        }
        else {
            show("ImagePath is null")
        }
    }

    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = (context as AppCompatActivity).contentResolver.query(uri!!, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(context as AppCompatActivity, uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion)
            }
            else if ("com.android.providers.downloads.documents" == uri.authority){
                val contentUri = ContentUris.withAppendedId(Uri.parse(
                    "content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = getImagePath(contentUri, null)
            }
        }
        else if ("content".equals(uri!!.scheme, ignoreCase = true)){
            imagePath = getImagePath(uri, null)
        }
        else if ("file".equals(uri.scheme, ignoreCase = true)){
            imagePath = uri.path
        }
        renderImage(imagePath)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
                                            , grantedResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when(requestCode){
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
                    PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }else {
                    show("Unfortunately You are Denied Permission to Perform this Operataion.")
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            OPERATION_CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(
                        (context as AppCompatActivity).getContentResolver().openInputStream(mUri!!))
                    mImageView!!.setImageBitmap(bitmap)
                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }
    }

}




