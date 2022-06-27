package it.units.alcoholestimator.logic;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

public class User {
    private static Gender gender;
    private static int weight;
    private static boolean isSignedIn = false;

    public static void setGender(Gender gender) {
        User.gender = gender;
    }

    public static void setWeight(int weight) {
        User.weight = weight;
    }

    public static Gender getGender() {
        return User.gender;
    }

    public static int getWeight() {
        return User.weight;
    }

    public static boolean isIsSignedIn() {
        return isSignedIn;
    }

    public static void setIsSignedIn(boolean isSignedIn) {
        User.isSignedIn = isSignedIn;
    }
}
