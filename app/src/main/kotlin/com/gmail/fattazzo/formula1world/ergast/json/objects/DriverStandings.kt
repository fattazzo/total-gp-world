package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1DriverStandings

import org.apache.commons.collections4.CollectionUtils

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class DriverStandings(private val position: Int, private val positionText: String, private val points: Float, private val wins: Int, private val driver: Driver?, private val constructors: List<Constructor>) {

    fun toF1DriverStandings(): F1DriverStandings {
        val f1DriverStandings = F1DriverStandings()
        f1DriverStandings.position = this.position
        f1DriverStandings.positionText = this.positionText
        f1DriverStandings.wins = this.wins
        f1DriverStandings.points = this.points
        if (this.driver != null) {
            f1DriverStandings.driver = this.driver.toF1Driver()
        }
        if (CollectionUtils.isNotEmpty(this.constructors)) {
            f1DriverStandings.constructor = this.constructors[0].toF1Constructor()
        }
        return f1DriverStandings
    }

    override fun toString(): String {
        return "DriverStandings{" +
                "position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", driver=" + driver +
                ", constructors=" + constructors +
                '}'
    }
}
