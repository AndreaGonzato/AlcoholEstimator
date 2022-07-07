package it.units.alcoholestimator;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.units.alcoholestimator.logic.TimeManagerStaticUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void isNowCalendarRecent() {
        assertTrue(TimeManagerStaticUtils.isRecent(new GregorianCalendar()));
    }

    @Test
    public void isNowDateRecent() {
        assertTrue(TimeManagerStaticUtils.isRecent(new Date()));
    }

    @Test
    public void isTodayCalendarRecent() {
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DATE));
        assertTrue(TimeManagerStaticUtils.isRecent(todayCalendar));
    }


}