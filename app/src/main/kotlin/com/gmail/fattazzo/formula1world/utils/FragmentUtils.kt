package com.gmail.fattazzo.formula1world.utils

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

import com.gmail.fattazzo.formula1world.R

/**
 * @author fattazzo
 *
 *
 * date: 20/10/17
 */
object FragmentUtils {

    @JvmOverloads fun replace(activity: Activity, fragment: Fragment, containerResId: Int = R.id.container) {
        val transaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(containerResId, fragment, fragment.javaClass.simpleName.replace("_", "")).commit()
    }

    @JvmOverloads fun add(activity: Activity, fragment: Fragment, containerResId: Int = R.id.container) {
        val transaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.add(containerResId, fragment, fragment.javaClass.simpleName.replace("_", "")).commit()
    }
}
