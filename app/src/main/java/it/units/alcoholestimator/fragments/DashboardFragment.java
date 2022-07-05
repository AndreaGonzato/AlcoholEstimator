package it.units.alcoholestimator.fragments;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.units.alcoholestimator.R;
import it.units.alcoholestimator.logic.DatabaseHelper;
import it.units.alcoholestimator.logic.DatabaseManager;
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

        Button fetchDrinksButton = requireView().findViewById(R.id.fetchDrinksButton);
        fetchDrinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = DatabaseHelper.getAllData();
                if(cursor.getCount() == 0){
                    // show message
                    showMessage("Error", "No data found");
                    return;
                }else {
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext()){
                        buffer.append("ID: "+ cursor.getString(0)+ "\n");
                        buffer.append("CLOUD_ID: "+ cursor.getString(1)+ "\n");
                        buffer.append("EMAIL: "+ cursor.getString(2)+ "\n");
                        buffer.append("GENDER: "+ cursor.getString(3)+ "\n");
                        buffer.append("WEIGHT: "+ cursor.getString(4)+ "\n");
                        buffer.append("IS_SIGNED_IN: "+ cursor.getString(5)+ "\n\n");
                    }

                    // show all data
                    showMessage("Data", buffer.toString());
                    DatabaseHelper.emptyUserTable(); // TODO remove this line
                }
                //DatabaseManager.fetchUserDrinks(); // TODO remove this comment
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}