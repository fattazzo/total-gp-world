package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "driversConstructors")
class DriverConstructor : Model() {

    @Column
    internal var driverId: Long = 0

    @Column
    internal var constructorId: Long = 0

    @Column
    internal var year: Int = 0
}
