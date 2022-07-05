package it.units.alcoholestimator.logic;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    public static final String DRINK_TYPE_KEY = "drinkType";
    public static final String ALCOHOL_CONTENT_KEY = "alcoholContent";
    public static final String DRINK_SIZE_KEY = "drinkSize";
    public static final String DATE_KEY = "date";
    public static final String EMAIL_KEY = "email";
    public static final String GENDER_KEY = "gender";
    public static final String WEIGHT_KEY = "weight";
    public static final String USERS = "users";
    public static final String DRINKS = "drinks";
    public static final long MILLISECODS_PER_DAY = 86400000L;

    public static FirebaseFirestore getDatabase(){
        return FirebaseFirestore.getInstance();
    }

    public static void addUser(String email, Gender gender, int weight){
        // Create a new drink with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put(EMAIL_KEY, email);
        user.put(GENDER_KEY, gender);
        user.put(WEIGHT_KEY, weight);

        // Add a new document with a generated ID
        getDatabase().collection(USERS)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        User.setId(documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static void addDrink(String drinkType, int alcoholContent, int drinkSize, Date date){
        // Create a new drink with a first and last name
        Map<String, Object> drink = new HashMap<>();
        drink.put(DRINK_TYPE_KEY, drinkType);
        drink.put(ALCOHOL_CONTENT_KEY, alcoholContent);
        drink.put(DRINK_SIZE_KEY, drinkSize);
        drink.put(DATE_KEY, date);

        // Add a new document with a generated ID
        getDatabase().collection(USERS+"/" + User.getId() + "/" + DRINKS)
                .add(drink)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static void fetchUserDrinks(){
        getDatabase().collection(USERS+"/"+User.getId()+"/"+DRINKS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Date date = (Date) document.get(DATE_KEY);
                                // TODO fetch only date of the previous 24 hours
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public static boolean isRecent(Calendar calendar){
        long now = System.currentTimeMillis();
        long time = calendar.getTimeInMillis();
        long deltaTime = Math.abs(now - time);
        return deltaTime <= MILLISECODS_PER_DAY;
    }


}
