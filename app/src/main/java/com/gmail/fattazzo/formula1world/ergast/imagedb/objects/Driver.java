package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dspot.declex.api.localdb.LocalDBModel;
import com.gmail.fattazzo.formula1world.domain.F1Driver;

import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "drivers")
public class Driver extends Model implements Serializable {

    @Column
    String driverRef;

    @Column
    int number;

    @Column
    public String code;

    @Column
    public String forename;

    @Column
    String surname;

    @Column
    String dob;

    @Column
    String url;

    @Column
    String nationality;

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + getId() +
                ", driverRef='" + driverRef + '\'' +
                ", number=" + number +
                ", code='" + code + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", url='" + url + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }

    public F1Driver toF1Driver() {
        F1Driver f1Driver = new F1Driver();
        f1Driver.driverRef = this.driverRef;
        f1Driver.code = this.code;
        try {
            f1Driver.dateOfBirth = DateUtils.parseDate(this.dob, "yyyy-MM-dd");
        } catch (Exception e) {
            f1Driver.dateOfBirth = null;
        }
        f1Driver.familyName = this.surname;
        f1Driver.givenName = this.forename;
        f1Driver.number = this.number;
        f1Driver.url = this.url;
        f1Driver.nationality = this.nationality;

        return f1Driver;
    }
}
