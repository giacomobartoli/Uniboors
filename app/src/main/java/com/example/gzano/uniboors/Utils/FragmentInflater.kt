package com.example.gzano.uniboors.Utils

import android.support.v4.app.Fragment
import org.jetbrains.annotations.NotNull

/**
 * Created by gzano on 26/09/2017.
 */
interface  FragmentInflater<in T: Fragment> {
    fun inflateFragment(@NotNull fragment: T)
}