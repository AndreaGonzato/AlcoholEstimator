package it.units.alcoholestimator.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import java.sql.SQLException;

import it.units.alcoholestimator.R;
import it.units.alcoholestimator.database.FirebaseDatabaseManager;
import it.units.alcoholestimator.database.LocalDatabaseHelper;
import it.units.alcoholestimator.logic.SignIn;
import it.units.alcoholestimator.logic.User;

public class LogInActivity extends AppCompatActivity {

    private static GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        LocalDatabaseHelper localDB = new LocalDatabaseHelper(this);
        try {
            // when start the app need load the user data from the local database
            User.loadUserFromLocalDatabase();
            startActivity(new Intent(LogInActivity.this, DashboardActivity.class));

        }catch (SQLException e){
            // there are no data to load for the user in the local database
            Log.i("TEST", "no data for the user in the local database");
        }


        GoogleSignInOptions googleOptions = SignIn.getGoogleSignInOptions();
        googleSignInClient = GoogleSignIn.getClient(this, googleOptions);

        // Set the dimensions of the sign-in button.
        SignInButton signInGoogleButton = findViewById(R.id.signInButton);
        signInGoogleButton.setSize(SignInButton.SIZE_STANDARD);
        signInGoogleButton.setOnClickListener(view -> signIn());


        Button skipLogInButton = findViewById(R.id.skipLoginButton);
        skipLogInButton.setOnClickListener(view -> startActivity(new Intent(LogInActivity.this, UserDataSettingActivity.class)));
    }


    private void signIn() {
        User.setIsSignedInWithGoogle(true);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityIntent.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_CANCELED) {
                        Log.i("IMPORTANT", "CANCELED Intent");
                        User.setIsSignedInWithGoogle(false);
                    } else {
                        User.setIsSignedInWithGoogle(true);

                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        assert account != null;
                        User.setEmail(account.getEmail());
                        FirebaseDatabaseManager.addUser(User.getEmail(), User.getGender(), User.getWeight());

                        // change activity
                        startActivity(new Intent(LogInActivity.this, UserDataSettingActivity.class));
                    }
                }

            });

}