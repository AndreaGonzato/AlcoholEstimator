package it.units.alcoholestimator.logic;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class User {
    private static Gender gender;
    private static int weight;
    private static GoogleSignInClient googleSignInClient;

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

    public static GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public static void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        User.googleSignInClient = googleSignInClient;
    }
}
