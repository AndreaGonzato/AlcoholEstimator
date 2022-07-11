package it.units.alcoholestimator.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import it.units.alcoholestimator.R;
import it.units.alcoholestimator.database.FirebaseDatabaseManager;
import it.units.alcoholestimator.logic.AlcoholContentCalculator;
import it.units.alcoholestimator.logic.Drink;
import it.units.alcoholestimator.logic.DrinkRecyclerViewAdapter;
import it.units.alcoholestimator.logic.User;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    public static int interval = 0;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // download all the drinks of the last 24 of the user from cloud
        FirebaseDatabaseManager.fetchUserDrinks(this);

        // load the userDrink fragment
        Fragment userDrinksFragment = new UserDrinksFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.userDrinksFrameLayout, userDrinksFragment).commit();

        Button previousIntervalButton = requireView().findViewById(R.id.previousIntervalButton);
        previousIntervalButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                DashboardFragment.interval = Math.max(0, DashboardFragment.interval - 1);
                updateGUIAfterDownloadDataFromCloud();
            }
        });
        Button nextIntervalButton = requireView().findViewById(R.id.nextIntervalButton);
        nextIntervalButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                DashboardFragment.interval += 1;
                if (DashboardFragment.interval * 5 >= User.getDrinks().size()) {
                    DashboardFragment.interval -= 1;
                }
                updateGUIAfterDownloadDataFromCloud();
            }
        });


    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateGUIAfterDownloadDataFromCloud() {
        TextView numberOfDrink = requireView().findViewById(R.id.numberOfDrinksTextView);
        int recentDrinksQuantity = User.getRecentDrinks().size();
        numberOfDrink.setText(String.valueOf(recentDrinksQuantity));

        float maxBloodAlcoholContent = AlcoholContentCalculator.calculateAlcoholContent(User.getGender(), User.getWeight(), User.getRecentDrinks());
        float minBloodAlcoholContent = maxBloodAlcoholContent / AlcoholContentCalculator.SCALING_FACTOR_WITHOUT_A_MEAL;
        TextView maxAlcoholContentTextView = requireView().findViewById(R.id.maxAlcoholContentTextView);
        maxAlcoholContentTextView.setText(String.format(Locale.getDefault(), "%.2f g/l", maxBloodAlcoholContent));

        TextView minAlcoholContentTextView = requireView().findViewById(R.id.minAlcoholContentTextView);
        minAlcoholContentTextView.setText(String.format(Locale.getDefault(), "%.2f g/l", minBloodAlcoholContent));

        if (recentDrinksQuantity == 0) {
            // no drink for the user to show
            TextView lastDrinkTextView = requireView().findViewById(R.id.userLastDrinksTextView);
            lastDrinkTextView.setVisibility(View.INVISIBLE);

            Button previousIntervalButton = requireView().findViewById(R.id.previousIntervalButton);
            previousIntervalButton.setVisibility(View.INVISIBLE);

            Button nextIntervalButton = requireView().findViewById(R.id.nextIntervalButton);
            nextIntervalButton.setVisibility(View.INVISIBLE);

            TextView intervalTextView = requireView().findViewById(R.id.currentIntervalTextView);
            intervalTextView.setVisibility(View.INVISIBLE);
        } else {
            RecyclerView recyclerView = requireView().findViewById(R.id.recycleView);

            List<Drink> reversedRecentDrinks = new ArrayList<>(User.getRecentDrinks());
            Collections.reverse(reversedRecentDrinks);

            List<Drink> drinksToDisplay = new ArrayList<>();

            int startingValueInterval = 5 * interval + 1;
            int endValueInterval = 1;
            int count = 0;
            for (int i = 5 * interval; i < reversedRecentDrinks.size() && count < 5; i++) {
                drinksToDisplay.add(reversedRecentDrinks.get(i));
                count++;
                endValueInterval = i + 1;
            }

            DrinkRecyclerViewAdapter adapter = new DrinkRecyclerViewAdapter(getContext(), drinksToDisplay, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            TextView intervalTextView = requireView().findViewById(R.id.currentIntervalTextView);

            intervalTextView.setText("[" + startingValueInterval + ", " + endValueInterval + "]");

        }


    }

}