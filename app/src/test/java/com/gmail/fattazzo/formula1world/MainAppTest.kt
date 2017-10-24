package com.gmail.fattazzo.formula1world

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class MainAppTest {

    @Test
    @Throws(Exception::class)
    fun runtimeApplicationShouldBeTestApp() {
        // !!! Add MODULE_DIR in Run --> Edit Configurations --> Working directory
        val actualName = RuntimeEnvironment.application.javaClass.name
        val expectedName = TestApp::class.java.name

        Assert.assertTrue(actualName == expectedName)
    }
}