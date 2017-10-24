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
