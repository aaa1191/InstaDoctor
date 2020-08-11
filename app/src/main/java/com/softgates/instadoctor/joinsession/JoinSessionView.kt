package com.softgates.instadoctor.joinsession
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.FeltwayViewBinding
import com.softgates.instadoctor.databinding.JointsessionViewBinding
import com.softgates.instadoctor.util.Constant

class JoinSessionView : Fragment() {

    lateinit var binding: JointsessionViewBinding
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
        binding = DataBindingUtil.inflate<JointsessionViewBinding>(
            inflater, R.layout.jointsession_view, container, false)
        sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)

        Log.e("DOCTORIMAGE","doctor image is "+sharedPreferences.getString(Constant.DOCPROFILE,""))

        if(sharedPreferences.getString(Constant.DOCPROFILE,"").equals(""))
        {
        }
        else
        {
            Log.e("DOCTORIMAGE","doctor image is called")
            Glide.with(this)
                .load(sharedPreferences.getString(Constant.DOCPROFILE,""))
                .transition(DrawableTransitionOptions.withCrossFade()).circleCrop()
                .into(binding.docprofileimage)
        }
        binding.nextarrow.setOnClickListener {
            val action = JoinSessionViewDirections.actionJoinSessionViewToVideoCalling()
            NavHostFragment.findNavController(this).navigate(action)
        }


        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}