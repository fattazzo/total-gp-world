package com.gmail.fattazzo.formula1world.resources;

import android.graphics.Bitmap;

import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.ergast.json.service.OnlineDataService;
import com.gmail.fattazzo.formula1world.ergast.json.service.OnlineDataService_;
import com.gmail.fattazzo.formula1world.utils.CountryNationality;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.ImageUtils_;
import com.gmail.fattazzo.formula1world.utils.LocaleUtils;
import com.gmail.fattazzo.formula1world.utils.LocaleUtils_;
import com.gmail.fattazzo.formula1world.utils.Utils;
import com.gmail.fattazzo.formula1world.utils.Utils_;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author fattazzo
 *         <p/>
 *         date: 29/06/17
 */
@RunWith(CustomRobolectricRunner.class)
public class AssetsImagesTest {

    private Utils utils;
    private LocaleUtils localeUtils;
    private ImageUtils imageUtils;
    private OnlineDataService dataService;

    @Before
    public void setup() {
        utils = Utils_.getInstance_(RuntimeEnvironment.application.getApplicationContext());
        localeUtils = LocaleUtils_.getInstance_(RuntimeEnvironment.application.getApplicationContext());
        imageUtils = ImageUtils_.getInstance_(RuntimeEnvironment.application.getApplicationContext());
        dataService = OnlineDataService_.getInstance_(RuntimeEnvironment.application.getApplicationContext());


    }

    @Test
    public void testCircuitsImage() {
        List<F1Race> races = dataService.loadRaces();
        for (F1Race race : races) {
            Bitmap img = imageUtils.getCircuitForCode(race.getCircuit().getCircuitRef());
            assertNotNull("Circuit " + race.getCircuit().getName() + "(" + race.getCircuit().getCircuitRef() + "): image not found", img);
            System.out.println(race.getCircuit().getName() + " image: OK");
        }
    }

    @Test
    public void testCircuitsFlag() {
        List<F1Race> races = dataService.loadRaces();
        for (F1Race race : races) {
            String countryCode = localeUtils.getCountryCode(race.getCircuit().getLocation().getCountry());
            Bitmap img = imageUtils.getFlagForCountryCode(countryCode);
            assertNotNull("Circuit " + race.getCircuit().getName() + "(" + race.getCircuit().getCircuitRef() + "): flag not found", img);
            System.out.println(race.getCircuit().getName() + " image: OK");
        }
    }

    @Test
    public void testDriversFlag() {
        List<F1Driver> drivers = dataService.loadDrivers();
        for (F1Driver driver : drivers) {
            Bitmap img = null;
            CountryNationality countryNationality = utils.getCountryNationality(driver.getNationality());
            if (countryNationality != null) {
                img = imageUtils.getFlagForCountryCode(countryNationality.getAlpha2Code());
            }
            assertNotNull("Driver " + driver.getDriverRef() + " (" + driver.getNationality() + "): flag image not found", img);
            System.out.println("Driver " + driver.getDriverRef() + " (" + driver.getNationality() + ") flag image: OK");
        }
    }

    @Test
    public void testConstructorsFlag() {
        List<F1Constructor> constructors = dataService.loadConstructors();
        for (F1Constructor constructor : constructors) {
            Bitmap img = null;
            CountryNationality countryNationality = utils.getCountryNationality(constructor.getNationality());
            if (countryNationality != null) {
                img = imageUtils.getFlagForCountryCode(countryNationality.getAlpha2Code());
            }
            assertNotNull("Constructor " + constructor.getName() + " (" + constructor.getNationality() + "): flag image not found", img);
            System.out.println("Constructor " + constructor.getName() + " (" + constructor.getNationality() + ") flag image: OK");
        }
    }
}
