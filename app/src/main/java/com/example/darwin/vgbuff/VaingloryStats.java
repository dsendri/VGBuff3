package com.example.darwin.vgbuff;

import android.os.AsyncTask;
import android.util.Log;

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
import java.util.concurrent.ExecutionException;

/**
 * Created by darwin on 3/9/2017.
 */

public class VaingloryStats {

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
    public String playerResultRaw;

    // Stats
    public String level;
    public String totalWin;
    public String totalgames;
    public String lifeTimeGold;
    public String lossStreak;
    public String winStreak;
    public String totalXP;
    public String playedRank;


    // Method to get the summary of player stat
    public void getPlayerStats(){
        DownloadTask task = new DownloadTask();
        Log.i("url","https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/players/?filter[playerName]="+user);

        try {

            playerResultRaw = task.execute("https://api.dc01.gamelockerapp.com/shards/"+serverLoc+"/players/?filter[playerName]="+user).get();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }
    }

    // Method to read player summary
    public void playerSummary(){

        try {

            // Get JSON Object
            JSONObject playerStats = new JSONObject(playerResultRaw);

            // Get the first player from the array
            JSONArray playerArr = new JSONArray(playerStats.getString("data"));

            // Save the value to the variables
            level = playerArr.getJSONObject(0).getJSONObject("attributes").getJSONObject("stats").getString("level");
            totalWin = playerArr.getJSONObject(0).getJSONObject("attributes").getJSONObject("stats").getString("wins");
            totalgames = playerArr.getJSONObject(0).getJSONObject("attributes").getJSONObject("stats").getString("played");
            lifeTimeGold = playerArr.getJSONObject(0).getJSONObject("attributes").getJSONObject("stats").getString("lifetimeGold");
            lossStreak = playerArr.getJSONObject(0).getJSONObject("attributes").getJSONObject("stats").getString("lossStreak");
            winStreak = playerArr.getJSONObject(0).getJSONObject("attributes").getJSONObject("stats").getString("winStreak");
            totalXP = playerArr.getJSONObject(0).getJSONObject("attributes").getJSONObject("stats").getString("xp");
            playedRank = playerArr.getJSONObject(0).getJSONObject("attributes").getJSONObject("stats").getString("played_ranked");


            // Used to be a full json object, doesn't have an array
            //JSONObject playerStats = new JSONObject(playerResultRaw);
            //level = playerStats.getJSONObject("data").getJSONObject("attributes").getJSONObject("stats").getString("level");
            //totalWin = playerStats.getJSONObject("data").getJSONObject("attributes").getJSONObject("stats").getString("wins");
            //totalgames = playerStats.getJSONObject("data").getJSONObject("attributes").getJSONObject("stats").getString("played");
            //lifeTimeGold = playerStats.getJSONObject("data").getJSONObject("attributes").getJSONObject("stats").getString("lifetimeGold");
            //lossStreak = playerStats.getJSONObject("data").getJSONObject("attributes").getJSONObject("stats").getString("lossStreak");
            //winStreak = playerStats.getJSONObject("data").getJSONObject("attributes").getJSONObject("stats").getString("winStreak");
            //totalXP = playerStats.getJSONObject("data").getJSONObject("attributes").getJSONObject("stats").getString("xp");
            //playedRank = playerStats.getJSONObject("data").getJSONObject("attributes").getJSONObject("stats").getString("played_ranked");


        } catch (JSONException e) {

            e.printStackTrace();
            Log.i("Failed","fail");

        }

    }

    // Set userName
    public void setPlayer(String player){
        user = player;
    }

    // Set server location
    public void setServerLoc(String serverLocation){
        serverLoc = serverLocation;
    }

    // Background Thread
    // This Method downloads necessary page from the server
    // It reads url string,
    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {

            // Temp Result from API Call
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            // Try / Catch to get the exception and
            // Get data from the Http Get Request
            try {

                // Initialize url address and open the connection to the server
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                // Call Http Get Request
                urlConnection.setRequestMethod("GET");

                // Add Request Header
                urlConnection.setRequestProperty("Authorization",API_KEY);
                urlConnection.setRequestProperty("X-TITLE-ID",TITLE);
                urlConnection.setRequestProperty("Accept",ACCEPT);

                // Get response code from the request
                int responseCode = urlConnection.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                result = response.toString();

                bufferedReader.close();
                urlConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();
                return "Failed";

            } catch (IOException e) {

                e.printStackTrace();
                return "Failed";
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            /*
            Log.i("result",result);
            try {

                // get json array
                JSONObject playerStats = new JSONObject(result);
                Log.i("playerstat",playerStats.getString("data"));
                JSONArray playerArr = new JSONArray(playerStats.getString("data"));
                JSONObject playerData = playerArr.getJSONObject(0);
                Log.i("firstPlayer",playerData.toString());
                JSONObject playerAtt = playerData.getJSONObject("attributes");

                Log.i("attributes",playerAtt.toString());

                String name = playerAtt.getString("name");
                String level = playerAtt.getJSONObject("stats").getString("level");
                String totalWin = playerAtt.getJSONObject("stats").getString("wins");
                String totalgames = playerAtt.getJSONObject("stats").getString("played");

                Log.i("name",name);
                Log.i("level",level);
                Log.i("Wins",totalWin);
                Log.i("Played",totalgames);

            } catch (JSONException e) {

                e.printStackTrace();

            }

            //Log.i("Website Content",result);
            */

        }
    }




}