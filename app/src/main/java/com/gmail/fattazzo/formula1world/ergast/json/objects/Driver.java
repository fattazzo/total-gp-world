package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Driver;

import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class Driver implements Serializable {

    private String driverId;
    private int permanentNumber;
    private String code;
    private String url;
    private String givenName;
    private String familyName;
    private String dateOfBirth;
    private String nationality;

    public Driver(String driverId, int permanentNumber, String code, String url, String givenName, String familyName, String dateOfBirth, String nationality) {
        this.driverId = driverId;
        this.permanentNumber = permanentNumber;
        this.code = code;
        this.url = url;
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public F1Driver toF1Driver() {
        F1Driver f1Driver = new com.gmail.fattazzo.formula1world.domain.F1Driver();
        f1Driver.driverRef = this.driverId;
        f1Driver.code = this.code;
        try {
            f1Driver.dateOfBirth = DateUtils.parseDate(this.dateOfBirth, "yyyy-MM-dd");
        } catch (Exception e) {
            f1Driver.dateOfBirth = null;
        }
        f1Driver.familyName = this.familyName;
        f1Driver.givenName = this.givenName;
        f1Driver.number = this.permanentNumber;
        f1Driver.url = this.url;
        f1Driver.nationality = this.nationality;

        return f1Driver;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", permanentNumber=" + permanentNumber +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
