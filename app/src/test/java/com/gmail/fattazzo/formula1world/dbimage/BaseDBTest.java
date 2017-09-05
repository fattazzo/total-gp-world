package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.util.ReflectionUtils;

import org.junit.Before;
import org.robolectric.RuntimeEnvironment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 04/09/17
 */
public class BaseDBTest {

    @Before
    public void initDB() {
        Cache.clear();
        ActiveAndroid.dispose();

        String aaName = ReflectionUtils.getMetaData(RuntimeEnvironment.application, "AA_DB_NAME");

        RuntimeEnvironment.application.deleteDatabase(aaName);

        ActiveAndroid.initialize(RuntimeEnvironment.application);
    }
}
