package com.example.covidtracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidtracker.CountryActivity;
import com.example.covidtracker.R;
import com.example.covidtracker.model.CountryData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.viewHolder>  {

    /*
   declearing ArrayList of RecipeModel data type containing Image and text
   */
    ArrayList<CountryData> list;
    Context context;

    /* constructor taking  ArrayList and context as Atribute*/
    public CountryAdapter(ArrayList<CountryData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override

    /* in this method we simply inflate the layout which we made*/
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* here we pass parent to adjust the size to image to match parent in
        future if required*/
        View view= LayoutInflater.from(context).inflate(R.layout.country_list_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    /* in this function we set the value of image and text */
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CountryData model= list.get(position);

        holder.number.setText((position+1)+".");
        holder.country.setText(model.getCountry());
        holder.population.setText(model.getPopulation());

        Picasso.get().load(model.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CountryActivity.class);
                intent.putExtra("message", model.getCountry());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView number, country, population;
        ImageView image;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.numberTextView);
            country = itemView.findViewById(R.id.countryNameTextView);
            population = itemView.findViewById(R.id.countryPopulationTextView);
            image = itemView.findViewById(R.id.flagImageView);

        }
    }
}
