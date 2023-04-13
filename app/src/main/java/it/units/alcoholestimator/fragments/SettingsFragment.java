package it.units.alcoholestimator.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Objects;

import it.units.alcoholestimator.R;
import it.units.alcoholestimator.activity.UserDataSettingActivity;
import it.units.alcoholestimator.logic.Drink;
import it.units.alcoholestimator.database.FirebaseDatabaseManager;
import it.units.alcoholestimator.database.LocalDatabaseHelper;
import it.units.alcoholestimator.logic.SignIn;
import it.units.alcoholestimator.logic.User;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signOutButton = requireView().findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(view1 -> signOut());

        Button showUserInfoButton = requireView().findViewById(R.id.showUserInfoButton);
        showUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = LocalDatabaseHelper.getAllData();
                if (cursor.getCount() == 0) {
                    // show message
                    showMessage("Error", "No user found in the local database", getContext());
                } else {
                    StringBuilder buffer = new StringBuilder();
                    while (cursor.moveToNext()) {
                        buffer.append("EMAIL: ").append(cursor.getString(2)).append("\n");
                        buffer.append("GENDER: ").append(cursor.getString(3)).append("\n");
                        buffer.append("WEIGHT: ").append(cursor.getString(4)).append("\n");
                        buffer.append("IS_SIGNED_IN: ").append(cursor.getString(5)).append("\n\n");
                    }

                    // show all data
                    showMessage("User", buffer.toString(), getContext());

                }
            }
        });

        Button changeUserDataButton = requireView().findViewById(R.id.changeUserDataButton);
        changeUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.setGender(null);
                User.setWeight(70);
                Intent intent = new Intent(getContext(), UserDataSettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button deleteDrinksButton = requireView().findViewById(R.id.deleteDrinksButton);
        deleteDrinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Drink drink : User.getDrinks()) {
                    FirebaseDatabaseManager.deleteDrink(drink.getCloudId());
                }
            }
        });
    }

    private void signOut() {
        LocalDatabaseHelper.emptyUserTable();
        User.setIsSignedInWithGoogle(false);
        User.setCloudID(null);
        User.setEmail(null);
        Activity activity = getActivity();
        GoogleSignInOptions googleOptions = SignIn.getGoogleSignInOptions();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(activity), googleOptions);

        Intent intent = new Intent(getContext(), UserDataSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        googleSignInClient.signOut();
    }

    private static void showMessage(String title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}