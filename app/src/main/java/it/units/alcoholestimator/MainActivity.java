package it.units.alcoholestimator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

import it.units.alcoholestimator.logic.Gender;
import it.units.alcoholestimator.logic.User;

public class MainActivity extends AppCompatActivity {

    // TODO when this class is almost finish decide what to do with the fields. Can i make it local var?
    private NumberPicker numberPicker;
    private Button maleButton;
    private Button femaleButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

        maleButton = findViewById(R.id.male);
        saveButton = findViewById(R.id.saveButton);
        femaleButton = findViewById(R.id.female);

        maleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User.setGender(Gender.MALE);
            }
        });

        femaleButton.setOnClickListener(v -> User.setGender(Gender.FEMALE));

        numberPicker = (NumberPicker) findViewById(R.id.weightPicker);
        numberPicker.setMaxValue(200);
        numberPicker.setMinValue(10);
        numberPicker.setValue(70);
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

}