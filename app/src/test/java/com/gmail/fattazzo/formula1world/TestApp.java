package com.gmail.fattazzo.formula1world;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class TestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}