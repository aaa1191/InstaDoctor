package com.softgates.instadoctor.videocall

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.VideocallViewBinding
import us.zoom.sdk.ZoomSDK

class VideoCallView : Fragment() {

    lateinit var binding: VideocallViewBinding
    //private lateinit var viewModel : HomeViewModel
    private lateinit var vi: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var linearLayoutManager: LinearLayoutManager
     var mZoomSDK: ZoomSDK? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   vi = inflater.inflate(R.layout.fragment_registration_first,container,false)
        mZoomSDK = ZoomSDK.getInstance()

        binding = DataBindingUtil.inflate<VideocallViewBinding>(
            inflater, R.layout.videocall_view, container, false)

        //InitAuthSDKHelper.getInstance().initSDK(activity as AppCompatActivity, this)


        if (mZoomSDK!!.isInitialized()) {
            Log.e("ZOOMVIDECALL","onZoomSDK is initialize")

        }
        else
        {
            Log.e("ZOOMVIDECALL","onZoomSDK is not not initialize")

        }
        /*Handler().postDelayed({
           //    val inten = Intent(this@SplashActivity, HomeActivity::class.java)
            val action = VideoCallViewDirections.actionVideoCallViewToRatingViewModel()
            NavHostFragment.findNavController(this).navigate(action)
        }, 2000)*/

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

     fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
        Log.e("ZOOMVIDECALL","onZoomSDKInitializeResult, errorCode=$errorCode, internalErrorCode=$internalErrorCode")

        /* Log.i(
            us.zoom.sdksample.ui.InitAuthSDKActivity.TAG,
            "onZoomSDKInitializeResult, errorCode=$errorCode, internalErrorCode=$internalErrorCode"
        )*/

    }

     fun onZoomAuthIdentityExpired() {
        Log.e(
            "ZOOMVIDECALL",
            "onZoomAuthIdentityExpired"
        )
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}