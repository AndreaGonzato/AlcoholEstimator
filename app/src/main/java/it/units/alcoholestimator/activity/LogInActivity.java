package it.units.alcoholestimator.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        GoogleSignInOptions googleOptions = SignIn.getGoogleSignInOptions();
        googleSignInClient = GoogleSignIn.getClient(this, googleOptions);

        // Set the dimensions of the sign-in button.
        SignInButton signInGoogleButton = findViewById(R.id.signInButton);
        signInGoogleButton.setSize(SignInButton.SIZE_STANDARD);
        signInGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        LocalDatabaseHelper localDB = new LocalDatabaseHelper(this);

        try {
            User.loadUserFromLocalDatabase(); // TODO when start the app need to bo done
        }catch (SQLException e){
            // there are no data to load for the user in the local database
            Log.i("TEST", "no data for the user in the database");
            // TODO do I need to do something?
        }

        Button skipLogInButton = findViewById(R.id.skipLogInButton);
        skipLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, UserDataSettingActivity.class));
            }
        });
    }


    private void signIn() {
        User.setIsSignedInWithGoogle(true);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityIntent.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==RESULT_CANCELED){
                        Log.i("IMPORTANT", "CANCELED Intent");
                        User.setIsSignedInWithGoogle(false);
                    }else {
                        User.setIsSignedInWithGoogle(true);

                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        assert account != null;
                        User.setEmail(account.getEmail());
                        FirebaseDatabaseManager.addUser(User.getEmail(), User.getGender(), User.getWeight());
                    }
                }

            });

}