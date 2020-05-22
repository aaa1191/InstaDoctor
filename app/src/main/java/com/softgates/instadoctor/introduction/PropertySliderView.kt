package com.softgates.instadoctor.introduction

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softgates.instadoctor.R
import com.softgates.instadoctor.network.PropertiesImages
import java.io.Serializable

class PropertySliderView : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {
       val view = inflater.inflate(R.layout.property_sliderview, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getSerializable("VALUE")
        var response : PropertiesImages = data as PropertiesImages

      Log.e("ADVERTISEMENT","name is the value..."+data.toString())
    }

    companion object {
        fun newInstance(movies: PropertiesImages): PropertySliderView {
            val args = Bundle()
            //   args.putString("VALUE", movie)
            args.putSerializable("VALUE", movies as Serializable)
            val fragment = PropertySliderView()
            fragment.arguments = args
            return fragment
        }
    }
}