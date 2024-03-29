package it.units.alcoholestimator.database;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.units.alcoholestimator.fragments.DashboardFragment;
import it.units.alcoholestimator.logic.Drink;
import it.units.alcoholestimator.logic.Gender;
import it.units.alcoholestimator.logic.User;

public class FirebaseDatabaseManager {

    public static final String DRINK_TYPE_KEY = "drinkType";
    public static final String ALCOHOL_CONTENT_KEY = "alcoholContent";
    public static final String DRINK_SIZE_KEY = "drinkSize";
    public static final String DATE_KEY = "date";

    public static final String EMAIL_KEY = "email";
    public static final String GENDER_KEY = "gender";
    public static final String WEIGHT_KEY = "weight";

    public static final String USERS = "users";
    public static final String DRINKS = "drinks";

    public static FirebaseFirestore getDatabase() {
        return FirebaseFirestore.getInstance();
    }

    public static void addUser(String email, Gender gender, int weight) {
        // Create a new user
        Map<String, Object> user = new HashMap<>();
        user.put(EMAIL_KEY, email);
        user.put(GENDER_KEY, gender);
        user.put(WEIGHT_KEY, weight);

        final String[] cloudID = new String[1];

        // Add a new document with a generated ID
        getDatabase().collection(USERS)
                .add(user)
                .onSuccessTask(documentReference -> {
                    User.setCloudID(documentReference.getId());
                    LocalDatabaseHelper.insertData(documentReference.getId(), User.getEmail(), User.getGender().representation, User.getWeight(), "false");
                    return null;
                });
    }

    public static void updateUser(String userKey, String email, Gender gender, int weight) {
        // Create a new user
        Map<String, Object> user = new HashMap<>();
        user.put(EMAIL_KEY, email);
        user.put(GENDER_KEY, gender);
        user.put(WEIGHT_KEY, weight);

        final String[] cloudID = new String[1];

        // Add a new document with a generated ID
        getDatabase().collection(USERS)
                .document(userKey)
                .update(user);
    }

    public static void addDrink(String drinkType, int alcoholContent, int drinkSize, Date date) {
        // Create a new drink with a first and last name
        Map<String, Object> drink = new HashMap<>();
        drink.put(DRINK_TYPE_KEY, drinkType);
        drink.put(ALCOHOL_CONTENT_KEY, alcoholContent);
        drink.put(DRINK_SIZE_KEY, drinkSize);
        drink.put(DATE_KEY, date);

        // Add a new document with a generated ID
        getDatabase().collection(USERS + "/" + User.getCloudID() + "/" + DRINKS)
                .add(drink)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public static void deleteDrink(String idDrinkDocumentToDelete) {
        getDatabase().collection(USERS + "/" + User.getCloudID() + "/" + DRINKS).document(idDrinkDocumentToDelete)
                .delete()
                .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));
    }

    public static void fetchUserDrinks(DashboardFragment dashboardFragment) {
        getDatabase().collection(USERS + "/" + User.getCloudID() + "/" + DRINKS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Drink> drinks = new LinkedList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Timestamp timestamp = (Timestamp) document.getData().get(DATE_KEY);
                                Date date = Objects.requireNonNull(timestamp).toDate();

                                // TODO do i need to optimize the download of only the drink in the last 24 hours? (since I don't expect lots of drinks for each user at the moment is not a priority this task)
                                String id = document.getId();

                                String description = (String) document.getData().get(DRINK_TYPE_KEY);

                                int size = Objects.requireNonNull((Long) document.getData().get(DRINK_SIZE_KEY)).intValue();

                                float alcoholContent = Objects.requireNonNull((Long) document.getData().get(ALCOHOL_CONTENT_KEY)).floatValue();
                                Log.i("TEST", "fetched drink: {description:" + description + " size:" + size + " alcoholContent: " + alcoholContent + " date:" + date + " }");

                                Drink drink = new Drink(id, description, size, alcoholContent, date);
                                Log.i("TEST", "drink: " + drink);
                                drinks.add(drink);

                            }

                            User.setDrinks(drinks);

                            dashboardFragment.updateGUIAfterDownloadDataFromCloud();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
