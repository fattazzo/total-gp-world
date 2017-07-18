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
    public String hex;

    @Column
    public int year;

    @Column(name = "constructorId")
    public  Constructor constructor;

    @Column(name = "driverId")
    public Driver driver;

    @Column
    int rgbRed;

    @Column
    int rgbGreen;

    @Column
    int rgbBlue;

    @Override
    public String toString() {
        return "ConstructorColors{" +
                "year=" + year +
                ", constructor=" + constructor +
                ", driver=" + driver +
                ", rgbRed=" + rgbRed +
                ", rgbGreen=" + rgbGreen +
                ", rgbBlue=" + rgbBlue +
                ", hex='" + hex + '\'' +
                '}';
    }
}
