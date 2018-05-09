/*
 * Project: total-gp-world
 * File: ProjectsInfoService.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gmail.fattazzo.formula1world.service

import com.gmail.fattazzo.formula1world.config.Config
import com.gmail.fattazzo.formula1world.domain.json.Info
import com.google.gson.GsonBuilder
import org.androidannotations.annotations.EBean
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author fattazzo
 *         <p/>
 *         date: 09/05/18
 */
@EBean(scope = EBean.Scope.Singleton)
open class ProjectsInfoService {

    /**
     * Carica l'input stream dall url specificato.
     *
     * @param stringUrl
     * url
     * @return input stream caricato
     * @throws Exception eccezione generica sul caricamento dell'input stream
     */
    private fun getInputStreamFromURL(stringUrl: String): InputStream {
        val input = URL(stringUrl)
        val conn = input.openConnection() as HttpURLConnection
        conn.connectTimeout = 15000
        conn.readTimeout = 15000

        return conn.inputStream
    }

    fun loadInfo(): Info? {

        return try {
            val stream = getInputStreamFromURL(Config.PROJECTS_INFO_URL)

            GsonBuilder().create().fromJson(InputStreamReader(stream), Info::class.java)
        } catch (e: Exception) {
            null
        }
    }
}