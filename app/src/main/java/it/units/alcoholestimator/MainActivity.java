package it.units.alcoholestimator;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;
import java.util.concurrent.Executor;

import it.units.alcoholestimator.logic.Gender;
import it.units.alcoholestimator.logic.User;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_WEIGHT = 70;
    // TODO when this class is almost finish decide what to do with the fields. Can i make it local var?
    private NumberPicker numberPicker;
    private Button maleButton;
    private Button femaleButton;
    private Button saveButton;

    private static final int RC_SIGN_IN = 9001;
    private static GoogleSignInClient googleSignInClient;

    // constants
    private static final float WEIGHT_FOR_SEX_SELECTED = 2.0f;
    private static final int SCALAR_PIXEL_SIZE_FOR_PRESSED_BUTTON_SEX = 25;
    private static final int SCALAR_PIXEL_SIZE_FOR_UNPRESSED_BUTTON_SEX = 14;

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account == null || !User.isIsSignedIn()){
            // first time that the user is doing the log in
            Log.i("TEST", "welcome for the first time");
        }else {
            // user has already log in
            Log.i("TEST", "welcome again");
            Log.i("TEST", "" + account.getEmail());
            // TODO decide if user has already log in to start a new activity
            // startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Set the dimensions of the sign-in button.
        SignInButton signInGoogleButton = findViewById(R.id.signInButton);
        signInGoogleButton.setSize(SignInButton.SIZE_STANDARD);
        signInGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        GoogleSignInOptions googleOptions = MainActivity.getGoogleSignInOptions();
        googleSignInClient = GoogleSignIn.getClient(this, googleOptions);

        maleButton = findViewById(R.id.male);
        saveButton = findViewById(R.id.saveButton);
        femaleButton = findViewById(R.id.female);

        maleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User.setGender(Gender.MALE);

                LinearLayout.LayoutParams paramsForMaleButton = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        WEIGHT_FOR_SEX_SELECTED
                );
                maleButton.setLayoutParams(paramsForMaleButton);

                LinearLayout.LayoutParams paramsForFemaleButton = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                );
                femaleButton.setLayoutParams(paramsForFemaleButton);

                // increase the size text of the pressed button and decrease the other one
                maleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, SCALAR_PIXEL_SIZE_FOR_PRESSED_BUTTON_SEX);
                femaleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, SCALAR_PIXEL_SIZE_FOR_UNPRESSED_BUTTON_SEX);

                // put a border on the selected button
                maleButton.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.male_button_with_border));
                femaleButton.setBackground(ContextCompat.getDrawable(getBaseContext(), R.color.femalePink));


            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User.setGender(Gender.FEMALE);

                LinearLayout.LayoutParams paramsForMaleButton = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                );
                maleButton.setLayoutParams(paramsForMaleButton);

                LinearLayout.LayoutParams paramsForFemaleButton = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        WEIGHT_FOR_SEX_SELECTED
                );
                femaleButton.setLayoutParams(paramsForFemaleButton);

                // increase the size text of the pressed button and decrease the other one
                femaleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, SCALAR_PIXEL_SIZE_FOR_PRESSED_BUTTON_SEX);
                maleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, SCALAR_PIXEL_SIZE_FOR_UNPRESSED_BUTTON_SEX);

                // put a border on the selected button
                femaleButton.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.female_button_with_border));
                maleButton.setBackground(ContextCompat.getDrawable(getBaseContext(), R.color.manBlue));


            }
        });

        numberPicker = findViewById(R.id.weightPicker);
        numberPicker.setMaxValue(200);
        numberPicker.setMinValue(10);
        numberPicker.setValue(DEFAULT_WEIGHT);
        User.setWeight(DEFAULT_WEIGHT);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                // update the user weight
                User.setWeight(newValue);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.getGender() != null){
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_first_select_sex), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @NonNull
    public static GoogleSignInOptions getGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
    }

    private void signIn() {
        User.setIsSignedIn(true);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityIntent.launch(signInIntent);
        //startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public Context getContex(){
        return this.getContex();
    }

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==RESULT_CANCELED){
                    Log.i("IMPORTANT", "CANCELED Intent");
                        User.setIsSignedIn(false);
                    }else {
                        User.setIsSignedIn(true);
                    }
                }

            });

}