package com.example.darwin.vgbuff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by darwin on 3/30/2017.
 */
public class VaingloryHeroAndMatches {

    // Constants

    // API KEY Const
    public static final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3MmQ1MDZkMC1kZjM1LTAxMzQtY2FkNi0wMjQyYWMxMTAwMDYiLCJpc3MiOiJnYW1lbG9ja2VyIiwib3JnIjoiZHNlbmRyaS1nbWFpbC1jb20iLCJhcHAiOiI3MmQzYWQ0MC1kZjM1LTAxMzQtY2FkNS0wMjQyYWMxMTAwMDYiLCJwdWIiOiJzZW1jIiwidGl0bGUiOiJ2YWluZ2xvcnkiLCJzY29wZSI6ImNvbW11bml0eSIsImxpbWl0IjoxMH0.BtkacZ7L05dRMSk9MEONh_ZLUig9sZMGMlVjTUiweF8";
    public static final String TITLE = "semc-vainglory";
    public static final String ACCEPT = "application/vnd.api+json";

    // Variables
    // User
    public String user;

    // Loc
    public String serverLoc;

    // Raw Data from player API Call
    public String dataRaw;

    public VaingloryHeroAndMatches.DownloadTask2 task;

    // Set userName
    public void setPlayer(String player) {
        user = player;
    }

    // Set server location
    public void setServerLoc(String serverLocation) {
        serverLoc = serverLocation;
    }

    public Player playerToBeSearched;

    // Match Data
    public Match matches;

    // Loading Animation
    ProgressDialog progDailog;
    Context mcontext;

    public void setContext (Context context) { mcontext = context; }



    // Get raw data from the API
    public void getRawData(){

        task = new VaingloryHeroAndMatches.DownloadTask2();

        // Get current date and date 30 days ago
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -30);
        System.out.println("Current time => " + c.getTime());

        // Set the date to appropirate format for the http request get call
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        Log.i("url","https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/matches?sort=-createdAt&filter[createdAt-start]="+formattedDate+"T00:00:00Z&filter[playerNames]="+user);


        try {

            // Get history matches for the last 30 days
            dataRaw = task.execute("https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/matches?sort=-createdAt&filter[createdAt-start]="+formattedDate+"T00:00:00Z&filter[playerNames]="+user).get();



            // Test Data
            //dataRaw = task.execute("https://api.dc01.gamelockerapp.com/shards/sg/matches/6210fb0a-1359-11e7-9722-02ff607182ab").get();

            Log.i("User",user);

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }

    }

    // Method to get player history for the last 30 days
    public void getPlayerStats(){

            //Log.i("User",user);

            // Intialize new player
            playerToBeSearched = new Player();

            // Search player data from the json data
            playerToBeSearched.searchPlayerFromJSON(user,dataRaw);

            if (playerToBeSearched.playerStats != null) playerToBeSearched.getNumberOfHeroPlayed();
    }

    // Method to get matches history for the last 30 days
    public void getMatchesHistory(){

        // Initialize match history
        matches = new Match();
        //Log.i("BEFORE MATCH",user);
        matches.getMatchesDetail(user,dataRaw);


    }

    // Background Thread
    // This Method downloads necessary page from the server
    // It reads url string,
    public class DownloadTask2 extends AsyncTask<String, Void, String> {

        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progDailog = new ProgressDialog(mcontext);
            //progDailog.setMessage("Loading...");
            //progDailog.setIndeterminate(false);
            //progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //progDailog.setCancelable(true);
            //progDailog.show();
            Log.i("On pre execute","On pre execute");
        }

        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);
            //progDailog.dismiss();
            Log.i("On ppost execute","On post execute");
        }

        @Override
        protected String doInBackground(String... urls) {

            // Temp Result from API Call

            URL url;
            HttpURLConnection urlConnection = null;


            // Try / Catch to get the exception and
            // Get data from the Http Get Request
            try {

                // Initialize url address and open the connection to the server
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                //urlConnection.setReadTimeout(10000 /* milliseconds */);
                //
                // urlConnection.setConnectTimeout(15000 /* milliseconds */);

                // Call Http Get Request
                urlConnection.setRequestMethod("GET");

                // Add Request Header
                urlConnection.setRequestProperty("Authorization", API_KEY);
                urlConnection.setRequestProperty("X-TITLE-ID", TITLE);
                urlConnection.setRequestProperty("Accept", ACCEPT);

                // Get response code from the request
                int responseCode = urlConnection.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String inputLine;
                StringBuilder  response = new StringBuilder ();

                while ((inputLine = bufferedReader.readLine()) != null) {
                        response.append(inputLine);
                }

                result = response.toString();

                bufferedReader.close();
                urlConnection.disconnect();

                //Log.i("result", result);
                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();
                return "Failed";

            } catch (IOException e) {

                e.printStackTrace();
                return "Failed";
            }
        }
    }
}
