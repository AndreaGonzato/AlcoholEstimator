package it.units.alcoholestimator.logic;

import java.util.Date;

public class Drink implements Comparable<Drink>{
    private final String cloudId;
    private final String description;
    private final int sizeMl;
    private final float alcoholContentPercentage;
    private final Date assumption;

    public Drink(String cloudId, String description, int sizeMl, float alcoholContentPercentage, Date assumption) {
        this.cloudId = cloudId;
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

    public String getCloudId() {
        return cloudId;
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

    @Override
    public int compareTo(Drink otherDrink) {
        if (assumption == null || otherDrink.getAssumption() == null) {
            return 0;
        }
        return assumption.compareTo(otherDrink.getAssumption());
    }
}
