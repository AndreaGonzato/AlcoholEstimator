package it.units.alcoholestimator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import it.units.alcoholestimator.logic.Drink;

public class DrinkTest {
    @Test
    public void orderOfDrinksAfterSort() {
        Drink recentDrink = new Drink("id1", "beer", 100, 5.5f, new Date());
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DATE), todayCalendar.get(Calendar.HOUR), todayCalendar.get(Calendar.MINUTE));
        Date today = new Date(todayCalendar.getTimeInMillis());
        Drink firstDrinkOfTheDay = new Drink("id2", "wine", 200, 10.2f, today);

        List<Drink> drinks = new LinkedList<>();
        drinks.add(recentDrink);
        drinks.add(firstDrinkOfTheDay);

        Collections.sort(drinks);

        assertEquals(firstDrinkOfTheDay, drinks.get(0));

    }
}
