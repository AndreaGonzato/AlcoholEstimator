package it.units.alcoholestimator;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.temporal.ChronoUnit;
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

    @Test
    public void isThisIntervalLong5Minutes() {
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.set(nowCalendar.get(Calendar.YEAR), nowCalendar.get(Calendar.MONTH), nowCalendar.get(Calendar.DATE), nowCalendar.get(Calendar.HOUR), nowCalendar.get(Calendar.MINUTE));

        Calendar next5MinutesCalendar = Calendar.getInstance();
        next5MinutesCalendar.set(next5MinutesCalendar.get(Calendar.YEAR), next5MinutesCalendar.get(Calendar.MONTH), next5MinutesCalendar.get(Calendar.DATE), next5MinutesCalendar.get(Calendar.HOUR), next5MinutesCalendar.get(Calendar.MINUTE)+5);
        Date nowDate = new Date(nowCalendar.getTimeInMillis());
        Date next5MinutesDate = new Date(next5MinutesCalendar.getTimeInMillis());

        assertEquals(5, TimeManagerStaticUtils.getDuration(nowDate, next5MinutesDate).toMinutes());

    }



}