package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author fattazzo
 *         <p/>
 *         date: 13/07/17
 */
@Table(name = "constructorColors")
public class ConstructorColors extends Model {

    @Column
    int year;

    @Column(name = "constructorId")
    Constructor constructor;

    @Column
    int rgbRed;

    @Column
    int rgbGreen;

    @Column
    int rgbBlue;

    @Column
    public String hex;

    @Override
    public String toString() {
        return "ConstructorColors{" +
                "year=" + year +
                ", constructor=" + constructor +
                ", rgbRed=" + rgbRed +
                ", rgbGreen=" + rgbGreen +
                ", rgbBlue=" + rgbBlue +
                ", hex='" + hex + '\'' +
                '}';
    }
}
