package com.softgates.instadoctor.introduction

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class IntroductionAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            /*     0 -> {
                     FirstFragment()
                 }*/
            0 -> PropertySliderView()
            1 -> PropertySliderView()
            2 -> PropertySliderView()
            3 -> PropertySliderView()
            else -> {
                return PropertySliderView()
            }
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            //   0 -> "First Tab"
            0 -> "Townhouses"
            1 -> "2-4 Br"
            2 -> "Price (Min-Max)"
            3 -> "Size (Min-Max)"
            else -> {
                return "Filter"
            }
        }
    }

}