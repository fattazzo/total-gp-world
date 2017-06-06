package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dspot.declex.api.localdb.LocalDBModel;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;

import java.io.Serializable;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "constructors")
public class Constructor extends Model implements Serializable {

    @Column
    String constructorRef;

    @Column
    String url;

    @Column
    String name;

    @Column
    String nationality;

    public F1Constructor toF1Constructor() {
        F1Constructor f1Constructor = new F1Constructor();
        f1Constructor.constructorRef = this.constructorRef;
        f1Constructor.name = this.name;
        f1Constructor.nationality = this.nationality;
        f1Constructor.url = this.url;
        return f1Constructor;
    }

    @Override
    public String toString() {
        return "Constructor{" +
                "constructorId=" + getId() +
                ", constructorRef='" + constructorRef + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
