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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;
import java.util.concurrent.Executor;

import it.units.alcoholestimator.R;
import it.units.alcoholestimator.activity.DashboardActivity;
import it.units.alcoholestimator.activity.LogInActivity;
import it.units.alcoholestimator.activity.UserDataSettingActivity;
import it.units.alcoholestimator.logic.Drink;
import it.units.alcoholestimator.database.FirebaseDatabaseManager;
import it.units.alcoholestimator.database.LocalDatabaseHelper;
import it.units.alcoholestimator.logic.SignIn;
import it.units.alcoholestimator.logic.TimeManagerStaticUtils;
import it.units.alcoholestimator.logic.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        Button userInfoButton = requireView().findViewById(R.id.showUserInfoButton);
        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = LocalDatabaseHelper.getAllData();
                if(cursor.getCount() == 0){
                    // show message
                    showMessage("Error", "No user found in the local database", getContext());
                }else {
                    StringBuilder buffer = new StringBuilder();
                    while (cursor.moveToNext()){
                        buffer.append("ID: ").append(cursor.getString(0)).append("\n");
                        buffer.append("CLOUD_ID: ").append(cursor.getString(1)).append("\n");
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

        Button deleteDrinksButton = requireView().findViewById(R.id.deleteDrinksButton);
        deleteDrinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Drink drink : User.getDrinks()){
                    FirebaseDatabaseManager.deleteDrink(drink.getCloudId());
                }
            }
        });
    }

    private void signOut() {
        LocalDatabaseHelper.emptyUserTable();
        User.setIsSignedInWithGoogle(false);
        Activity activity = getActivity();
        GoogleSignInOptions googleOptions = SignIn.getGoogleSignInOptions();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(activity), googleOptions);

        Intent intent = new Intent(getContext(), LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        // TODO why there are no log?
        googleSignInClient.signOut()
                .addOnCompleteListener((Executor) this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("TEST", "signOut completed inside listener");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("TEST", "on success logout");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TEST", "on failure logout");
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.i("TEST", "on cancelled logout");
                    }
                });

        Log.i("TEST", "signOut completed outside all the listeners");
    }

    public static void showMessage(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}