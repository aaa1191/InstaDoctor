package com.softgates.instadoctor.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.softgates.instadoctor.R
import com.softgates.instadoctor.databinding.HomeactivityViewBinding
import com.softgates.instadoctor.util.hide
import com.softgates.instadoctor.util.show

class HomeActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: HomeactivityViewBinding = DataBindingUtil.setContentView(
            this, R.layout.homeactivity_view)
      //  setContentView(R.layout.homeactivity_view)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        val navController = Navigation.findNavController(this@HomeActivity, R.id.nav_host)
        binding.bottomNav.setupWithNavController(navController)
        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                Log.e("HOMEVIEW","destination is......"+destination)
                when (destination.id) {
                    R.id.home ,R.id.profile -> binding.bottomNav.show()
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

}