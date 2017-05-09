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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String tempRaw1;
    public String tempRaw2;
    public String tempRaw3;

    public VaingloryHeroAndMatches.DownloadTask2 task;
    public VaingloryHeroAndMatches.DownloadTask2 task2;
    public VaingloryHeroAndMatches.DownloadTask2 task3;

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
    Activity activity;

    public void setContext (Context context) { mcontext = context; }



    // Get raw data from the API
    public void getRawData(){

        task = new VaingloryHeroAndMatches.DownloadTask2();
        task2 = new VaingloryHeroAndMatches.DownloadTask2();
        task3 = new VaingloryHeroAndMatches.DownloadTask2();

        activity = (Activity) mcontext;

        // Get current date and date 30 days ago
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -30);
        System.out.println("Current time => " + c.getTime());

        // Set the date to appropirate format for the http request get call
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c.getTime());

        Log.i("url","https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/matches?sort=-createdAt&filter[createdAt-start]="+formattedDate+"T00:00:00Z&filter[playerNames]="+user);


        try {

            // Get history matches for the last 30 days
            //dataRaw = task.execute("https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/matches?sort=-createdAt&filter[createdAt-start]="+formattedDate+"T00:00:00Z&filter[playerNames]="+user).get();


            tempRaw1 = task.execute("https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/matches?page[limit]=50&sort=-createdAt&filter[createdAt-start]="+formattedDate+"T00:00:00Z&filter[playerNames]="+user).get();
            tempRaw2 = task2.execute("https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/matches?page[offset]=50&page[limit]=50&sort=-createdAt&filter[createdAt-start]="+formattedDate+"T00:00:00Z&filter[playerNames]="+user).get();
            tempRaw3 = task3.execute("https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/matches?page[offset]=100&page[limit]=50&sort=-createdAt&filter[createdAt-start]="+formattedDate+"T00:00:00Z&filter[playerNames]="+user).get();

                Log.i("TempRaw", tempRaw1);
                Pattern dataPattern = Pattern.compile("[{]\"data\":\\[(.*?)[}][}][}][]],");
                Matcher dataMatch = dataPattern.matcher(tempRaw1);

                Pattern includedPattern = Pattern.compile("\"included\":\\[(.*?)[}][}][}][]][}]");
                Matcher includedMatch = includedPattern.matcher(tempRaw1);

                Log.i("TempRaw", tempRaw2);
                Pattern dataPattern2 = Pattern.compile("[{]\"data\":\\[(.*?)[}][}][}][]],");
                Matcher dataMatch2 = dataPattern2.matcher(tempRaw2);

                Pattern includedPattern2 = Pattern.compile("\"included\":\\[(.*?)[}][}][}][]][}]");
                Matcher includedMatch2 = includedPattern2.matcher(tempRaw2);

                Log.i("TempRaw", tempRaw3);
                Pattern dataPattern3 = Pattern.compile("[{]\"data\":\\[(.*?)[}][}][}][]],");
                Matcher dataMatch3 = dataPattern3.matcher(tempRaw3);

                Pattern includedPattern3 = Pattern.compile("\"included\":\\[(.*?)[}][}][}][]][}]");
                Matcher includedMatch3 = includedPattern3.matcher(tempRaw3);

                includedMatch.find();
                dataMatch.find();
                includedMatch2.find();
                dataMatch2.find();
                includedMatch3.find();
                dataMatch3.find();




            Log.i("data",dataMatch.group(1));
            Log.i("included",includedMatch.group(1));
            //dataRaw = "{\"data\":["+dataMatch.group(1)+"}}}],\"included\":["+includedMatch.group(1)+"}}}]}";
            dataRaw = "{\"data\":["+dataMatch.group(1)+"}}},"+dataMatch2.group(1)+"}}},"+dataMatch3.group(1)+"}}}],\"included\":["+includedMatch.group(1)+"}}},"+includedMatch2.group(1)+"}}},"+includedMatch3.group(1)+"}}}]}";
            Log.i("RawMOD",dataRaw);
            //dataRaw = tempRaw1;


            Log.i("User",user);

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        } catch (Exception e) {

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
            //progDailog = new ProgressDialog(activity);
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
