package it.units.alcoholestimator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

import it.units.alcoholestimator.fragments.AddDrinkFragment;
import it.units.alcoholestimator.fragments.DashboardFragment;
import it.units.alcoholestimator.fragments.SettingsFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // remove the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        Fragment dashboardFragment = new DashboardFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, dashboardFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.toString()){
                    case "add a drink":
                        Fragment addDrinkFragment = new AddDrinkFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, addDrinkFragment).commit();
                        break;
                    case "dashboard":
                        Fragment dashboardFragment = new DashboardFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, dashboardFragment).commit();
                        break;
                    case "settings":
                        Fragment settingsFragment = new SettingsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, settingsFragment).commit();
                        break;
                }
                return true;
            }
        });


    }
}