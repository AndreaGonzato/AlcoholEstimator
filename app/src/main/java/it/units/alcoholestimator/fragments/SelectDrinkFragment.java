package it.units.alcoholestimator.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;

import it.units.alcoholestimator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectDrinkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectDrinkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectDrinkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDrinkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectDrinkFragment newInstance(String param1, String param2) {
        SelectDrinkFragment fragment = new SelectDrinkFragment();
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
        return inflater.inflate(R.layout.fragment_select_drink, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button[] lightBeerButtons = new Button[3];
        lightBeerButtons[0] = requireView().findViewById(R.id.lightBeerButton);
        lightBeerButtons[1] = requireView().findViewById(R.id.lightBeerAlcoholButton);
        lightBeerButtons[2] = requireView().findViewById(R.id.lightBeerSizeButton);
        Arrays.stream(lightBeerButtons).forEach(button -> button.setOnClickListener(v -> replaceCurrentFragmentWithAddDrinkFragment(getString(R.string.light_beer), getString(R.string._4_percentage), getString(R.string._500ml))));

        Button[] regularBeerButtons = new Button[3];
        regularBeerButtons[0] = requireView().findViewById(R.id.regularBeerButton);
        regularBeerButtons[1] = requireView().findViewById(R.id.regularBeerAlcoholButton);
        regularBeerButtons[2] = requireView().findViewById(R.id.regularBeerSizeButton);
        Arrays.stream(regularBeerButtons).forEach(button -> button.setOnClickListener(v -> replaceCurrentFragmentWithAddDrinkFragment(getString(R.string.regular_beer), getString(R.string._5_percentage), getString(R.string._500ml))));

        Button[] strongBeerButtons = new Button[3];
        strongBeerButtons[0] = requireView().findViewById(R.id.strongBeerButton);
        strongBeerButtons[1] = requireView().findViewById(R.id.strongBeerAlcoholButton);
        strongBeerButtons[2] = requireView().findViewById(R.id.strongBeerSizeButton);
        Arrays.stream(strongBeerButtons).forEach(button -> button.setOnClickListener(v -> replaceCurrentFragmentWithAddDrinkFragment(getString(R.string.strong_beer), getString(R.string._8_percentage), getString(R.string._500ml))));

        Button[] sprizButtons = new Button[3];
        sprizButtons[0] = requireView().findViewById(R.id.sprizButton);
        sprizButtons[1] = requireView().findViewById(R.id.sprizAlcoholButton);
        sprizButtons[2] = requireView().findViewById(R.id.sprizSizeButton);
        Arrays.stream(sprizButtons).forEach(button -> button.setOnClickListener(v -> replaceCurrentFragmentWithAddDrinkFragment(getString(R.string.spriz), getString(R.string._9_percentage), getString(R.string._300ml))));

        Button[] wineButtons = new Button[3];
        wineButtons[0] = requireView().findViewById(R.id.wineButton);
        wineButtons[1] = requireView().findViewById(R.id.wineAlcoholButton);
        wineButtons[2] = requireView().findViewById(R.id.wineSizeButton);
        Arrays.stream(wineButtons).forEach(button -> button.setOnClickListener(v -> replaceCurrentFragmentWithAddDrinkFragment(getString(R.string.wine), getString(R.string._12_percentage), getString(R.string._200ml))));

        Button[] generalCocktailButtons = new Button[3];
        generalCocktailButtons[0] = requireView().findViewById(R.id.generalCocktailButton);
        generalCocktailButtons[1] = requireView().findViewById(R.id.generalCocktailAlcoholButton);
        generalCocktailButtons[2] = requireView().findViewById(R.id.generalCocktailSizeButton);
        Arrays.stream(generalCocktailButtons).forEach(button -> button.setOnClickListener(v -> replaceCurrentFragmentWithAddDrinkFragment(getString(R.string.generic_cocktail), getString(R.string._13_percentage), getString(R.string._150ml))));

        Button[] shotButtons = new Button[3];
        shotButtons[0] = requireView().findViewById(R.id.shotButton);
        shotButtons[1] = requireView().findViewById(R.id.shotAlcoholButton);
        shotButtons[2] = requireView().findViewById(R.id.shotSizeButton);
        Arrays.stream(shotButtons).forEach(button -> button.setOnClickListener(v -> replaceCurrentFragmentWithAddDrinkFragment(getString(R.string.shot), getString(R.string._40_percentage), getString(R.string._40ml))));

        Button customDrinkButton = requireView().findViewById(R.id.customDrinkButton);
        customDrinkButton.setOnClickListener(v -> {
                    Fragment addDrinkFragment = new AddDrinkFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(AddDrinkFragment.DRINK_TYPE_KEY, getString(R.string.custom_drink));
                    bundle.putString(AddDrinkFragment.ALCOHOL_CONTENT_KEY, "3%"); // TODO do not pass a value
                    bundle.putString(AddDrinkFragment.DRINK_SIZE_KEY, "50 ml"); // TODO do not pass a value
                    addDrinkFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainFragment, addDrinkFragment)
                            .setReorderingAllowed(true)
                            .addToBackStack(null);
                    fragmentTransaction.commit();
                }
                );
    }


    private void replaceCurrentFragmentWithAddDrinkFragment(String drinkType, String alcoholContent, String drinkSize) {
        Fragment addDrinkFragment = new AddDrinkFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AddDrinkFragment.DRINK_TYPE_KEY, drinkType);
        bundle.putString(AddDrinkFragment.ALCOHOL_CONTENT_KEY, alcoholContent);
        bundle.putString(AddDrinkFragment.DRINK_SIZE_KEY, drinkSize);
        addDrinkFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, addDrinkFragment)
                            .setReorderingAllowed(true)
                            .addToBackStack(null);
        fragmentTransaction.commit();
    }
}