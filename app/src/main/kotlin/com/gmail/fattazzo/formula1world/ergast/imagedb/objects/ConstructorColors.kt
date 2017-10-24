package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

/**
 * @author fattazzo
 *
 *
 * date: 13/07/17
 */
@Table(name = "constructorColors")
class ConstructorColors : Model() {

    @Column
    var hex: String? = null

    @Column
    var year: Int = 0

    @Column(name = "constructorId")
    var constructor: Constructor? = null

    @Column(name = "driverId")
    var driver: Driver? = null

    @Column
    internal var rgbRed: Int = 0

    @Column
    internal var rgbGreen: Int = 0

    @Column
    internal var rgbBlue: Int = 0

    override fun toString(): String {
        return "ConstructorColors{" +
                "year=" + year +
                ", constructor=" + constructor +
                ", driver=" + driver +
                ", rgbRed=" + rgbRed +
                ", rgbGreen=" + rgbGreen +
                ", rgbBlue=" + rgbBlue +
                ", hex='" + hex + '\'' +
                '}'
    }
}
