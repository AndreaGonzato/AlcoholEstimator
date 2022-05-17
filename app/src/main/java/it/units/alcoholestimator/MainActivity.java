package it.units.alcoholestimator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import it.units.alcoholestimator.logic.Gender;
import it.units.alcoholestimator.logic.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button maleButton = findViewById(R.id.male);
        maleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User.setGender(Gender.MALE);
                Log.i("TEST", "is male");
            }
        });

        Button femaleButton = findViewById(R.id.female);
        femaleButton.setOnClickListener(v -> User.setGender(Gender.FEMALE));

    }

}