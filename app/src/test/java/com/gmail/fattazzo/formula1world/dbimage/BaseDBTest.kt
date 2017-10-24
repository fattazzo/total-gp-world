package com.gmail.fattazzo.formula1world.dbimage

import com.activeandroid.ActiveAndroid
import com.activeandroid.Cache
import com.activeandroid.util.ReflectionUtils

import org.junit.Before
import org.robolectric.RuntimeEnvironment

/**
 * @author fattazzo
 *
 *
 * date: 04/09/17
 */
open class BaseDBTest {

    @Before
    open fun initDB() {
        Cache.clear()
        ActiveAndroid.dispose()

        val aaName = ReflectionUtils.getMetaData<String>(RuntimeEnvironment.application, "AA_DB_NAME")

        RuntimeEnvironment.application.deleteDatabase(aaName)

        ActiveAndroid.initialize(RuntimeEnvironment.application)
    }
}
