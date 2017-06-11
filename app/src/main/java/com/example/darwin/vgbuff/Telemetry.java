package com.example.darwin.vgbuff;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by darwin on 6/11/2017.
 */

public class Telemetry {

    public String rawTelemetryData;
    public Telemetry.DownloadTask2 task;

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
