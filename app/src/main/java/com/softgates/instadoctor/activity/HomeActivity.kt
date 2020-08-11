package com.softgates.instadoctor.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.HomeactivityViewBinding
import com.softgates.instadoctor.util.Constant
import com.softgates.instadoctor.util.hide
import com.softgates.instadoctor.util.show
import com.softgates.myapplication.util.bindLayoutFullscreen
import kotlinx.android.synthetic.main.homeactivity_view.*

class HomeActivity : AppCompatActivity()
{
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: HomeactivityViewBinding = DataBindingUtil.setContentView(
            this, R.layout.homeactivity_view)
      //  setContentView(R.layout.homeactivity_view)
     //   getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences =   getSharedPreferences(Constant.SHAREDPREFERENCENAME, Context.MODE_PRIVATE)
        Log.e("HOMEVIEW","destination is......")
        val navHostFragment = nav_host as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.home_view)
        binding.linearlayout.bindLayoutFullscreen(false,false)
        binding.bottomNav.visibility= View.VISIBLE
        graph.startDestination = (R.id.home)
        navHostFragment.navController.graph = graph
        sharedPreferences.edit { putBoolean(Constant.RATINGBUTTONVISIBLE,false)}

        Log.e("CHECKDATA","videodisconnect....."+sharedPreferences.getBoolean(Constant.VIDEODISCONNECT,false))
       if(sharedPreferences.getBoolean(Constant.VIDEODISCONNECT,false))
        {
            Log.e("HOMEVIEW","INVISIBLE is called")
            binding.bottomNav.visibility= View.GONE
            binding.linearlayout.bindLayoutFullscreen(true,true)
            graph.startDestination = (R.id.ratingViewModel)
            navHostFragment.navController.graph = graph
        }
        else
        {
            Log.e("HOMEVIEW","VISIBLE is called")
            binding.linearlayout.bindLayoutFullscreen(false,false)
            binding.bottomNav.visibility= View.VISIBLE
            graph.startDestination = (R.id.home)
            navHostFragment.navController.graph = graph
        }
        val navController = Navigation.findNavController(this@HomeActivity, R.id.nav_host)
        binding.bottomNav.setupWithNavController(navController)
        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                Log.e("HOMEVIEW","destination is......"+destination)
                when (destination.id) {
                    R.id.home ,R.id.profile,R.id.setting -> binding.bottomNav.show()
                    else -> binding.bottomNav.hide()
                }
            }
        }
    }

    fun loginView()
    {
        val inten = Intent(this@HomeActivity, LoginActivity::class.java)
        startActivity(inten)
        finish()
    }

    fun homepressed()
    {
        val inten = Intent(this@HomeActivity, LoginActivity::class.java)
        startActivity(inten)
        finish()
    }

    fun onbackpressed()
    {
          super.onBackPressed();
      /*  val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        this.finish();*/
    }

    fun whiteStatusbar()
    {
        getWindow().setNavigationBarColor(getResources().getColor(android.R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
    }

    fun greyStatusbar()
    {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.homebg));
        getWindow().setStatusBarColor(getResources().getColor(R.color.homebg));
    }
}