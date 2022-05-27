package it.units.alcoholestimator.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.units.alcoholestimator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDrinkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDrinkFragment extends Fragment {

    public static final String DRINK_TYPE_KEY = "DRINK_TYPE";
    public static final String ALCOHOL_CONTENT_KEY = "ALCOHOL_CONTENT";
    public static final String DRINK_SIZE_KEY = "DRINK_SIZE";

    // TODO: Rename and change types of parameters
    private String drinkType;
    private String alcoholContent;
    private String drinkSize;

    public AddDrinkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment AddDrinkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDrinkFragment newInstance(String param1, String param2, String param3) {
        AddDrinkFragment fragment = new AddDrinkFragment();
        Bundle args = new Bundle();
        args.putString(DRINK_TYPE_KEY, param1);
        args.putString(ALCOHOL_CONTENT_KEY, param2);
        args.putString(DRINK_SIZE_KEY, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            drinkType = getArguments().getString(DRINK_TYPE_KEY);
            alcoholContent = getArguments().getString(ALCOHOL_CONTENT_KEY);
            drinkSize = getArguments().getString(DRINK_SIZE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_drink, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("TEST data:", drinkType + " " + alcoholContent + " "+ drinkSize);
    }
}