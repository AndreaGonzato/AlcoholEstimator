package it.units.alcoholestimator.logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.stream.Collectors;

public class AlcoholContentCalculator {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static float calculateAlcoholContent(Gender userGender, List<Drink> recentDrinks){
        // filter and get all the drinks are all recent in the last 24 hours
        recentDrinks = recentDrinks.stream().filter(drink -> StaticUtils.isRecent(drink.getAssumption())).collect(Collectors.toList());



        return 999f;
    }
}
