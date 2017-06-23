package com.example.darwin.vgbuff.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.darwin.vgbuff.CustomList5;
import com.example.darwin.vgbuff.R;
import com.example.darwin.vgbuff.VaingloryHeroAndMatches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeroesStats.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeroesStats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeroesStats extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Initialize ListView
    ListView heroesStatsListView;

    // Initialize View
    View view;

    // Heroes Database arrays
    String[] heroes;
    Integer[] heroesImage;
    VaingloryHeroAndMatches vaingloryHeroAndMatches;

    private OnFragmentInteractionListener mListener;

    public HeroesStats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeroesStats.
     */
    // TODO: Rename and change types and number of parameters
    public static HeroesStats newInstance(String param1, String param2) {
        HeroesStats fragment = new HeroesStats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.activity_drawer_heroes_stats, menu);

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
    public boolean onOptionsItemSelected(MenuItem item) {

        final int sortedChoice;

        // handle item selection to choose the sorted technic
        int id = item.getItemId();

        // Open a coresponding fragment whenever a menu is tapped
        if (id == R.id.nav_most_played) {

            sortedChoice = 1;;
            Log.i("Choice","most played");

        } else if (id == R.id.nav_most_win) {

            sortedChoice = 2;
            Log.i("Choice","most win");

        } else if (id == R.id.nav_general) {

            sortedChoice = 0;
            Log.i("Choice","most alphabet");

        } else if (id == R.id.nav_percent) {

            sortedChoice = 3;
            Log.i("Choice","most percent");

        } else {

            sortedChoice = 0;
        }



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

        // Find heroesListView
        heroesStatsListView = (ListView) view.findViewById(R.id.heroesListView);

        //Create array of heroes
        final String heroesDatabase = loadJSONFromAsset();

        // Initialize match history
        vaingloryHeroAndMatches = new VaingloryHeroAndMatches();

        // Get data from the main page
        final Bundle datatoSummaryPage = this.getArguments();

        //Data to be passed on

        // Open hero data base
        JSONObject vgDatabaseTemp = null;
        JSONArray heroesJsonArrTemp = null;
        try {

            vgDatabaseTemp = new JSONObject(heroesDatabase);

            // Get heroes data and create JSON array of it
            heroesJsonArrTemp = new JSONArray(vgDatabaseTemp.getString("heroes"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initiliaze array and set it to 0
        final Integer[] totalGamesHeroes = new Integer[heroesJsonArrTemp.length()];
        final Integer[] totalWinsHeroes = new Integer[heroesJsonArrTemp.length()];
        final Integer[] totalKills = new Integer[heroesJsonArrTemp.length()];
        final Integer[] totalDeaths = new Integer[heroesJsonArrTemp.length()];
        final Integer[] totalAssists = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostKills = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostDeaths = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostAssists = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostKillsTemp = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostDeathsTemp = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostAssistsTemp = new Integer[heroesJsonArrTemp.length()];

        Arrays.fill(totalGamesHeroes,0);
        Arrays.fill(totalWinsHeroes,0);
        Arrays.fill(totalKills,0);
        Arrays.fill(totalDeaths,0);
        Arrays.fill(totalAssists,0);
        Arrays.fill(mostKills,0);
        Arrays.fill(mostDeaths,0);
        Arrays.fill(mostAssists,0);
        Arrays.fill(mostKillsTemp,0);
        Arrays.fill(mostDeathsTemp,0);
        Arrays.fill(mostAssistsTemp,0);

        // Check if there is data, and get the position of the hero inside the JSON file
        if (datatoSummaryPage != null) {

            new Thread() {

                public void run() {

                    // Get data from the main activity
                    vaingloryHeroAndMatches.dataRaw = datatoSummaryPage.getString("Raw");
                    Log.i("DataFromMain", vaingloryHeroAndMatches.dataRaw);
                    Log.i("data received", datatoSummaryPage.getString("Player"));

                    //Create default player
                    vaingloryHeroAndMatches.setPlayer(datatoSummaryPage.getString("Player"));
                    vaingloryHeroAndMatches.setServerLoc(datatoSummaryPage.getString("Server"));
                    vaingloryHeroAndMatches.getMatchesHistory();

                    if (vaingloryHeroAndMatches.matches.matchID != null) {

                        // Find the total games played for the last 30 days capped for 150 games
                        final int totalgames = vaingloryHeroAndMatches.matches.myParticipant.length;

                        // Check record for each hero stats
                        for (int i = 0; i < totalgames; i++) {

                            String actor = vaingloryHeroAndMatches.matches.myParticipant[i].actor;
                            //Log.i("actor", actor);

                            int location;

                            switch (actor) {

                                case "*Adagio*":

                                    location = 0;

                                    break;
                                case "*Alpha*":

                                    location = 1;

                                    break;
                                case "*Ardan*":

                                    location = 2;

                                    break;
                                case "*Baptiste":

                                    location = 3;

                                    break;
                                case "*Baron*":

                                    location = 4;

                                    break;
                                case "*Blackfeather*":

                                    location = 5;

                                    break;
                                case "*Catherine*":

                                    location = 6;

                                    break;
                                case "*Celeste*":

                                    location = 7;

                                    break;
                                case "*Flicker*":

                                    location = 8;

                                    break;
                                case "*Fortress*":

                                    location = 9;

                                    break;
                                case "*Glaive*":

                                    location = 10;

                                    break;
                                case "*Grace*":

                                    location = 11;
                                    break;
                                case "*Grumpjaw*":

                                    location = 12;

                                    break;
                                case "*Gwen*":

                                    location = 13;

                                    break;
                                case "*Idris*":

                                    location = 14;

                                    break;
                                case "*Joule*":

                                    location = 15;

                                    break;
                                case "*Kestrel*":

                                    location = 16;

                                    break;
                                case "*Koshka*":

                                    location = 17;

                                    break;
                                case "*Krul*":

                                    location = 18;

                                    break;
                                case "*Lance*":

                                    location = 19;

                                    break;
                                case "*Lyra*":

                                    location = 20;

                                    break;
                                case "*Ozo*":

                                    location = 21;

                                    break;
                                case "*Petal*":

                                    location = 22;

                                    break;
                                case "*Phinn*":

                                    location = 23;

                                    break;
                                case "*Reim*":

                                    location = 24;

                                    break;
                                case "*Ringo*":

                                    location = 25;

                                    break;
                                case "*Rona*":

                                    location = 26;

                                    break;
                                case "*Samuel*":

                                    location = 27;

                                    break;
                                case "*SAW*":

                                    location = 28;

                                    break;
                                case "*Skaarf*":

                                    location = 29;

                                    break;
                                case "*Skye*":

                                    location = 30;

                                    break;
                                case "*Taka*":

                                    location = 31;

                                    break;
                                default:

                                    location = 32;

                                    break;
                            }

                            // Get the total wins for each hero
                            if (vaingloryHeroAndMatches.matches.myParticipant[i].win)
                                totalWinsHeroes[location]++;

                            // Get total KDA for each hero to calculate avg
                            totalKills[location] = totalKills[location] + vaingloryHeroAndMatches.matches.myParticipant[i].kills;
                            totalAssists[location] = totalAssists[location] + vaingloryHeroAndMatches.matches.myParticipant[i].assists;
                            totalDeaths[location] = totalDeaths[location] + vaingloryHeroAndMatches.matches.myParticipant[i].deaths;

                            // Find the max of KDA
                            mostKillsTemp[location] = vaingloryHeroAndMatches.matches.myParticipant[i].kills;
                            if (mostKillsTemp[location] >= mostKills[location])
                                mostKills[location] = mostKillsTemp[location];

                            mostDeathsTemp[location] = vaingloryHeroAndMatches.matches.myParticipant[i].deaths;
                            if (mostDeathsTemp[location] >= mostDeaths[location])
                                mostDeaths[location] = mostDeathsTemp[location];

                            mostAssistsTemp[location] = vaingloryHeroAndMatches.matches.myParticipant[i].assists;
                            if (mostAssistsTemp[location] >= mostAssists[location])
                                mostAssists[location] = mostAssistsTemp[location];

                            // Find the total games played for each hero
                            totalGamesHeroes[location]++;

                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (vaingloryHeroAndMatches.matches.matchID != null) {

                                // remove loading animation
                                dialog.dismiss();

                                try {

                                    // Open hero data base
                                    JSONObject vgDatabase = new JSONObject(heroesDatabase);

                                    // Get heroes data and create JSON array of it
                                    JSONArray heroesJsonArr = new JSONArray(vgDatabase.getString("heroes"));
                                    heroes = new String[heroesJsonArr.length()];
                                    heroesImage = new Integer[heroesJsonArr.length()];

                                    // Get id of the hero's image thumbnail
                                    for (int i = 0; i < heroesJsonArr.length(); i++) {
                                        heroes[i] = heroesJsonArr.getJSONObject(i).getString("hero");
                                        Log.i("hero", heroesJsonArr.getJSONObject(i).getString("hero"));
                                        heroesImage[i] = getResources().getIdentifier("heroes_" + (heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase() + "_thumb", "drawable", getActivity().getPackageName());
                                    }

                                    // Create custom list layout for the listView
                                    CustomList5 adapter = new CustomList5(getActivity(), heroes, heroesImage,totalGamesHeroes,totalWinsHeroes,totalKills,totalDeaths,totalAssists,mostKills,mostDeaths,mostAssists,sortedChoice);

                                    // Attach the custom list layout to the listview
                                    heroesStatsListView.setAdapter(adapter);

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }


                            } else {

                                // remove loading animation
                                dialog.dismiss();

                                Log.i("Not found", "Not found");
                                Toast.makeText(getActivity().getApplicationContext(), "Player not found", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }.start();
        }

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_heroes_stats, container, false);

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

        // Find heroesListView
        heroesStatsListView = (ListView) view.findViewById(R.id.heroesListView);

        //Create array of heroes
        final String heroesDatabase = loadJSONFromAsset();

        // Initialize match history
        vaingloryHeroAndMatches = new VaingloryHeroAndMatches();

        // Get data from the main page
        final Bundle datatoSummaryPage = this.getArguments();

        //Data to be passed on

        // Open hero data base
        JSONObject vgDatabaseTemp = null;
        JSONArray heroesJsonArrTemp = null;
        try {

            vgDatabaseTemp = new JSONObject(heroesDatabase);

            // Get heroes data and create JSON array of it
            heroesJsonArrTemp = new JSONArray(vgDatabaseTemp.getString("heroes"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initiliaze array and set it to 0
        final Integer[] totalGamesHeroes = new Integer[heroesJsonArrTemp.length()];
        final Integer[] totalWinsHeroes = new Integer[heroesJsonArrTemp.length()];
        final Integer[] totalKills = new Integer[heroesJsonArrTemp.length()];
        final Integer[] totalDeaths = new Integer[heroesJsonArrTemp.length()];
        final Integer[] totalAssists = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostKills = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostDeaths = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostAssists = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostKillsTemp = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostDeathsTemp = new Integer[heroesJsonArrTemp.length()];
        final Integer[] mostAssistsTemp = new Integer[heroesJsonArrTemp.length()];

        Arrays.fill(totalGamesHeroes,0);
        Arrays.fill(totalWinsHeroes,0);
        Arrays.fill(totalKills,0);
        Arrays.fill(totalDeaths,0);
        Arrays.fill(totalAssists,0);
        Arrays.fill(mostKills,0);
        Arrays.fill(mostDeaths,0);
        Arrays.fill(mostAssists,0);
        Arrays.fill(mostKillsTemp,0);
        Arrays.fill(mostDeathsTemp,0);
        Arrays.fill(mostAssistsTemp,0);

        // Check if there is data, and get the position of the hero inside the JSON file
        if (datatoSummaryPage != null) {

            new Thread() {

                public void run() {

                    // Get data from the main activity
                    vaingloryHeroAndMatches.dataRaw = datatoSummaryPage.getString("Raw");
                    Log.i("DataFromMain", vaingloryHeroAndMatches.dataRaw);
                    Log.i("data received", datatoSummaryPage.getString("Player"));

                    //Create default player
                    vaingloryHeroAndMatches.setPlayer(datatoSummaryPage.getString("Player"));
                    vaingloryHeroAndMatches.setServerLoc(datatoSummaryPage.getString("Server"));
                    vaingloryHeroAndMatches.getMatchesHistory();

                    if (vaingloryHeroAndMatches.matches.matchID != null) {

                        // Find the total games played for the last 30 days capped for 150 games
                        final int totalgames = vaingloryHeroAndMatches.matches.myParticipant.length;

                        // Check record for each hero stats
                        for (int i = 0; i < totalgames; i++) {

                            String actor = vaingloryHeroAndMatches.matches.myParticipant[i].actor;
                            //Log.i("actor", actor);

                            int location;

                            switch (actor) {

                                case "*Adagio*":

                                    location = 0;

                                    break;
                                case "*Alpha*":

                                    location = 1;

                                    break;
                                case "*Ardan*":

                                    location = 2;

                                    break;
                                case "*Baptiste":

                                    location = 3;

                                    break;
                                case "*Baron*":

                                    location = 4;

                                    break;
                                case "*Blackfeather*":

                                    location = 5;

                                    break;
                                case "*Catherine*":

                                    location = 6;

                                    break;
                                case "*Celeste*":

                                    location = 7;

                                    break;
                                case "*Flicker*":

                                    location = 8;

                                    break;
                                case "*Fortress*":

                                    location = 9;

                                    break;
                                case "*Glaive*":

                                    location = 10;

                                    break;
                                case "*Grace*":

                                    location = 11;
                                    break;
                                case "*Grumpjaw*":

                                    location = 12;

                                    break;
                                case "*Gwen*":

                                    location = 13;

                                    break;
                                case "*Idris*":

                                    location = 14;

                                    break;
                                case "*Joule*":

                                    location = 15;

                                    break;
                                case "*Kestrel*":

                                    location = 16;

                                    break;
                                case "*Koshka*":

                                    location = 17;

                                    break;
                                case "*Krul*":

                                    location = 18;

                                    break;
                                case "*Lance*":

                                    location = 19;

                                    break;
                                case "*Lyra*":

                                    location = 20;

                                    break;
                                case "*Ozo*":

                                    location = 21;

                                    break;
                                case "*Petal*":

                                    location = 22;

                                    break;
                                case "*Phinn*":

                                    location = 23;

                                    break;
                                case "*Reim*":

                                    location = 24;

                                    break;
                                case "*Ringo*":

                                    location = 25;

                                    break;
                                case "*Rona*":

                                    location = 26;

                                    break;
                                case "*Samuel*":

                                    location = 27;

                                    break;
                                case "*SAW*":

                                    location = 28;

                                    break;
                                case "*Skaarf*":

                                    location = 29;

                                    break;
                                case "*Skye*":

                                    location = 30;

                                    break;
                                case "*Taka*":

                                    location = 31;

                                    break;
                                default:

                                    location = 32;

                                    break;
                            }

                            // Get the total wins for each hero
                            if (vaingloryHeroAndMatches.matches.myParticipant[i].win)
                                totalWinsHeroes[location]++;

                            // Get total KDA for each hero to calculate avg
                            totalKills[location] = totalKills[location] + vaingloryHeroAndMatches.matches.myParticipant[i].kills;
                            totalAssists[location] = totalAssists[location] + vaingloryHeroAndMatches.matches.myParticipant[i].assists;
                            totalDeaths[location] = totalDeaths[location] + vaingloryHeroAndMatches.matches.myParticipant[i].deaths;

                            // Find the max of KDA
                            mostKillsTemp[location] = vaingloryHeroAndMatches.matches.myParticipant[i].kills;
                            if (mostKillsTemp[location] >= mostKills[location])
                                mostKills[location] = mostKillsTemp[location];

                            mostDeathsTemp[location] = vaingloryHeroAndMatches.matches.myParticipant[i].deaths;
                            if (mostDeathsTemp[location] >= mostDeaths[location])
                                mostDeaths[location] = mostDeathsTemp[location];

                            mostAssistsTemp[location] = vaingloryHeroAndMatches.matches.myParticipant[i].assists;
                            if (mostAssistsTemp[location] >= mostAssists[location])
                                mostAssists[location] = mostAssistsTemp[location];

                            // Find the total games played for each hero
                            totalGamesHeroes[location]++;

                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (vaingloryHeroAndMatches.matches.matchID != null) {

                                // remove loading animation
                                dialog.dismiss();

                                try {

                                    // Open hero data base
                                    JSONObject vgDatabase = new JSONObject(heroesDatabase);

                                    // Get heroes data and create JSON array of it
                                    JSONArray heroesJsonArr = new JSONArray(vgDatabase.getString("heroes"));
                                    heroes = new String[heroesJsonArr.length()];
                                    heroesImage = new Integer[heroesJsonArr.length()];

                                    // Get id of the hero's image thumbnail
                                    for (int i = 0; i < heroesJsonArr.length(); i++) {
                                        heroes[i] = heroesJsonArr.getJSONObject(i).getString("hero");
                                        Log.i("hero", heroesJsonArr.getJSONObject(i).getString("hero"));
                                        heroesImage[i] = getResources().getIdentifier("heroes_" + (heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase() + "_thumb", "drawable", getActivity().getPackageName());
                                    }

                                    // Create custom list layout for the listView
                                    CustomList5 adapter = new CustomList5(getActivity(), heroes, heroesImage,totalGamesHeroes,totalWinsHeroes,totalKills,totalDeaths,totalAssists,mostKills,mostDeaths,mostAssists,0);

                                    // Attach the custom list layout to the listview
                                    heroesStatsListView.setAdapter(adapter);

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }


                            } else {

                                // remove loading animation
                                dialog.dismiss();

                                Log.i("Not found", "Not found");
                                Toast.makeText(getActivity().getApplicationContext(), "Player not found", Toast.LENGTH_LONG).show();
                            }
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
