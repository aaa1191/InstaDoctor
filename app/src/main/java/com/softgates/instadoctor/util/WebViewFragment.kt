package com.softgates.instadoctor.util

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.softgates.instadoctor.R

class WebViewFragment : Fragment()
{

    var progressBar: ProgressBar? = null
//    internal var url = "http://facebook.com"
     var url = "https://www.hotshelf.com/dev/terms-condiitons"
    var webView: WebView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view :View =inflater.inflate(R.layout.webviewfragment,container,false)

      //  val id = SubCategoryViewArgs.fromBundle(arguments!!).id
        val title = WebViewFragmentArgs.fromBundle(arguments!!).title
//        val title = ""

        if(title.equals(Constant.TERMCONDITION))
        {
            url="https://instadoctor.ae/terms-and-conditions"
        }
        else if(title.equals(Constant.PRIVACYPOLICY))
        {
            url="https://instadoctor.ae/privacy-policy"
        }
        else if(title.equals(Constant.ABOUTUS))
        {
            url="https://instadoctor.ae/about-us"
        }

        var wv: WebView? = null
        wv = view.findViewById<WebView>(R.id.webviewlayout)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
     //   wv.webViewClient = myWebClient()
        wv.settings.javaScriptEnabled = true
        wv.settings.builtInZoomControls = true
        wv.settings.displayZoomControls = false
        wv.loadUrl(url)

        wv.setWebViewClient(object : WebViewClient() {

//            override fun shouldOverrideUrlLoading(view: WebView?,request: WebResourceRequest?): Boolean {
//                progressBar!!.visibility = View.VISIBLE
//                view!!.loadUrl(url)
//                return true
//            }
            override fun onPageFinished(view: WebView, url: String) {
                progressBar!!.visibility = View.GONE
                Log.e("PROGRESSBAR","ondata finished is called")
            }
        })

        return view

    }


}


