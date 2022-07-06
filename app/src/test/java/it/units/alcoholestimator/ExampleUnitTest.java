package it.units.alcoholestimator;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.units.alcoholestimator.logic.DatabaseManager;
import it.units.alcoholestimator.logic.Gender;
import it.units.alcoholestimator.logic.StaticUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void isNowCalendarRecent() {
        assertTrue(StaticUtils.isRecent(new GregorianCalendar()));
    }

    @Test
    public void isNowDateRecent() {
        assertTrue(StaticUtils.isRecent(new Date()));
    }

    @Test
    public void isTodayCalendarRecent() {
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DATE));
        assertTrue(StaticUtils.isRecent(todayCalendar));
    }


}