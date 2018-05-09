/*
 * Project: total-gp-world
 * File: App.kt
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

package com.gmail.fattazzo.formula1world.domain.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 09/05/18
 */
class App : Serializable {

    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("icon")
    @Expose
    lateinit var icon: String

    @SerializedName("storeUrl")
    @Expose
    var storeUrl: String? = null

    @SerializedName("projectUrl")
    @Expose
    lateinit var projectUrl: String

    @SerializedName("wiki")
    @Expose
    var wiki: String? = null

    @SerializedName("i18n")
    @Expose
    lateinit var i18n: List<I18n>

    fun getI18n(lang: String = I18n.DEFAULT): I18n? {
        i18n.forEach {
            if (it.lang == lang) {
                return it
            }
        }
        return null
    }

}