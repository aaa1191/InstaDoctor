package com.softgates.instadoctor.introduction

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.softgates.instadoctor.network.PropertiesImages

class PropertySliderAdapter (fragmentManager: FragmentManager, private val movies: MutableList<PropertiesImages>) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        // return AdvertismentView.newInstance(movies[position % movies.size])
        if(position==0)
        {
          return  IntroductionOneView()
        }
        else if(position==1)
        {
          return  IntroductionTwoView()
        }
        else if(position==2)
        {
          return  IntroductionThreeView()
        }
        else
        {
            return PropertySliderView.newInstance(movies.get(position))
        }
    }

    // 3
    override fun getCount(): Int {
        //  return movies.size * MAX_VALUE
        return movies.size
    }
//  override fun getPageTitle(position: Int): CharSequence {
//    return movies[position % movies.size].title
//  }
}