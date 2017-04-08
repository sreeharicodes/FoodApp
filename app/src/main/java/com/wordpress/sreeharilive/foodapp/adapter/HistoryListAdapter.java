package com.wordpress.sreeharilive.foodapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.model.HistoryItem;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<HistoryItem> items;

    public HistoryListAdapter(Context context, ArrayList<HistoryItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HistoryListAdapter.ViewHolder holder, int position) {

        holder.totalTV.setText("Total Amount= ₹" + String.valueOf(items.get(position).getTotal()) + "/-");
        holder.modeTV.setText(items.get(position).getMode());

        long orderTimeInMillis = items.get(position).getOrderTimeStamp();
        long deliveryTimeInMillis = items.get(position).getDeliveryTimeStamp();

        holder.orderTimeTV.setText(getTimeAsString(orderTimeInMillis));
        holder.deliveryTimeTV.setText(getTimeAsString(deliveryTimeInMillis));

        holder.dateTV.setText(getDateStringFrom(orderTimeInMillis));

    }

    private String getDateStringFrom(long timeInMillis) {


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String daySuffix;

        String dayStr = String.valueOf(day);

        String months[] = {
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };

        String monthStr = months[month];

        if (dayStr.endsWith("1")){
            if (dayStr.endsWith("11"))
                daySuffix = "th";
            else
                daySuffix = "st";
        }else if (dayStr.endsWith("2")){
            if (dayStr.endsWith("12"))
                daySuffix = "th";
            else
                daySuffix = "nd";
        }else if (dayStr.endsWith("3")){
            if (dayStr.endsWith("13"))
                daySuffix = "th";
            else
                daySuffix = "rd";
        }else {
            daySuffix = "th";
        }

        if (dayStr.length() < 2){
            dayStr = "0".concat(dayStr);
        }

        return dayStr + daySuffix + " " + monthStr + ", " + year;

    }

    private String getTimeAsString(long timeInMillis) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String period, hourString, minuteString;

        if (hours == 0){
            hours = 12;
            period = "AM";
        }else if (hours > 12){
            hours = hours - 12;
            period = "PM";
        }else {
            period = "PM";
        }

        if (String.valueOf(hours).length()<2){
            hourString = "0" + hours;
        }else {
            hourString = String.valueOf(hours);
        }


        if (String.valueOf(minutes).length()<2){
            minuteString = "0" + minutes;
        }else {
            minuteString = String.valueOf(minutes);
        }

        return hourString + ":" + minuteString + " " + period;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTV, orderTimeTV, deliveryTimeTV, totalTV, modeTV;
        View rootView;
        ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            dateTV = (TextView) itemView.findViewById(R.id.orderDate);
            orderTimeTV = (TextView) itemView.findViewById(R.id.timeOfOrder);
            deliveryTimeTV = (TextView) itemView.findViewById(R.id.timeOfDelivery);
            totalTV = (TextView) itemView.findViewById(R.id.totalAmount);
            modeTV = (TextView) itemView.findViewById(R.id.modeOfPayment);
        }
    }
}
