package com.softgates.instadoctor.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.softgates.instadoctor.R
import com.softgates.instadoctor.introduction.IntroductionActivity
import kotlinx.android.synthetic.main.splash_view.*

class SplashActivity  : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view)

   //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Glide.with(this).load(R.drawable.splash).into(splashimgview);
        Handler().postDelayed({
//            val inten = Intent(this@SplashActivity, HomeActivity::class.java)
            val inten = Intent(this@SplashActivity, IntroductionActivity::class.java)
            startActivity(inten)
            finish()
//            val inten = Intent(this@SplashActivity, Activity_Login::class.java)
//            startActivity(inten)
//            finish()
        }, 4000)
    }
}