package com.gmail.fattazzo.formula1world;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class MainAppTest {

    @Test
    public void runtimeApplicationShouldBeTestApp() throws Exception {
        // !!! Add MODULE_DIR in Run --> Edit Configurations --> Working directory
        String actualName = RuntimeEnvironment.application.getClass().getName();
        String expectedName = TestApp.class.getName();

        Assert.assertTrue((actualName).equals(expectedName));
    }
}