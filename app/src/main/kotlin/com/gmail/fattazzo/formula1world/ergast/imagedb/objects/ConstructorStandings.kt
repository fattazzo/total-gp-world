package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "constructorStandings")
class ConstructorStandings : Model() {

    @Column
    internal var raceId: Int = 0

    @Column
    internal var points: Float = 0.toFloat()

    @Column
    internal var position: Int = 0

    @Column
    internal var positionText: String? = null

    @Column
    internal var wins: Int = 0

    @Column(name = "constructorId")
    internal var constructor: Constructor? = null

    fun toF1ConstructorStandings(): F1ConstructorStandings {
        val f1ConstructorStandings = F1ConstructorStandings()
        f1ConstructorStandings.points = this.points
        f1ConstructorStandings.position = this.position
        f1ConstructorStandings.positionText = this.positionText
        f1ConstructorStandings.wins = this.wins
        if (this.constructor != null) {
            f1ConstructorStandings.constructor = this.constructor!!.toF1Constructor()
        }
        return f1ConstructorStandings
    }

    override fun toString(): String {
        return "ConstructorStandings{" +
                "raceId=" + raceId +
                ", points=" + points +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", wins=" + wins +
                ", constructor=" + constructor +
                '}'
    }
}
