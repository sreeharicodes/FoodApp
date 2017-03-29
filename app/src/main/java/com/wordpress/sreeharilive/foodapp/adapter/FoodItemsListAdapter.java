package com.wordpress.sreeharilive.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wordpress.sreeharilive.foodapp.R;
import com.wordpress.sreeharilive.foodapp.activity.FoodDescriptionActivity;
import com.wordpress.sreeharilive.foodapp.model.FoodItem;
import com.wordpress.sreeharilive.foodapp.util.Constants;

import java.util.ArrayList;

/***
 * Created by negibabu on 3/18/17.
 */

public class FoodItemsListAdapter extends RecyclerView.Adapter<FoodItemsListAdapter.ViewHolder>{

    private Context context;

    private ArrayList<FoodItem> foodItems;

    public FoodItemsListAdapter(Context context, ArrayList<FoodItem> foodItems) {
        this.context = context;
        this.foodItems = foodItems;
    }

    @Override
    public FoodItemsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.food_items_list_item,parent,false);
        return new FoodItemsListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final FoodItemsListAdapter.ViewHolder holder, int position) {


        holder.nameTextView.setText(
                foodItems.get(position).getName()
        );

        holder.priceTextView.setText(
                String.valueOf(foodItems.get(position).getPrice())
        );


        if (foodItems.get(holder.getAdapterPosition()).getCount()>0){
            holder.outOfStockOrNotTextView.setText(R.string.available);

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FoodDescriptionActivity.class);
                    intent.putExtra(Constants.SELECTED_ITEM_KEY,foodItems.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }else {
            holder.outOfStockOrNotTextView.setText(R.string.out_of_stock);
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Out of stock!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Picasso.with(context)
                .load(foodItems.get(position).getImageUrl())
                .placeholder(ContextCompat.getDrawable(context,R.drawable.ic_hourglass_full_black_24dp))
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTextView, priceTextView, outOfStockOrNotTextView;
        View rootView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.foodImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.itemNameTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
            outOfStockOrNotTextView = (TextView) itemView.findViewById(R.id.inStockOrNotTextView);
            rootView = itemView;

        }
    }
}
