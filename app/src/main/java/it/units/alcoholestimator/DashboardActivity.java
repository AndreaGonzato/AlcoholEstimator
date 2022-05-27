package it.units.alcoholestimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import it.units.alcoholestimator.fragments.SelectDrinkFragment;
import it.units.alcoholestimator.fragments.DashboardFragment;
import it.units.alcoholestimator.fragments.SettingsFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // load the DashboardFragment as default fragment
        Fragment dashboardFragment = new DashboardFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, dashboardFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.toString()){
                case "add a drink":
                    Fragment selectDrinkFragment = new SelectDrinkFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, selectDrinkFragment).commit();
                    break;
                case "dashboard":
                    Fragment dashboardFragment1 = new DashboardFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, dashboardFragment1).commit();
                    break;
                case "settings":
                    Fragment settingsFragment = new SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, settingsFragment).commit();
                    break;
                default:
                    throw new IllegalArgumentException("No menuItem with such name: "+ menuItem.toString());
            }
            return true;
        });


    }
}