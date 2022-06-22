package it.units.alcoholestimator.logic;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class User {
    private static Gender gender;
    private static int weight;
    private static GoogleSignInAccount googleSignInAccount;

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
}
