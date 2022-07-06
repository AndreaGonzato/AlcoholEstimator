package it.units.alcoholestimator.logic;

import java.util.Date;

public class Drink {
    private final String description;
    private final int sizeMl;
    private final float alcoholContentPercentage;
    private final Date assumption;

    public Drink(String description, int sizeMl, float alcoholContentPercentage, Date assumption) {
        this.description = description;
        this.sizeMl = sizeMl;
        this.alcoholContentPercentage = alcoholContentPercentage;
        this.assumption = assumption;
    }

    public String getDescription() {
        return description;
    }

    public int getSizeMl() {
        return sizeMl;
    }

    public float getAlcoholContentPercentage() {
        return alcoholContentPercentage;
    }

    public Date getAssumption() {
        return assumption;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "description='" + description + '\'' +
                ", sizeMl=" + sizeMl +
                ", alcoholContentPercentage=" + alcoholContentPercentage +
                ", assumption=" + assumption +
                '}';
    }
}
