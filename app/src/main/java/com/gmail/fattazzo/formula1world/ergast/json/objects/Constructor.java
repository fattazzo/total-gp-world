package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;

import java.io.Serializable;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class Constructor implements Serializable {
    private String constructorId;
    private String url;
    private String name;
    private String nationality;

    public Constructor(String circuitId, String url, String name, String nationality) {
        this.constructorId = circuitId;
        this.url = url;
        this.name = name;
        this.nationality = nationality;
    }

    public F1Constructor toF1Constructor() {
        F1Constructor f1Constructor = new F1Constructor();
        f1Constructor.constructorRef = this.constructorId;
        f1Constructor.name = this.name;
        f1Constructor.nationality = this.nationality;
        f1Constructor.url = this.url;
        return f1Constructor;
    }

    @Override
    public String toString() {
        return "Constructor{" +
                "constructorId='" + constructorId + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
