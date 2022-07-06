package it.units.alcoholestimator.logic;

import android.app.AlertDialog;
import android.content.Context;

public class StaticUtils {
    public static void showMessage(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
