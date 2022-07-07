package it.units.alcoholestimator.logic;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class TimeManagerStaticUtils {

    public static final long MILLISECONDS_PER_DAY = 86400000L;

    private static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static boolean isRecent(Calendar calendar){
        long now = System.currentTimeMillis();
        long time = calendar.getTimeInMillis();
        long deltaTime = now - time;
        return deltaTime <= MILLISECONDS_PER_DAY && deltaTime >= 0;
    }

    public static boolean isRecent(Date date){
        return isRecent(dateToCalendar(date));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Duration getDuration(Date start, Date end){
        Instant startInstant = start.toInstant();
        Instant endInstant = end.toInstant();
        return Duration.between(startInstant, endInstant);
    }
}
