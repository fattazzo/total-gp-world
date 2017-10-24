package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class ConstructorStandings(private val position: Int, private val positionText: String, points: Int, private val wins: Int, private val constructor: Constructor?) {
    private val points: Float

    init {
        this.points = points.toFloat()
    }

    fun toF1ConstructorStandings(): F1ConstructorStandings {
        val f1ConstructorStandings = F1ConstructorStandings()
        f1ConstructorStandings.points = this.points
        f1ConstructorStandings.position = this.position
        f1ConstructorStandings.positionText = this.positionText
        f1ConstructorStandings.wins = this.wins
        if (this.constructor != null) {
            f1ConstructorStandings.constructor = this.constructor.toF1Constructor()
        }
        return f1ConstructorStandings
    }

    override fun toString(): String {
        return "ConstructorStandings{" +
                "position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", constructor=" + constructor +
                '}'
    }
}
