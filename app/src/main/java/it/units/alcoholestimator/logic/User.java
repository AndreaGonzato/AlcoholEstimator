package it.units.alcoholestimator.logic;

import android.database.Cursor;

import java.sql.SQLException;
import java.util.List;

import it.units.alcoholestimator.database.LocalDatabaseHelper;

public class User {
    private static String cloudID;
    private static String email;
    private static Gender gender;
    private static int weight;
    private static boolean isSignedInWithGoogle = false;
    private static List<Drink> recentDrinks;

    public static void loadUserFromLocalDatabase() throws SQLException {
        Cursor cursor = LocalDatabaseHelper.getAllData();
        if(cursor.getCount() == 0){
            throw new SQLException("Error: No data found for the local user");
        }else {
            while (cursor.moveToNext()){
                User.setCloudID(cursor.getString(1));
                User.setEmail(cursor.getString(2));
                switch (cursor.getString(3)){
                    case "MALE":
                        User.setGender(Gender.MALE);
                        break;
                    case "FEMALE":
                        User.setGender(Gender.FEMALE);
                        break;
                }

                User.setWeight(Integer.parseInt(cursor.getString(4)));
                switch (cursor.getString(5)){
                    case "true":
                        User.setIsSignedInWithGoogle(true);
                        break;
                    case "false":
                        User.setIsSignedInWithGoogle(false);
                        break;
                }
            }

        }
    }

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

    public static boolean isIsSignedInWithGoogle() {
        return isSignedInWithGoogle;
    }

    public static void setIsSignedInWithGoogle(boolean isSignedInWithGoogle) {
        User.isSignedInWithGoogle = isSignedInWithGoogle;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getCloudID() {
        return cloudID;
    }

    public static void setCloudID(String cloudID) {
        User.cloudID = cloudID;
    }

    public static List<Drink> getRecentDrinks() {
        return recentDrinks;
    }

    public static void setRecentDrinks(List<Drink> recentDrinks) {
        User.recentDrinks = recentDrinks;
    }
}
