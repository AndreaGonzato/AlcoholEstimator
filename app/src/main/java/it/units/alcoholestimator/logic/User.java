package it.units.alcoholestimator.logic;

public class User {
    private static Gender gender;
    private static int weight;

    public static void setGender(Gender gender) {
        User.gender = gender;
    }

    public static void setWeight(int weight) {
        User.weight = weight;
    }

    public static Gender getGender() {
        return gender;
    }

    public static int getWeight() {
        return weight;
    }
}
