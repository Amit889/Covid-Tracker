package com.example.covidtracker;

import android.util.Log;

import com.example.covidtracker.model.CountryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class utils {

   public static String trackerapp = "TrackerApp";
   public static  ArrayList<CountryData>  list;
    public static ArrayList<CountryData> fetchEarthquakeData(String myurl){
        URL url=null;
        list = new ArrayList<>();
// this code is only to check weather Progressbar work or not
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            url= createURL(myurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String jsonString ="";

        try {
            jsonString=makeHttpRequest(url);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            ArrayList<CountryData> covidData = extractFeatureFromJason(jsonString);
            list = covidData;
            return covidData;
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }
    private static URL createURL(String str) throws MalformedURLException {
        URL url=null;
        try {
            url=new  URL(str);
        }catch (MalformedURLException  exception){
            Log.d(trackerapp,"Url not created Sucessfuly");
            return null;
        }

        return url;
    }
    private static  String makeHttpRequest(URL url) throws IOException {
        String jsonString = "";

        if(url==null){
            return jsonString;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setConnectTimeout(30000);

            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonString = readFromStream(inputStream);
            }else{
                Log.d(trackerapp,"Error response code "+httpURLConnection.getResponseCode());
            }

        }catch (IOException ioException){
            Log.e(trackerapp, "Problem retrieving the quakeResult JSON results.");
        }finally{
            if(httpURLConnection !=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }

        return jsonString;
    }

    private static  String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output =new StringBuilder();

        if(inputStream!=null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }


        return output.toString();
    }

    private static ArrayList<CountryData>  extractFeatureFromJason(String jsonString) throws JSONException {
        ArrayList<CountryData>  myal= new ArrayList<>();
        try {
            JSONArray object = new JSONArray(jsonString);
//            JSONArray feature = object.getJSONArray("features");
//            for (int i = 0; i < feature.length(); i++) {
//                JSONObject obj = feature.getJSONObject(i);
//                JSONObject properties = obj.getJSONObject("properties");
//                String mag = properties.getString("mag");
//                String place = properties.getString("place");
//                String date = properties.getString("time");
//               // myal.add(new CountryData(mag, place, date));
//            }
            for (int i = 0; i < object.length(); i++) {
                JSONObject obj = object.getJSONObject(i);
                String updated = obj.getString("updated");
                String country = obj.getString("country");
                String cases = obj.getString("cases");
                String todayCases = obj.getString("todayCases");
                String deaths = obj.getString("deaths");
                String todayDeaths = obj.getString("todayDeaths");
                String recovered = obj.getString("recovered");
                String todayRecovered = obj.getString("todayRecovered");
                String active = obj.getString("active");
                String tests = obj.getString("tests");
                String population = obj.getString("population");
                //getting image
                JSONObject imageobj= obj.getJSONObject("countryInfo");
                String image = imageobj.getString("flag");

                //Storing data in Arraylist
                CountryData mdata= new CountryData(updated,country,cases,todayCases,deaths,
                        todayDeaths,recovered,todayRecovered,active,tests,image,population);
                myal.add(mdata);
            }


            return myal;
        }catch (JSONException exception){
            Log.e(trackerapp, "Problem parsing the earthquake JSON results");
        }
        return null;
    }
}
