package it.units.alcoholestimator.logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlcoholContentCalculator {

    private static final float MALE_DIFFUSION = 0.66f;
    private static final float FEMALE_DIFFUSION = 0.73f;
    private static final float SCALING_FACTOR = 0.00844f;
    private static final float ALCOHOL_DECREASE_PER_HOUR = 0.18f;
    private static final int MINUTES_IN_HOUR = 60;




    @RequiresApi(api = Build.VERSION_CODES.O)
    public static float calculateAlcoholContent(Gender userGender, int userWeight, List<Drink> recentDrinks){
        // filter and get all the drinks are all recent in the last 24 hours
        recentDrinks = recentDrinks.stream().filter(drink -> TimeManagerStaticUtils.isRecent(drink.getAssumption())).collect(Collectors.toList());

        // put in order all the drinks by the assumption filed that is a Date type
        Collections.sort(recentDrinks);

        float alcoholContent = 0f;

        // define the diffusion based on the sex of the user
        float diffusion = MALE_DIFFUSION;
        if(userGender.equals(Gender.FEMALE)){
            diffusion = FEMALE_DIFFUSION;
        }

        Map<Drink, Float> alcoholContentAtDinkMap = new HashMap<>(); // TODO remove this var?


        boolean firstDrink = true;
        Date lastProcessedDrinkDate = new Date();
        // TODO take in consideration the time of assumption
        for(Drink drink : recentDrinks){
            if (firstDrink){
                alcoholContent = drink.getAlcoholContentPercentage() * drink.getSizeMl() * SCALING_FACTOR / (userWeight * diffusion);
                alcoholContentAtDinkMap.put(drink, alcoholContent);
                lastProcessedDrinkDate = drink.getAssumption();
                firstDrink = false;
            }else {
                Duration intervalBetweenLastProcessedDrink = TimeManagerStaticUtils.getDuration(lastProcessedDrinkDate, drink.getAssumption());
                long minutesFromLastProcessedDrink = intervalBetweenLastProcessedDrink.toMinutes();

                float decreaseOfAlcoholFromLastProcessedDrink = minutesFromLastProcessedDrink * ALCOHOL_DECREASE_PER_HOUR / MINUTES_IN_HOUR;
                alcoholContent = Math.max(0, alcoholContent-decreaseOfAlcoholFromLastProcessedDrink);

                alcoholContent += drink.getAlcoholContentPercentage() * drink.getSizeMl() * SCALING_FACTOR / (userWeight * diffusion);
                alcoholContentAtDinkMap.put(drink, alcoholContent);
                lastProcessedDrinkDate = drink.getAssumption();
            }
        }

        Duration intervalBetweenLastDrink = TimeManagerStaticUtils.getDuration(lastProcessedDrinkDate, new Date());
        long minutesFromLastDrink = intervalBetweenLastDrink.toMinutes();

        float decreaseOfAlcoholFromLastDrink = minutesFromLastDrink * ALCOHOL_DECREASE_PER_HOUR / MINUTES_IN_HOUR;
        alcoholContent = Math.max(0, alcoholContent-decreaseOfAlcoholFromLastDrink);

        return alcoholContent;
    }
}
