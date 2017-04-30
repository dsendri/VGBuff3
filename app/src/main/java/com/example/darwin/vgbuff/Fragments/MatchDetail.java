package com.example.darwin.vgbuff.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darwin.vgbuff.CustomList3;
import com.example.darwin.vgbuff.R;
import com.example.darwin.vgbuff.VaingloryHeroAndMatches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Variable
    View view;

    private OnFragmentInteractionListener mListener;

    public class ArrayIndexComparator implements Comparator<Integer>
    {
        private final Date[] array;

        public ArrayIndexComparator(Date[] array)
        {
            this.array = array;
        }

        public Integer[] createIndexArray()
        {
            Integer[] indexes = new Integer[array.length];
            for (int i = 0; i < array.length; i++)
            {
                indexes[i] = i; // Autoboxing
            }
            return indexes;
        }

        @Override
        public int compare(Integer index1, Integer index2)
        {
            // Autounbox from Integer to int to use as array indexes
            return array[index1].compareTo(array[index2]);
        }
    }

    public MatchDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchDetail newInstance(String param1, String param2) {
        MatchDetail fragment = new MatchDetail();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_detail, container, false);

        // Create loading animation while data is being processed
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.setMessage("Loading...");
                if(!dialog.isShowing()){
                    dialog.show();
                }
            }
        });

        // player 1
        final TextView userView = (TextView) view.findViewById(R.id.gameMode);
        final TextView kdaView = (TextView) view.findViewById(R.id.kda);
        final TextView resultView = (TextView) view.findViewById(R.id.result);
        final TextView minionsView = (TextView) view.findViewById(R.id.minions);
        final TextView krakenView = (TextView) view.findViewById(R.id.kraken);
        final TextView turretView = (TextView) view.findViewById(R.id.turret);
        final TextView dateView = (TextView) view.findViewById(R.id.time);

        final ImageView heroView = (ImageView) view.findViewById(R.id.hero);
        final ImageView item1View = (ImageView) view.findViewById(R.id.item1);
        final ImageView item2View = (ImageView) view.findViewById(R.id.item2);
        final ImageView item3View = (ImageView) view.findViewById(R.id.item3);
        final ImageView item4View = (ImageView) view.findViewById(R.id.item4);
        final ImageView item5View = (ImageView) view.findViewById(R.id.item5);
        final ImageView item6View = (ImageView) view.findViewById(R.id.item6);

        // player 2
        final TextView userView2 = (TextView) view.findViewById(R.id.gameMode2);
        final TextView kdaView2 = (TextView) view.findViewById(R.id.kda2);
        final TextView resultView2 = (TextView) view.findViewById(R.id.result2);
        final TextView minionsView2 = (TextView) view.findViewById(R.id.minions2);
        final TextView krakenView2 = (TextView) view.findViewById(R.id.kraken2);
        final TextView turretView2 = (TextView) view.findViewById(R.id.turret2);
        final TextView dateView2 = (TextView) view.findViewById(R.id.time2);

        final ImageView heroView2 = (ImageView) view.findViewById(R.id.hero2);
        final ImageView item1View2 = (ImageView) view.findViewById(R.id.item12);
        final ImageView item2View2 = (ImageView) view.findViewById(R.id.item22);
        final ImageView item3View2 = (ImageView) view.findViewById(R.id.item32);
        final ImageView item4View2 = (ImageView) view.findViewById(R.id.item42);
        final ImageView item5View2 = (ImageView) view.findViewById(R.id.item52);
        final ImageView item6View2 = (ImageView) view.findViewById(R.id.item62);

        // player 3
        final TextView userView3 = (TextView) view.findViewById(R.id.gameMode3);
        final TextView kdaView3 = (TextView) view.findViewById(R.id.kda3);
        final TextView resultView3 = (TextView) view.findViewById(R.id.result3);
        final TextView minionsView3 = (TextView) view.findViewById(R.id.minions3);
        final TextView krakenView3 = (TextView) view.findViewById(R.id.kraken3);
        final TextView turretView3 = (TextView) view.findViewById(R.id.turret3);
        final TextView dateView3 = (TextView) view.findViewById(R.id.time3);

        final ImageView heroView3 = (ImageView) view.findViewById(R.id.hero3);
        final ImageView item1View3 = (ImageView) view.findViewById(R.id.item13);
        final ImageView item2View3 = (ImageView) view.findViewById(R.id.item23);
        final ImageView item3View3 = (ImageView) view.findViewById(R.id.item33);
        final ImageView item4View3 = (ImageView) view.findViewById(R.id.item43);
        final ImageView item5View3 = (ImageView) view.findViewById(R.id.item53);
        final ImageView item6View3 = (ImageView) view.findViewById(R.id.item63);

        final Bundle dataToMatchDetailPage = this.getArguments();

        final String heroesDatabase = loadJSONFromAsset();

        // Initialize match history
        final VaingloryHeroAndMatches vaingloryHeroAndMatches = new VaingloryHeroAndMatches();

        // Check if there is data, and get the position of the hero inside the JSON file
        if (dataToMatchDetailPage != null) {

            new Thread (){

                public void run() {

                    final int matchPos = dataToMatchDetailPage.getInt("position");
                    vaingloryHeroAndMatches.dataRaw = dataToMatchDetailPage.getString("raw");

                    //Create default player
                    vaingloryHeroAndMatches.setPlayer(dataToMatchDetailPage.getString("player"));
                    vaingloryHeroAndMatches.getMatchesHistory();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (vaingloryHeroAndMatches.matches.matchID != null) {

                                // remove loading animation
                                dialog.dismiss();
                                Log.i("user name",dataToMatchDetailPage.getString("player"));

                                Integer[] sortedPos;
                                Date[] datePlayed;
                                DateFormat pf;

                                // Set the date
                                TimeZone tz = TimeZone.getTimeZone("UTC");
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                                df.setTimeZone(tz);

                                // Sort the date
                                sortedPos = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                datePlayed = new Date[vaingloryHeroAndMatches.matches.matchID.length];

                                // Parse the string date to date variable type
                                for (int j = 0; j<vaingloryHeroAndMatches.matches.matchID.length; j++){

                                    sortedPos[j] = j;

                                    try {
                                        datePlayed[j] = df.parse(vaingloryHeroAndMatches.matches.rawCreatedAt[j]);
                                        //Log.i("date",datePlayed[j].toString());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }

                                //for (int z = 0; z < sortedPos.length ; z++ ) Log.i("unsorted", sortedPos[z].toString());

                                // Sort the date and get the indices
                                MatchDetail.ArrayIndexComparator comparator = new MatchDetail.ArrayIndexComparator(datePlayed);
                                sortedPos = comparator.createIndexArray();
                                Arrays.sort(sortedPos,comparator);


                                //for (int z = 0; z < sortedPos.length ; z++ ) Log.i("sorted", sortedPos[z].toString());

                                pf = new SimpleDateFormat("dd/MM/yyyy HH:mm");


                                // set user name 1
                                userView.setText(vaingloryHeroAndMatches.matches.player11[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView.setText(String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].assists));
                                if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].win) {
                                    resultView.setText("Victory");
                                } else {
                                    resultView.setText("Defeat");
                                }
                                minionsView.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].turret));

                                // set user name 2
                                userView2.setText(vaingloryHeroAndMatches.matches.player12[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView2.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView2.setText(String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].assists));
                                if (vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].win) {
                                    resultView2.setText("Victory");
                                } else {
                                    resultView2.setText("Defeat");
                                }
                                minionsView2.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView2.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView2.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].turret));

                                // set user name 3
                                userView3.setText(vaingloryHeroAndMatches.matches.player13[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView3.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView3.setText(String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].assists));
                                if (vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].win) {
                                    resultView3.setText("Victory");
                                } else {
                                    resultView3.setText("Defeat");
                                }
                                minionsView3.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView3.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView3.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].turret));


                                String[] item1 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item2 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item3 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item4 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item5 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item6 = new String[vaingloryHeroAndMatches.matches.matchID.length];

                                Integer[] itemsImage1 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage2 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage3 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage4 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage5 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage6 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];

                                String[] heroes = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] heroesImage = new Integer[vaingloryHeroAndMatches.matches.matchID.length];

                                String[] item12 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item22 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item32 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item42 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item52 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                String[] item62 = new String[vaingloryHeroAndMatches.matches.matchID.length];

                                Integer[] itemsImage12 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage22 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage32 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage42 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage52 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] itemsImage62 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];

                                String[] heroes2 = new String[vaingloryHeroAndMatches.matches.matchID.length];
                                Integer[] heroesImage2 = new Integer[vaingloryHeroAndMatches.matches.matchID.length];


                                JSONArray itemsJsonArr;
                                JSONObject vgDatabase;

                                // Set Images
                                try {


                                    vgDatabase = new JSONObject(heroesDatabase);
                                    itemsJsonArr = new JSONArray(vgDatabase.getString("items"));

                                    // Get heroes data and create JSON array of it
                                    JSONArray heroesJsonArr = new JSONArray(vgDatabase.getString("heroes"));

                                                                        // Get id of the hero's image thumbnail
                                    for (int i = 0; i<heroesJsonArr.length();i++){

                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].actor.substring(1,vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))) {
                                            heroes[sortedPos[sortedPos.length-1-matchPos]] = heroesJsonArr.getJSONObject(i).getString("hero");
                                            heroesImage[sortedPos[sortedPos.length-1-matchPos]] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());

                                        }
                                        //Log.i("hero",heroesJsonArr.getJSONObject(i).getString("hero"));

                                    }


                                    for (int i = 0; i < itemsJsonArr.length(); i++) {

                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[0].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item1[sortedPos[sortedPos.length-1-matchPos]] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item0", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item1[sortedPos[sortedPos.length-1-matchPos]];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage1[sortedPos[sortedPos.length-1-matchPos]] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename0", fileName);


                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[1].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item2[sortedPos[sortedPos.length-1-matchPos]] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item1", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item2[sortedPos[sortedPos.length-1-matchPos]];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage2[sortedPos[sortedPos.length-1-matchPos]] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename1", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[2].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item3[sortedPos[sortedPos.length-1-matchPos]] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item2", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item3[sortedPos[sortedPos.length-1-matchPos]];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage3[sortedPos[sortedPos.length-1-matchPos]] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename2", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[3].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item4[sortedPos[sortedPos.length-1-matchPos]] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item3", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item4[sortedPos[sortedPos.length-1-matchPos]];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage4[sortedPos[sortedPos.length-1-matchPos]] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename3", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[4].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item5[sortedPos[sortedPos.length-1-matchPos]] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item4", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item5[sortedPos[sortedPos.length-1-matchPos]];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage5[sortedPos[sortedPos.length-1-matchPos]] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename4", fileName);

                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[5].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item6[sortedPos[sortedPos.length-1-matchPos]] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item5", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item6[sortedPos[sortedPos.length-1-matchPos]];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage6[sortedPos[sortedPos.length-1-matchPos]] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename5", fileName);

                                        }

                                    }
                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }

                                // Set Hero and Item Images
                                if (heroesImage[sortedPos[sortedPos.length-1-matchPos]]!= null)
                                    heroView.setImageResource(heroesImage[sortedPos[sortedPos.length-1-matchPos]]);
                                if (itemsImage1[sortedPos[sortedPos.length-1-matchPos]] != null)
                                    item1View.setImageResource(itemsImage1[sortedPos[sortedPos.length-1-matchPos]]);
                                if (itemsImage2[sortedPos[sortedPos.length-1-matchPos]] != null)
                                    item2View.setImageResource(itemsImage2[sortedPos[sortedPos.length-1-matchPos]]);
                                if (itemsImage3[sortedPos[sortedPos.length-1-matchPos]] != null)
                                    item3View.setImageResource(itemsImage3[sortedPos[sortedPos.length-1-matchPos]]);
                                if (itemsImage4[sortedPos[sortedPos.length-1-matchPos]] != null)
                                    item4View.setImageResource(itemsImage4[sortedPos[sortedPos.length-1-matchPos]]);
                                if (itemsImage5[sortedPos[sortedPos.length-1-matchPos]] != null)
                                    item5View.setImageResource(itemsImage5[sortedPos[sortedPos.length-1-matchPos]]);
                                if (itemsImage6[sortedPos[sortedPos.length-1-matchPos]] != null)
                                    item6View.setImageResource(itemsImage6[sortedPos[sortedPos.length-1-matchPos]]);

                            } else {

                                // remove loading animation
                                dialog.dismiss();

                                Log.i("Not found","Not found");
                                Toast.makeText(getActivity().getApplicationContext(),"Player not found",Toast.LENGTH_LONG).show();
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
