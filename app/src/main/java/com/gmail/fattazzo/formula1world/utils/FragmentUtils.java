package com.gmail.fattazzo.formula1world.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gmail.fattazzo.formula1world.R;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/10/17
 */
public class FragmentUtils {

    public static void replace(Activity activity, Fragment fragment, int containerResId) {
        FragmentTransaction transaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.replace(containerResId, fragment, fragment.getClass().getSimpleName().replace("_","")).commit();
    }

    public static void replace(Activity activity, Fragment fragment) {
        FragmentTransaction transaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName().replace("_","")).commit();
    }

    public static void add(Activity activity, Fragment fragment, int containerResId) {
        FragmentTransaction transaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.add(containerResId, fragment, fragment.getClass().getSimpleName().replace("_","")).commit();
    }

    public static void add(Activity activity, Fragment fragment) {
        FragmentTransaction transaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment, fragment.getClass().getSimpleName().replace("_","")).commit();
    }
}
