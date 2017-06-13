package com.example.darwin.vgbuff;

import android.os.AsyncTask;
import android.os.UserHandle;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

/**
 * Created by darwin on 6/11/2017.
 */

public class Telemetry {




    // Class for dealing damage status
    class DealDamage {

        //{ "time": "2017-06-13T12:47:58+0000", "type": "DealDamage", "payload": { "Team": "Left", "Actor": "*Joule*", "Target": "*Krul*", "Source": "Ability__Joule__A", "Damage": 295, "Delt":  162, "IsHero": 1, "TargetIsHero": 1 } },

        Date time;
        String eventType;
        String team;
        String actor;
        String target;
        int damageDealt;
        int isHero;
        int targetIsHero;

        void formatTime (String rawTime) {

            Date[] datePlayed;
            DateFormat pf;

            // Set the date
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);

            try {
                time = df.parse(rawTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

    class UserInfo {

        //{ "time": "2017-06-13T12:35:08+0000", "type": "HeroSelect", "payload": { "Hero": "*Ardan*", "Team": "2", "Player": "aa701f3e-7f39-11e6-9681-06eb725f8a76", "Handle": "VATANA2" } }
        Date time;
        String eventType;
        String team;
        String actor;
        String user;
        String id;

        void setTeam(int teamInt){

            if (teamInt == 1) team = "Left";
            else if (teamInt == 2) team = "Right";
        }

        void formatTime (String rawTime) {

            Date[] datePlayed;
            DateFormat pf;

            // Set the date
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);

            try {
                time = df.parse(rawTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

    //{ "time": "2017-06-13T12:48:10+0000", "type": "KillActor", "payload": { "Team": "Left", "Actor": "*Adagio*", "Killed": "*VainTurret*", "KilledTeam": "Right", "Gold": "100", "IsHero": 1, "TargetIsHero": 0, "Position": [ 75.48, 0.00, 11.96 ] } },

    class KillActor {

        Date time;
        String eventType;
        String team;
        String actor;
        String target;
        int isHero;
        int targetIsHero;

        void formatTime (String rawTime) {

            Date[] datePlayed;
            DateFormat pf;

            // Set the date
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);

            try {
                time = df.parse(rawTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

    // Raw Data
    public String rawTelemetryData;
    public Telemetry.DownloadTask2 task;

    // Length of events
    int length;

    // damageDealt Event
    public ArrayList<DealDamage> damageDealtArray;
    // UserInfo
    public ArrayList<UserInfo> userInfoArray;
    // KilledEvent
    public ArrayList<KillActor> killedEvents;

    public void getRawTelemetryData(String url) {

        // Initialize background task
        task = new Telemetry.DownloadTask2();

        try {

            rawTelemetryData = task.execute(url).get();
            Log.i("Raw Tele",rawTelemetryData);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void formatRawDataToArrayEvent (){

        // Initialize event arrays
        damageDealtArray = new ArrayList<DealDamage>();
        userInfoArray = new ArrayList<UserInfo>();
        killedEvents = new ArrayList<KillActor>();

        try {

            // Initiaze raw data to JSONArray
            JSONArray eventTelemetry = new JSONArray(rawTelemetryData);

            length = eventTelemetry.length();

            // Convert appropriate event to its class


            for (int i = 0; i < length; i++){

                //{ "time": "2017-06-13T12:47:58+0000", "type": "DealDamage", "payload": { "Team": "Left", "Actor": "*Joule*", "Target": "*Krul*", "Source": "Ability__Joule__A", "Damage": 295, "Delt":  162, "IsHero": 1, "TargetIsHero": 1 } },

                // If type of event is "DealDamage"
                if (eventTelemetry.getJSONObject(i).getString("type").equals("DealDamage")){

                    //Log.i("type","DealDamage");
                    // set temporary object dealdamage
                    DealDamage temp = new DealDamage();

                    // save time of event
                    temp.formatTime(eventTelemetry.getJSONObject(i).getString("time"));
                    temp.eventType = eventTelemetry.getJSONObject(i).getString("type");

                    // get all info from the telemetry
                    JSONObject payload = eventTelemetry.getJSONObject(i).getJSONObject("payload");
                    temp.actor = payload.getString("Actor");
                    temp.team = payload.getString("Team");
                    temp.target = payload.getString("Target");
                    temp.damageDealt = payload.getInt("Delt");
                    temp.isHero = payload.getInt("IsHero");
                    temp.targetIsHero = payload.getInt("TargetIsHero");



                    // print log
                    DateFormat pf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Log.i("time",pf.format(temp.time));
                    Log.i("Team",temp.team);
                    Log.i("Actor",temp.actor);
                    Log.i("Damage dealt",String.valueOf(temp.damageDealt));

                    // add to array
                    damageDealtArray.add(temp);
                }

                // If type of event is "HeroSelect"
                if (eventTelemetry.getJSONObject(i).getString("type").equals("HeroSelect")){

                    //Log.i("type","DealDamage");
                    // set temporary object userInfo
                    UserInfo temp = new UserInfo();

                    // save time of event
                    temp.formatTime(eventTelemetry.getJSONObject(i).getString("time"));
                    temp.eventType = eventTelemetry.getJSONObject(i).getString("type");

                    //{ "time": "2017-06-13T12:35:08+0000", "type": "HeroSelect", "payload": { "Hero": "*Ardan*", "Team": "2", "Player": "aa701f3e-7f39-11e6-9681-06eb725f8a76", "Handle": "VATANA2" } }

                    // get all info from the telemetry
                    JSONObject payload = eventTelemetry.getJSONObject(i).getJSONObject("payload");
                    temp.actor = payload.getString("Hero");
                    temp.setTeam(payload.getInt("Team"));
                    temp.id = payload.getString("Player");
                    temp.user = payload.getString("Handle");

                    // print log
                    DateFormat pf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Log.i("time",pf.format(temp.time));
                    Log.i("Team",temp.team);
                    Log.i("Actor",temp.actor);
                    Log.i("User",temp.user);

                    // add to array
                    userInfoArray.add(temp);
                }

                // If type of event is "KillActor"
                if (eventTelemetry.getJSONObject(i).getString("type").equals("KillActor") && eventTelemetry.getJSONObject(i).getJSONObject("payload").getInt("IsHero") == 1 && eventTelemetry.getJSONObject(i).getJSONObject("payload").getInt("TargetIsHero") == 1){

                    //{ "time": "2017-06-13T12:48:10+0000", "type": "KillActor", "payload": { "Team": "Left", "Actor": "*Adagio*", "Killed": "*VainTurret*", "KilledTeam": "Right", "Gold": "100", "IsHero": 1, "TargetIsHero": 0, "Position": [ 75.48, 0.00, 11.96 ] } },

                    // set temporary object KilledActor
                    KillActor temp = new KillActor();

                    // save time of event
                    temp.formatTime(eventTelemetry.getJSONObject(i).getString("time"));
                    temp.eventType = eventTelemetry.getJSONObject(i).getString("type");

                    // get all info from the telemetry
                    JSONObject payload = eventTelemetry.getJSONObject(i).getJSONObject("payload");
                    temp.actor = payload.getString("Actor");
                    temp.team = payload.getString("Team");
                    temp.target = payload.getString("Killed");
                    temp.isHero = payload.getInt("IsHero");
                    temp.targetIsHero = payload.getInt("TargetIsHero");

                    // print log
                    DateFormat pf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Log.i("time",pf.format(temp.time));
                    Log.i("Team",temp.team);
                    Log.i("Actor",temp.actor);
                    Log.i("Target",temp.target);


                    // add to array
                    killedEvents.add(temp);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    // Background Thread
    // This Method downloads necessary page from the server
    // It reads url string,
    public class DownloadTask2 extends AsyncTask<String, Void, String> {

        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("On pre execute","On pre execute");
        }

        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);
            Log.i("On post execute","On post execute");
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

                // Get response code from the request
                int responseCode = urlConnection.getResponseCode();
                System.out.println("\nSending request to URL : " + url);
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
