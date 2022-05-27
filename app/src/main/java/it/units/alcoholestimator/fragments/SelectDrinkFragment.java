package it.units.alcoholestimator.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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
        Arrays.stream(lightBeerButtons).forEach(button -> button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addDrinkFragment = new AddDrinkFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AddDrinkFragment.DRINK_TYPE_KEY, "light Beer");
                bundle.putString(AddDrinkFragment.ALCOHOL_CONTENT_KEY, "4%");
                bundle.putString(AddDrinkFragment.DRINK_SIZE_KEY, "500 ml");
                addDrinkFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainFragment, addDrinkFragment)
                                    .setReorderingAllowed(true)
                                    .addToBackStack(null);
                fragmentTransaction.commit();
            }
        }));

    }
}