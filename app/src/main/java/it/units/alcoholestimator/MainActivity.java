package it.units.alcoholestimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

import it.units.alcoholestimator.logic.Gender;
import it.units.alcoholestimator.logic.User;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_WEIGHT = 70;
    // TODO when this class is almost finish decide what to do with the fields. Can i make it local var?
    private NumberPicker numberPicker;
    private Button maleButton;
    private Button femaleButton;
    private Button saveButton;

    // constants
    private static final float WEIGHT_FOR_SEX_SELECTED = 2.0f;
    private static final int SCALAR_PIXEL_SIZE_FOR_PRESSED_BUTTON_SEX = 25;
    private static final int SCALAR_PIXEL_SIZE_FOR_UNPRESSED_BUTTON_SEX = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

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

        numberPicker = (NumberPicker) findViewById(R.id.weightPicker);
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

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}