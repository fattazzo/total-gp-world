package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "constructorStandings")
public class ConstructorStandings extends Model {

    @Column
    int raceId;

    @Column
    float points;

    @Column
    int position;

    @Column
    String positionText;

    @Column
    int wins;

    @Column(name = "constructorId")
    Constructor constructor;

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
                "raceId=" + raceId +
                ", points=" + points +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", wins=" + wins +
                ", constructor=" + constructor +
                '}';
    }
}
