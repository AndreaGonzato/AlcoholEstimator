package it.units.alcoholestimator.logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AlcoholContentCalculator {

    private static final float MALE_DIFFUSION = 0.66f;
    private static final float FEMALE_DIFFUSION = 0.73f;
    private static final float SCALING_FACTOR = 0.00844f;
    private static final float ALCOHOL_DECREASE_PER_HOUR = 0.18f;



    @RequiresApi(api = Build.VERSION_CODES.N)
    public static float calculateAlcoholContent(Gender userGender, int userWeight, List<Drink> recentDrinks){
        // filter and get all the drinks are all recent in the last 24 hours
        recentDrinks = recentDrinks.stream().filter(drink -> TimeManagerStaticUtils.isRecent(drink.getAssumption())).collect(Collectors.toList());

        // put in order all the drinks by the assumption filed that is a Date
        Collections.sort(recentDrinks);

        float alcoholContent = 0f;

        // define the diffusion based on the sex of the user
        float diffusion = MALE_DIFFUSION;
        if(userGender.equals(Gender.FEMALE)){
            diffusion = FEMALE_DIFFUSION;
        }


        // TODO take in consideration the time of assumption
        for(Drink drink : recentDrinks){
            alcoholContent = drink.getAlcoholContentPercentage() * drink.getSizeMl() * SCALING_FACTOR / (userWeight * diffusion);
        }

        return alcoholContent;
    }
}
