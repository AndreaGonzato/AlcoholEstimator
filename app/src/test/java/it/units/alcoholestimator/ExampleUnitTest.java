package it.units.alcoholestimator;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.units.alcoholestimator.logic.DatabaseManager;
import it.units.alcoholestimator.logic.Gender;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void isNowRecent() {
        assertTrue(DatabaseManager.isRecent(new GregorianCalendar()));
    }

    @Test
    public void isTodayRecent() {
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DATE));
        assertTrue(DatabaseManager.isRecent(todayCalendar));
    }


}