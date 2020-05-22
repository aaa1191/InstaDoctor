package com.softgates.instadoctor.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.softgates.instadoctor.R
import com.softgates.instadoctor.introduction.IntroductionActivity

class LoginActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity_view)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    fun logout()
    {
        val inten = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(inten)
        finish()
    }

}