package com.gmail.fattazzo.formula1world

import org.junit.runners.model.InitializationError
import org.robolectric.RobolectricTestRunner

class CustomRobolectricRunner @Throws(InitializationError::class)
constructor(testClass: Class<*>) : RobolectricTestRunner(testClass) {
    init {
        val buildVariant = (if (BuildConfig.FLAVOR == "") "" else BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE
        var intermediatesPath = BuildConfig::class.java.getResource("")
                .toString().replace("file:", "")
        intermediatesPath = intermediatesPath
                .substring(0, intermediatesPath.indexOf("/classes"))

        System.setProperty("android.package",
                BuildConfig.APPLICATION_ID)
        System.setProperty("android.assets",
                intermediatesPath + "/assets/" + buildVariant)
    }
}