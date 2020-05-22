package com.softgates.instadoctor.introduction

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.softgates.instadoctor.R
import com.softgates.instadoctor.activity.HomeActivity
import com.softgates.instadoctor.activity.LoginActivity
import com.softgates.instadoctor.databinding.IntroductionActivityViewBinding
import com.softgates.instadoctor.network.PropertiesImages
import kotlinx.android.synthetic.main.introduction_activity_view.*

class IntroductionActivity : AppCompatActivity()
{
    private lateinit var viewModel : IntroductionViewModel
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var pagerAdapter: PropertySliderAdapter
    private var dotscount: Int = 0
    private var viewPagerPosition: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: IntroductionActivityViewBinding = DataBindingUtil.setContentView(
            this, R.layout.introduction_activity_view)
          sharedPreferences =   (this).getSharedPreferences("dd", Context.MODE_PRIVATE)
        val application = requireNotNull(this).application
        val viewModelFactory = IntroductionViewModelFactory(sharedPreferences, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(IntroductionViewModel::class.java)


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //sliderimages

        viewModel.sliderimg.observe(this, Observer {
            pagerAdapter = PropertySliderAdapter(supportFragmentManager, it as  MutableList<PropertiesImages> )
            binding.saleviewpager.adapter = pagerAdapter
            dotscount = pagerAdapter.count
            var dots: Array<ImageView> = Array<ImageView>(dotscount) { index -> ImageView(this) }
            for (i in 0 until dotscount) {
                dots.set(i, ImageView(this))
                dots!![i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.propertynon_active_dot))
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(8, 0, 8, 0)
                binding.saleslider.addView(dots!![i], params)
            }
            dots!![0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.property_active_dot))
            binding.saleviewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }
                override fun onPageSelected(position: Int) {
                    for (i in 0 until dotscount) {
                        dots!![i].setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.propertynon_active_dot))
                    }
                    dots!![position].setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.property_active_dot))
                }
                override fun onPageScrollStateChanged(state: Int) {
                }
            })
        })

        binding.skip.setOnClickListener {
            val inten = Intent(this@IntroductionActivity, LoginActivity::class.java)
        //    val inten = Intent(this@IntroductionActivity, HomeActivity::class.java)
            startActivity(inten)
            finish()
        }

/*        binding.nextbtn.setOnClickListener {
         var viewpagerpostion =   saleviewpager.getCurrentItem()
            Log.e("VIEWPAGERPOSITION","viewpager position....."+viewpagerpostion)
            if(viewpagerpostion==0)
            {
                binding.saleviewpager.currentItem=1
            }
            else if(viewpagerpostion==1)
            {
                binding.saleviewpager.currentItem=2
            }
            else if(viewpagerpostion==2)
            {
                val inten = Intent(this@IntroductionActivity, LoginActivity::class.java)
                startActivity(inten)
                finish()
            }

        }*/
    }

}