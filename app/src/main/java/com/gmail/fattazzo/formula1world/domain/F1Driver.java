package com.gmail.fattazzo.formula1world.domain;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
public class F1Driver implements Serializable {

    public String driverRef;

    public int number;

    public String code;

    public String url;

    public String givenName;
    public String familyName;

    public Date dateOfBirth;

    public String nationality;

    public String getFullName() {
        return StringUtils.defaultString(givenName) + " " + StringUtils.defaultString(familyName);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverRef='" + driverRef + '\'' +
                ", number=" + number +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
