package it.units.alcoholestimator.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Objects;

import it.units.alcoholestimator.R;
import it.units.alcoholestimator.database.FirebaseDatabaseManager;
import it.units.alcoholestimator.database.LocalDatabaseHelper;
import it.units.alcoholestimator.logic.Gender;
import it.units.alcoholestimator.logic.User;

public class UserDataSettingActivity extends AppCompatActivity {

    public static final int DEFAULT_WEIGHT = 70;
    private Button maleButton;
    private Button femaleButton;

    // constants
    private static final float WEIGHT_FOR_SEX_SELECTED = 2.0f;
    private static final int SCALAR_PIXEL_SIZE_FOR_PRESSED_BUTTON_SEX = 25;
    private static final int SCALAR_PIXEL_SIZE_FOR_UNPRESSED_BUTTON_SEX = 14;

    @Override
    protected void onStart() {
        super.onStart();

        if (User.getCloudID() == null) {
            // first time that the user is doing the log in
            Log.i("TEST", "welcome for the first time");
        } else {
            // user has already log in
            Log.i("TEST", "welcome again");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        boolean letUserEditInfo = getIntent().getBooleanExtra("letUserEditInfo", false);
        boolean newUser = true;

        LocalDatabaseHelper localDB = new LocalDatabaseHelper(this);
        try {
            // when start the app need load the user data from the local database
            User.loadUserFromLocalDatabase();
            if ( !letUserEditInfo){
                startActivity(new Intent(UserDataSettingActivity.this, DashboardActivity.class));
            }
            newUser = User.getCloudID() == null;



        }catch (SQLException e){
            // there are no data to load for the user in the local database
            Log.i("TEST", "no data for the user in the local database");
        }

        // remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        maleButton = findViewById(R.id.male);
        Button saveButton = findViewById(R.id.saveButton);
        femaleButton = findViewById(R.id.female);

        maleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User.setGender(Gender.MALE);

                maleButton.setActivated(true);
                femaleButton.setActivated(false);

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

                femaleButton.setActivated(true);
                maleButton.setActivated(false);
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

        NumberPicker numberPicker = findViewById(R.id.weightPicker);
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


        boolean finalNewUser = newUser;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedGender()){
                        if (finalNewUser){
                            FirebaseDatabaseManager.addUser(User.getEmail(), User.getGender(), User.getWeight());

                        }else{
                            // user is already inserted, so need to update it
                            FirebaseDatabaseManager.updateUser(User.getCloudID(), User.getEmail(), User.getGender(), User.getWeight());
                        }

                    startActivity(new Intent(UserDataSettingActivity.this, DashboardActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_first_select_sex), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    boolean selectedGender(){
        return femaleButton.isActivated() || maleButton.isActivated();
    }





}