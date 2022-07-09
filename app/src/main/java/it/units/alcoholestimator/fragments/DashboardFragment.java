package it.units.alcoholestimator.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import it.units.alcoholestimator.R;
import it.units.alcoholestimator.database.FirebaseDatabaseManager;
import it.units.alcoholestimator.logic.AlcoholContentCalculator;
import it.units.alcoholestimator.logic.Drink;
import it.units.alcoholestimator.logic.DrinkRecyclerViewAdapter;
import it.units.alcoholestimator.logic.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        FirebaseDatabaseManager.fetchUserDrinks(this); // TODO is this line in the right method?

        // load the userDrink fragment
        Fragment userDrinksFragment = new UserDrinksFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.userDrinksFrameLayout, userDrinksFragment).commit();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateGUIAfterDownloadDataFromCloud(){
        TextView numberOfDrink = requireView().findViewById(R.id.numberOfDrinksTextView);
        numberOfDrink.setText(String.valueOf(User.getRecentDrinks().size()));

        float maxBloodAlcoholContent = AlcoholContentCalculator.calculateAlcoholContent(User.getGender(), User.getWeight(), User.getRecentDrinks());
        float minBloodAlcoholContent = maxBloodAlcoholContent / AlcoholContentCalculator.SCALING_FACTOR_WITHOUT_A_MEAL;
        TextView maxAlcoholContentTextView = requireView().findViewById(R.id.maxAlcoholContentTextView);
        maxAlcoholContentTextView.setText(String.format(Locale.getDefault(), "%.2f g/l", maxBloodAlcoholContent));

        TextView minAlcoholContentTextView = requireView().findViewById(R.id.minAlcoholContentTextView);
        minAlcoholContentTextView.setText(String.format(Locale.getDefault(), "%.2f g/l", minBloodAlcoholContent));

        RecyclerView recyclerView = requireView().findViewById(R.id.recycleView);

        for(int i=User.getRecentDrinks().size()-1 ; i >= 0; i--){
            Drink drink = User.getRecentDrinks().get(i);

        }

        DrinkRecyclerViewAdapter adapter = new DrinkRecyclerViewAdapter(getContext(), User.getRecentDrinks());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}