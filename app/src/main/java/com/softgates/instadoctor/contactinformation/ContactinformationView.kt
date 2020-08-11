package com.softgates.instadoctor.contactinformation

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.databinding.ContactinformationViewBinding
import com.softgates.instadoctor.util.ApiStatus
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.ProgressDialog
import com.softgates.instadoctor.util.ValidationUtil
import kotlinx.android.synthetic.main.contactinformation_view.*
import java.io.File

class ContactinformationView : Fragment() , View.OnClickListener {

    lateinit var binding: ContactinformationViewBinding
    private lateinit var viewModel: ContactInformationViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager

    //Our variables
    private var mImageView: ImageView? = null
    private var mUri: Uri? = null

    //Our widgets
    private lateinit var btnCapture: Button
    private lateinit var btnChoose: Button

    //Our constants
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2

    var address: String = ""
    var addresstwo: String = ""
    var city: String = ""
    var state: String = ""
    var zipcode: String = ""
    private lateinit var loader: ProgressDialog


    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        binding = DataBindingUtil.inflate<ContactinformationViewBinding>(
            inflater, R.layout.contactinformation_view, container, false
        )


        sharedPreferences = (activity as AppCompatActivity).getSharedPreferences(
            Constant.SHAREDPREFERENCENAME,
            Context.MODE_PRIVATE
        )
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ContactInformationViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ContactInformationViewModel::class.java)

        binding.camerabtn.setOnClickListener {
            openGallery()

        }

        binding.emailedittext.setText(sharedPreferences.getString(Constant.USEREMAIL, ""))

        if (sharedPreferences.getString(Constant.USERPHONE, "").equals("null")) {
            binding.phoneedittext.setText("")
        } else {
            binding.phoneedittext.setText(sharedPreferences.getString(Constant.USERPHONE, ""))
        }

        binding.editaddress.setOnClickListener {
            updateAddressDialog()
        }



        /*  binding.editaddressone.setOnClickListener {
            //  updateAddressDialog()
          }*/

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
            Toast.makeText(activity as AppCompatActivity, it.toString(), Toast.LENGTH_SHORT).show()
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


        binding.backbtn.setOnClickListener {
            Log.e("ONBACKPRESSED", "onbackpressed is called")
            (activity as HomeActivity).onbackpressed()
        }

      //  binding.img.setOnClickListener(this);

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onResume() {
        super.onResume()
       // checkCameraPermission()
    }


    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                activity as AppCompatActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as AppCompatActivity,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION
            )
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity((context as AppCompatActivity).packageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select picture"), REQUEST_PICK_IMAGE );

           /* intent.resolveActivity((context as AppCompatActivity).packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }*/
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            Log.e("CAMERAIMAGE","Camera image result ok ok is called in android")

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap
                userimage.setImageBitmap(bitmap)
            } else if (requestCode == REQUEST_PICK_IMAGE) {

                val filePath = ""//getImageFilePath(data!!)
                Log.e("CAMERAIMAGE", "Filepath is called....." + filePath!!)
                if (filePath != null) {

                    /* drugtwoentyimagelayout.setVisibility(View.VISIBLE)
                     browsetwoenty_btn.setVisibility(View.GONE)
                     val selectedImage = BitmapFactory.decodeFile(filePath)*/
                }
                val uri = data?.getData()
                Log.e("CAMERAIMAGE", "uri is called....." + uri!!)
                userimage.setImageURI(uri)
                getPath((activity as AppCompatActivity),uri)
                Log.e("CAMERAIMAGE", "FINAL PATH PATH is called....." +  getPath((activity as AppCompatActivity),uri))

                //   getRealPathFromURI(uri)
               // getRealPathFromURI_API11to18((activity as AppCompatActivity),uri)
              // Log.e("CAMERAIMAGE", "RealPath is called....." + getRealPathFromURI(uri)!!)

            }
        }
        else
        {
            Log.e("CAMERAIMAGE","Camera image else else else else is called in android")
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor =
            (activity as AppCompatActivity).getContentResolver().query(contentURI, null, null, null, null)!!
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            Log.e("CAMERAIMAGE","id is is is..."+idx)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    fun getPath(context: Context?, uri: Uri): String? {
        val isKitKat: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId: String = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id: String = DocumentsContract.getDocumentId(uri)
                val contentUri: Uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
                return getDataColumn(context!!, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId: String = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf<String?>(
                    split[1]
                )
                return getDataColumn(context!!, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context!!, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String?>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

/*
    @SuppressLint("NewApi")
    fun getRealPathFromURI_API11to18(
        context: Context?,
        contentUri: Uri?
    ): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var result: String? = null
        val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
        val cursor: Cursor = cursorLoader.loadInBackground()
        if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(column_index)
            Log.e("CAMERAIMAGE", "RealPath is called....." + result.toString())
            cursor.close()
        }
        return result
    }*/

    fun getImageFilePath(data: Intent): String? {
        return getImageFromFilePath(data)
    }

    private fun getImageFromFilePath(data: Intent?): String? {
        val isCamera = data == null || data.data == null
        return if (isCamera)
        {
           getCaptureImageOutputUri()!!.path
        }
        else
        {
            getPathFromURI(data!!.data)


        }
    }

    private fun getCaptureImageOutputUri(): Uri? {
        var outputFileUri: Uri? = null
        val getImage = (activity as AppCompatActivity).getExternalFilesDir("")
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage!!.getPath(), "profile.png"))
        }
        return outputFileUri
    }

    private fun getPathFromURI(contentUri: Uri?): String {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = (activity as AppCompatActivity).getContentResolver().query(contentUri!!, proj, null, null, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor!!.moveToFirst()
        return cursor!!.getString(column_index)
    }

 /*   fun getRealPathFromURI(contentUri: Uri?): String? {

        var cursor: Cursor? = null
        return try {
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            cursor = context!!.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            Log.e("CAMERAIMAGE", "getRealPathFromURI Exception : $cursor!!.getString(column_index)")

            cursor!!.getString(column_index)
        } catch (e: Exception) {
            Log.e("CAMERAIMAGE", "getRealPathFromURI Exception : $e")
            ""
        } finally {
            cursor?.close()
        }

        *//*val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor: Cursor = (activity as AppCompatActivity).managedQuery(contentUri, proj, null, null, null)
//        val cursor: Cursor = (activity as AppCompatActivity).getContentResolver().query(contentUri!!, proj, null, null, null)!!
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
      //  val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        Log.e("CAMERAIMAGE","path is called..."+cursor.getString(column_index))
        return cursor.getString(column_index)*//*

        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

    }
*/
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
        val dialog = Dialog(activity as AppCompatActivity, R.style.CustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.popupwindow)
        // val resident: RelativeLayout = dialog.findViewById(R.id.resident) as RelativeLayout
        dialog.getWindow()!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        /* resident.setOnClickListener {
            dialog.hide()
        }*/
        //IFSALEOW
        dialog.show()
    }

    fun updateAddressDialog() {

        Log.e("UPDATEADDRESS","UPDATE ADDRESS IS CLICKED")
        val dialog = Dialog(activity as AppCompatActivity, R.style.CustomDialog)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.updateaddress_dialog)
        dialog.getWindow()!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );

        val addressone: TextInputEditText = dialog.findViewById(R.id.addrssone) as TextInputEditText
        val addresstwotxt: TextInputEditText =
            dialog.findViewById(R.id.addressedittxt) as TextInputEditText
        val citytxt: TextInputEditText = dialog.findViewById(R.id.cityedittxt) as TextInputEditText
        val statetxt: TextInputEditText =
            dialog.findViewById(R.id.stateedittxt) as TextInputEditText
        val zipcodetxt: TextInputEditText =
            dialog.findViewById(R.id.zipcodeditxt) as TextInputEditText
        val save: RelativeLayout = dialog.findViewById(R.id.save) as RelativeLayout

        Log.e(
            "CONTACTINFORMATION",
            "contact information is......" + sharedPreferences.getString(
                Constant.USERADDRESSONE,
                ""
            )
        )

        if (sharedPreferences.getString(Constant.USERADDRESSONE, "").equals("null")) {
            addressone.setText("")
        } else {
            addressone.setText(sharedPreferences.getString(Constant.USERADDRESSONE, ""))
        }

        if (sharedPreferences.getString(Constant.USERADDRESSTWO, "").equals("null")) {
            addresstwotxt.setText("")
        } else {
            addresstwotxt.setText(sharedPreferences.getString(Constant.USERADDRESSTWO, ""))
        }

        if (sharedPreferences.getString(Constant.USERCITY, "").equals("null")) {
            citytxt.setText("")
        } else {
            citytxt.setText(sharedPreferences.getString(Constant.USERCITY, ""))
        }

        if (sharedPreferences.getString(Constant.USERSTATE, "").equals("null")) {
            statetxt.setText("")
        } else {
            statetxt.setText(sharedPreferences.getString(Constant.USERSTATE, ""))
        }

        if (sharedPreferences.getString(Constant.USERZIPCODE, "").equals("null")) {
            zipcodetxt.setText("")
        } else {
            zipcodetxt.setText(sharedPreferences.getString(Constant.USERZIPCODE, ""))
        }

        save.setOnClickListener {
            if (addressone.text.toString().equals("")) {
                Toast.makeText(context, "Address is required", Toast.LENGTH_SHORT).show()
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
        val dialog = Dialog(activity as AppCompatActivity, R.style.CustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.updateemail_dialog)
        dialog.getWindow()!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );

        val password: TextInputEditText =
            dialog.findViewById(R.id.passwordedittxt) as TextInputEditText
        val verifypassword: RelativeLayout =
            dialog.findViewById(R.id.verifypassword) as RelativeLayout

        verifypassword.setOnClickListener {

            if (binding.emailedittext.text.toString().isEmpty()) {
                Toast.makeText(context, "Email field is required", Toast.LENGTH_SHORT).show()
            } else if (!ValidationUtil.isEmailValid(binding.emailedittext.text.toString())) {
                Toast.makeText(context, "The Email address is not valid", Toast.LENGTH_SHORT).show()
            } else if (binding.phoneedittext.text.toString().isEmpty()) {
                Toast.makeText(context, "Phone field is required", Toast.LENGTH_SHORT).show()
            } else if (password.text.toString().isEmpty()) {
                Toast.makeText(context, "Password field is required", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.contactinformationApi(
                    address.toString(),
                    addresstwo.toString(),
                    city.toString(),
                    state.toString(),
                    zipcode.toString(),
                    binding.emailedittext.text.toString(),
                    binding.phoneedittext.text.toString(),
                    password.text.toString()
                )
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
        Toast.makeText(context as AppCompatActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (view!!.id) {
            R.id.img -> {
                Log.e("CAMERAIMAGE","Camera image else else else else is called in android")

            }
        }
    }
}

 /*   private fun capturePhoto(){
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
    }*/


    private fun openGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
            //  startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }

 /*   private fun renderImage(imagePath: String?){
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            mImageView?.setImageBitmap(bitmap)
        }
        else {
            show("ImagePath is null")
        }
    }*/

 /*   private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = (context as AppCompatActivity).contentResolver.query(uri!!, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }*/

   /* @TargetApi(19)
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
    }*/


  /*  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
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
    }*/

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
    }*/






