package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.covidtracker.databinding.ActivityCountryBinding;
import com.example.covidtracker.model.CountryData;

import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CountryActivity extends AppCompatActivity {

    ActivityCountryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCountryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");

        for(CountryData d: utils.list){
            if(d.getCountry().equals(message)){
                updateUI(d);
            }
        }

    }
    public void updateUI(CountryData data){



                binding.confirmCases.setText(data.getCases());
                binding.todayConfirmCases.setText("( +"+data.getTodayCases()+" )");
                binding.activeCases.setText(data.getActive());
                binding.recoveredCases.setText(data.getRecovered());
                binding.todayRecoveredCases.setText("( +"+data.getTodayRecovered()+" )");
                binding.deathCases.setText(data.getDeaths());
                binding.todayDeathCases.setText("( +"+data.getTodayDeaths()+" )");
                binding.testCases.setText(data.getTests());
                binding.countryNameTextView.setText(data.getCountry());
                //set date
                setDate(data.getUpdated());

                binding.piechart.addPieSlice(new PieModel("Confirm",Integer.parseInt(data.getCases()),
                        getResources().getColor(R.color.yellow)));
                binding.piechart.addPieSlice(new PieModel("Active",Integer.parseInt(data.getActive()),
                        getResources().getColor(R.color.blue)));
                binding.piechart.addPieSlice(new PieModel("Recovered",Integer.parseInt(data.getRecovered()),
                        getResources().getColor(R.color.green)));
                binding.piechart.addPieSlice(new PieModel("Death",Integer.parseInt(data.getDeaths()),
                        getResources().getColor(R.color.red)));




    }

    private void setDate(String updated) {
        DateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy");

        long millissecond = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millissecond);

        binding.updatedAtTextView.setText("Updated at "+format.format(calendar.getTime()));
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(CountryActivity.this,MainActivity.class));

    }
}