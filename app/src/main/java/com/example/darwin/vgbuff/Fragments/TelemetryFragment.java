package com.example.darwin.vgbuff.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darwin.vgbuff.R;
import com.example.darwin.vgbuff.Telemetry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TelemetryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TelemetryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TelemetryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Variables
    View view;
    String url;
    Telemetry telemetry;
    JSONArray heroesJsonArr;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TelemetryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TelemetryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TelemetryFragment newInstance(String param1, String param2) {
        TelemetryFragment fragment = new TelemetryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Read Json Database
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open("vainglory_database.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_telemetry, container, false);

        // Initialize telemetry
        telemetry = new Telemetry();

        // Get data from the main page
        final Bundle dataToDetail = this.getArguments();

        // Check if there is data, and get the position of the hero inside the JSON file
        if (dataToDetail != null) {

            // Create loading animation while data is being processed
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage("Loading...");
                    dialog.setCanceledOnTouchOutside(false);
                    if(!dialog.isShowing()){
                        dialog.show();
                    }
                }
            });

            // New Thread
            new Thread(){
                public void run() {

                    url = dataToDetail.getString("URL");
                    Log.i("url",url);

                    telemetry.getRawTelemetryData(url);
                    telemetry.formatRawDataToArrayEvent();

                    // Get database
                    String heroesDatabase = loadJSONFromAsset();

                    JSONObject vgDatabase = null;

                    try {

                        // Create jsonobject from database
                        vgDatabase = new JSONObject(heroesDatabase);

                        // Get heroes data and create JSON array of it
                        heroesJsonArr = new JSONArray(vgDatabase.getString("heroes"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // remove loading animation
                            dialog.dismiss();
                            Log.i("raw",telemetry.rawTelemetryData);

                            // Initialize UI Elements
                            ImageView heroImage1 = (ImageView) view.findViewById(R.id.heroImage1);
                            TextView user1 = (TextView) view.findViewById(R.id.userName1);
                            TextView totalDamage1 = (TextView) view.findViewById(R.id.totalDamage1);
                            ImageView enemy1 = (ImageView) view.findViewById(R.id.enemy1);
                            ImageView enemy2 = (ImageView) view.findViewById(R.id.enemy2);
                            ImageView enemy3 = (ImageView) view.findViewById(R.id.enemy3);

                            String[] heroes = new String[6];
                            Integer[] heroesImage = new Integer[6];

                            Log.i("hero",telemetry.userInfoArrayBlue.get(0).actor.substring(1,telemetry.userInfoArrayBlue.get(0).actor.length()-1));
                            Log.i("hero1",telemetry.userInfoArrayBlue.get(1).actor.substring(1,telemetry.userInfoArrayBlue.get(1).actor.length()-1));
                            Log.i("hero2",telemetry.userInfoArrayBlue.get(2).actor.substring(1,telemetry.userInfoArrayBlue.get(2).actor.length()-1));
                            Log.i("hero3",telemetry.userInfoArrayRed.get(0).actor.substring(1,telemetry.userInfoArrayRed.get(0).actor.length()-1));
                            Log.i("hero4",telemetry.userInfoArrayRed.get(1).actor.substring(1,telemetry.userInfoArrayRed.get(1).actor.length()-1));
                            Log.i("hero5",telemetry.userInfoArrayRed.get(2).actor.substring(1,telemetry.userInfoArrayRed.get(2).actor.length()-1));

                            try {

                                // Set hero image
                                for (int i = 0; i<heroesJsonArr.length();i++){

                                    // Find hero
                                    if (telemetry.userInfoArrayBlue.get(0).actor.substring(1,telemetry.userInfoArrayBlue.get(0).actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))){

                                        heroes[0] = heroesJsonArr.getJSONObject(i).getString("hero");
                                        heroesImage[0] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());

                                    }

                                    // Find hero
                                    if (telemetry.userInfoArrayBlue.get(1).actor.substring(1,telemetry.userInfoArrayBlue.get(1).actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))){

                                        heroes[1] = heroesJsonArr.getJSONObject(i).getString("hero");
                                        heroesImage[1] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());

                                    }

                                    // Find hero
                                    if (telemetry.userInfoArrayBlue.get(2).actor.substring(1,telemetry.userInfoArrayBlue.get(2).actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))){

                                        heroes[2] = heroesJsonArr.getJSONObject(i).getString("hero");
                                        heroesImage[2] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());

                                    }

                                    // Find hero
                                    if (telemetry.userInfoArrayRed.get(0).actor.substring(1,telemetry.userInfoArrayRed.get(0).actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))){

                                        heroes[3] = heroesJsonArr.getJSONObject(i).getString("hero");
                                        heroesImage[3] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());

                                    }

                                    // Find hero
                                    if (telemetry.userInfoArrayRed.get(1).actor.substring(1,telemetry.userInfoArrayRed.get(1).actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))){

                                        heroes[4] = heroesJsonArr.getJSONObject(i).getString("hero");
                                        heroesImage[4] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());

                                    }

                                    // Find hero
                                    if (telemetry.userInfoArrayRed.get(2).actor.substring(1,telemetry.userInfoArrayRed.get(2).actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))){

                                        heroes[5] = heroesJsonArr.getJSONObject(i).getString("hero");
                                        heroesImage[5] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());

                                    }
                                    //Log.i("hero",heroesJsonArr.getJSONObject(i).getString("hero"));



                                }
                            }   catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // Set team blue hero 1 info
                            heroImage1.setImageResource(heroesImage[0]);
                            enemy1.setImageResource(heroesImage[3]);
                            enemy2.setImageResource(heroesImage[4]);
                            enemy3.setImageResource(heroesImage[5]);
                            user1.setText(telemetry.userInfoArrayBlue.get(0).user);



                        }
                    });

                }
            }.start();

        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
