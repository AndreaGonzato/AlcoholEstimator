package it.units.alcoholestimator.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

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

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    // TODO: Rename and change types of parameters
    private String drinkType;
    private String alcoholContentString;
    private int alcoholContent;
    private String drinkSizeString;
    private int drinkSize;

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
            alcoholContentString = getArguments().getString(ALCOHOL_CONTENT_KEY).replace("%", "").trim();
            alcoholContent = Integer.parseInt(alcoholContentString);
            drinkSizeString = getArguments().getString(DRINK_SIZE_KEY).replace(" ml", "").trim();
            drinkSize = Integer.parseInt(drinkSizeString);
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

        EditText drinkDescriptionEditText = requireView().findViewById(R.id.drinkDescriptionEditText);
        drinkDescriptionEditText.setText(drinkType);

        EditText drinkSizeEditText = requireView().findViewById(R.id.editTextNumberSize);
        drinkSizeEditText.setText(drinkSizeString);

        EditText alcoholContentEditText = requireView().findViewById(R.id.editTextNumberAlcoholContent);
        alcoholContentEditText.setText(alcoholContentString);

        initDatePicker();
        Button dateButton = requireView().findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        

    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                Button button = requireView().findViewById(R.id.datePickerButton);
                button.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " +year;
    }

    private String getMonthFormat(int month) {
        // TODO think if this is the best way to do that
        switch (month){
            case 1: return "JAN";
            case 2: return "FEB";
            case 3: return "MAR";
            case 4: return "APR";
            case 5: return "MAY";
            case 6: return "JUN";
            case 7: return "JUL";
            case 8: return "AUG";
            case 9: return "SET";
            case 10: return "OCT";
            case 11: return "NOV";
            case 12: return "DEC";
        }
        return "JAN";
    }

}