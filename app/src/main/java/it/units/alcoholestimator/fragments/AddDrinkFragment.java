package it.units.alcoholestimator.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import it.units.alcoholestimator.R;
import it.units.alcoholestimator.logic.DatabaseManager;
import it.units.alcoholestimator.logic.Month;

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
    private int year, month, day;
    private Button timeButton;
    private int hour, minute;

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

        timeButton = requireView().findViewById(R.id.timePickerButton);
        setCurrentTime();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true);

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        Button addDrinkButton = requireView().findViewById(R.id.addDrinkButton);
        addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update the input values
                drinkType = drinkDescriptionEditText.getText().toString();

                if (!alcoholContentEditText.getText().toString().equals("")){
                    alcoholContent = Integer.parseInt(alcoholContentEditText.getText().toString());
                }
                if (!drinkSizeEditText.getText().toString().equals("")){
                    drinkSize = Integer.parseInt(drinkSizeEditText.getText().toString());
                }

                Date date = new GregorianCalendar(year, month - 1, day, hour, minute).getTime();

                Log.i("TEST", "drinkType:" + drinkType + " alcoholContent:" + alcoholContent + " drinkSize:" + drinkSize + " date:" + date);

                if (drinkType == null || drinkSize == 0 || alcoholContent == 0){
                    Toast.makeText(getContext(), "Enter the drink details to add a drink", Toast.LENGTH_SHORT).show();
                }else {
                    // store drink in the database
                    DatabaseManager.addDrink(drinkType, alcoholContent, drinkSize, date);
                }

            }
        });

    }

    private void setCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d){
                year = y;
                month = m + 1;
                day = d;
                String date = makeDateString(day, month, year);
                Button button = requireView().findViewById(R.id.datePickerButton);
                button.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);

    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        month = month + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " +year;
    }

    private String getMonthFormat(int month) {
        Month monthEnum = Month.retrieveByMonthNumber(month);
        assert monthEnum != null;
        return monthEnum.representation;
    }

}