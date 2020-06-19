package com.softgates.instadoctor.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Constant
{

    const val ADDPRODUCTBTN = "ADDPRODUCTBTN"

    const val EMPTY ="EMPTY"
    const val SHAREDPREFERENCENAME ="INSTADOCTOR"
    const val FELTDAYS ="FELTDAYS"
    const val SYMPTOMNAME ="SYMPTOMNAME"
    const val MEDICATIONNAME ="MEDICATIONNAME"
    const val ALLERGY ="ALLERGY"
    const val ALLERGYNAME ="ALLERGYNAME"
    const val MEDICATION ="MEDICATION"
    const val LOGINSIGNUPCHECK ="LOGINSIGNUPCHECK"
    const val CHILDSTATUS = "CHILDSTATUS"
    const val CHILDID = "CHILDID"
    const val DOCID = "DOCID"

    const val SUCCEESSSTATUS = 1
    const val SUCCEESSSTATUSTWOHUNDRED = 200
    const val TOKENEXPIRED = 505
    const val USERTOKEN = "USERTOKEN"
    const val USEREMAIL = "USEREMAIL"
    const val PATIENTID = "PATIENTID"
    const val USERPHONE = "USERPHONE"
    const val USERADDRESSONE = "USERADDRESSONE"
    const val USERADDRESSTWO = "USERADDRESSTWO"
    const val USERCITY = "USERCITY"
    const val USERSTATE = "USERSTATE"
    const val USERZIPCODE = "USERZIPCODE"
    const val LANGUAGAE = "LANGUAGAE"
    const val WEBVIEWTYPE = "WEBVIEWTYPE"
    const val HOWITWORKS = "HOWITWORKS"
    const val TERMCONDITION = "TERMCONDITION"
    const val ABOUTUS = "ABOUTUS"
    const val LOGINGVIEW = "LOGINGVIEW"
    const val APIRESPONSE = "APIRESPONSE"
    const val ADDTOCARTLISTVIEW = "ADDTOCARTLISTVIEW"
    const val PRODUCTVIEW = "PRODUCTVIEW"
    const val WISHLISTVIEW = "WISHLISTVIEW"
    const val ORDERVIEW = "ORDERVIEW"
    const val USERID = "USERID"
    const val FAQ = "FAQ"
    const val CONTACTUS = "CONTACTUS"
    const val PROFILEVIEW = "PROFILEVIEW"
    const val WEBVIEW = "WEBVIEW"
    const val       PRODUCTDETAIL = "PRODUCTDETAIL"
    const val ALLPRODUCTDETAIL = "ALLPRODUCTDETAIL"
    const val OFFERDETAIL = "OFFERDETAIL"

    fun connected(context:Context) : Boolean
    {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return  isConnected
      //  return true
    }

}

