/**
 * Copyright (C) 2016 DSpot Sp. z o.o
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gmail.fattazzo.formula1world;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.activeandroid.util.ReflectionUtils;
import com.gmail.fattazzo.formula1world.utils.IssueReporter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.zip.ZipInputStream;

public class F1WorldApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.dispose();

        String aaName = ReflectionUtils.getMetaData(getApplicationContext(), "AA_DB_NAME");

        deleteDatabase(aaName);

        copydatabase(aaName);

        ActiveAndroid.initialize(this);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public void copydatabase(String dbName) {

        String path = this.getDatabasePath(dbName).getParent();
        File targetDirectory = new File(path);

        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(this.getAssets().open("f1db.zip")))) {
            int count;
            byte[] buffer = new byte[8192];
            while ((zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, dbName);
                try (FileOutputStream fout = new FileOutputStream(file)) {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                }
            }
        } catch (final Exception e) {
            IssueReporter.openReportIssue(this,"Error on application initialization " + DateFormatUtils.format(Calendar.getInstance(),"dd/MM/yyyy hh:MM:ss"), ExceptionUtils.getStackTrace(e),true);
            e.printStackTrace();
        }
    }
}