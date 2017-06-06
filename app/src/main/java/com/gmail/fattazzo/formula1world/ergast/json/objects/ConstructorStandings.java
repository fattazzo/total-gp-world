package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class ConstructorStandings {
    private int position;
    private String positionText;
    private float points;
    private int wins;
    private Constructor constructor;

    public ConstructorStandings(int position, String positionText, int points, int wins, Constructor constructor) {
        this.position = position;
        this.positionText = positionText;
        this.points = points;
        this.wins = wins;
        this.constructor = constructor;
    }

    public F1ConstructorStandings toF1ConstructorStandings() {
        F1ConstructorStandings f1ConstructorStandings = new F1ConstructorStandings();
        f1ConstructorStandings.points = this.points;
        f1ConstructorStandings.position = this.position;
        f1ConstructorStandings.positionText = this.positionText;
        f1ConstructorStandings.wins = this.wins;
        if (this.constructor != null) {
            f1ConstructorStandings.constructor = this.constructor.toF1Constructor();
        }
        return f1ConstructorStandings;
    }

    @Override
    public String toString() {
        return "ConstructorStandings{" +
                "position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", constructor=" + constructor +
                '}';
    }
}
