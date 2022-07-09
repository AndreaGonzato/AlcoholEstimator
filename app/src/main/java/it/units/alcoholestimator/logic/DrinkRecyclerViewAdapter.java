package it.units.alcoholestimator.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.units.alcoholestimator.R;

public class DrinkRecyclerViewAdapter extends RecyclerView.Adapter<DrinkRecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Drink> recentDrinks;

    public DrinkRecyclerViewAdapter(Context context, List<Drink> recentDrinks) {
        this.context = context;
        this.recentDrinks = recentDrinks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout (give a look th the rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new DrinkRecyclerViewAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // assign values to the view created in the recycler_view_row layout file based on the position of the recycler view
        holder.descriptionTextView.setText(recentDrinks.get(position).getDescription());
        Date date = recentDrinks.get(position).getAssumption();
        Calendar calendar = TimeManagerStaticUtils.dateToCalendar(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String hourString = String.valueOf(hour);
        String minuteString = String.valueOf(minute);

        if (hourString.length() == 1){
            hourString = "0" + hourString;
        }
        if (minuteString.length() == 1){
            minuteString = "0" + minuteString;
        }
        String time = hourString + ":" + minuteString;
        holder.timeTextView.setText(time); // TODO show only the time and not the day (day month and year)
        holder.deleteButton.setText(R.string.delete_drink);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getItemCount() {
        // how many item there are
        return recentDrinks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing all the views from the recycler_view_row layout file
        // kinda like in the onCreate method

        TextView descriptionTextView;
        TextView timeTextView;
        Button deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            deleteButton = itemView.findViewById(R.id.deleteDrinkButton);



        }
    }
}
