package com.example.gzano.uniboors.Utils.Adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.example.gzano.uniboors.Fragments.RecyclerViewFragment
import com.example.gzano.uniboors.Utils.Constants

/**
 * Created by gzano on 04/10/2017.
 */
class PlacesPagerAdapter(private var fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment = RecyclerViewFragment()

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return Constants.CESENA_CAMPUS
            1 -> return Constants.YOUR_PLACES

        }
        return null.toString()
    }

    override fun getCount(): Int = Constants.PLACES_PAGE_NUMBER
}