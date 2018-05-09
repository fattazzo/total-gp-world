/*
 * Project: total-gp-world
 * File: Constructor.kt
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

package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1Constructor

import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "constructors")
class Constructor : Model(), Serializable {

    @Column
    internal var constructorRef: String? = null

    @Column
    internal var url: String? = null

    @Column
    var name: String? = null

    @Column
    var nationality: String? = null

    fun toF1Constructor(): F1Constructor {
        val f1Constructor = F1Constructor()
        f1Constructor.constructorRef = this.constructorRef
        f1Constructor.name = this.name
        f1Constructor.nationality = this.nationality
        f1Constructor.url = this.url
        return f1Constructor
    }

    override fun toString(): String {
        return "Constructor{" +
                "constructorId=" + id +
                ", constructorRef='" + constructorRef + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                '}'
    }
}
