package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.covidtracker.Adapter.CountryAdapter;
import com.example.covidtracker.databinding.ActivityMainBinding;
import com.example.covidtracker.model.CountryData;

import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<CountryData>> {

    /*
    Covid Api : https://corona.lmao.ninja/v2/countries
Retrofit Website : https://square.github.io/retrofit/
Retrofit Library : https://github.com/square/retrofit
GSON Converter : https://github.com/square/retrofit/tr...
JSON Formatter : https://jsonformatter.org/
Pie Chart : https://github.com/blackfizz/EazeGraph
Google Fonts : https://fonts.google.com/
     */
    ActivityMainBinding binding;
    private ProgressDialog progressDialog;
    private ArrayList<CountryData> al;
    public static final  String myurl="https://corona.lmao.ninja/v2/countries";
    private static final int COVID_LOADER_ID = 1;
    private CountryAdapter adapter;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        al = new ArrayList<>();


        //initialising progress dialogue
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Loading covid data");
        progressDialog.show();


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.

            binding.emptyView.setVisibility(View.GONE);
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(COVID_LOADER_ID, null, this);
        }else{

            binding.recyclerView.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
            //dismis my progress dialog
            progressDialog.dismiss();
        }
    }

    @Override
    public Loader<List<CountryData>> onCreateLoader(int id, Bundle args) {

        // Create a new loader for the given URL
        return new CovidDataLoader(this,myurl);
    }

    @Override
    public void onLoadFinished(Loader<List<CountryData>> loader, List<CountryData> data) {


        progressDialog.dismiss();

        if(al !=null){
            al.clear();
        }

        if(data !=null&& !data.isEmpty()){
            al = (ArrayList<CountryData>)data;

        }

        adapter = new CountryAdapter(al,this);
        binding.recyclerView.setAdapter(adapter);

         layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onLoaderReset(Loader<List<CountryData>> loader) {
        //Log.d(QUAKE_APP,"hi i am in onLoadReset Method");
        // Loader reset, so we can clear out our existing data.
        //earthQuakeAdapter.clear();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }
}