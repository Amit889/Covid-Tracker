package com.example.covidtracker;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.covidtracker.model.CountryData;

import java.util.List;

public class CovidDataLoader extends AsyncTaskLoader<List<CountryData>> {

    public String trackerapp="TrackerApp";
    private String mURL;
    public CovidDataLoader(Context context, String url){
        super(context);
        Log.d(trackerapp,"hi i  am in EarthQuakeLoader Method of EarthQuakeLoader");
        mURL=url;
    }

    @Override
    protected void onStartLoading() {
        Log.d(trackerapp,"hi i  am in onStartLoading Method of EarthQuakeLoader");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Nullable
    @Override
    public List<CountryData> loadInBackground() {
        Log.d(trackerapp,"hi i  am in LoadInBackGround Method of EarthQuakeLoader");
        if(mURL==null){
            return null;
        }
        List<CountryData>  mylist= utils.fetchEarthquakeData(mURL);
        return mylist;
    }
}
