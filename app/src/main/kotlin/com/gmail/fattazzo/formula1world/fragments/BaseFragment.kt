package com.gmail.fattazzo.formula1world.fragments

import android.support.v4.app.Fragment

import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment_
import com.gmail.fattazzo.formula1world.utils.FragmentUtils

/**
 * @author fattazzo
 *
 *
 * date: 19/10/17
 */
open class BaseFragment : Fragment() {

    open fun backPressed() {
        FragmentUtils.replace(activity, HomeFragment_())
    }
}
