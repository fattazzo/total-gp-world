package com.gmail.fattazzo.formula1world

import com.activeandroid.ActiveAndroid
import com.activeandroid.app.Application

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ActiveAndroid.initialize(this)
    }
}