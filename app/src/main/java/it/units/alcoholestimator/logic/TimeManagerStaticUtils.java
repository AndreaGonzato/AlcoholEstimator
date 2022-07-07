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

    public static void showMessage(String title, String message, Context context){
        // TODO move this method in another class
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static boolean isRecent(Calendar calendar){
        long now = System.currentTimeMillis();
        long time = calendar.getTimeInMillis();
        long deltaTime = Math.abs(now - time);
        return deltaTime <= MILLISECONDS_PER_DAY;
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
