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
import android.widget.ListView;
import android.widget.Toast;

import com.example.darwin.vgbuff.CustomList3;
import com.example.darwin.vgbuff.R;
import com.example.darwin.vgbuff.VaingloryHeroAndMatches;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchesHistory.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchesHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchesHistory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // View
    View view;

    // Initialize List View
    ListView matchListView;

    // Hero fav
    String heroFav;
    VaingloryHeroAndMatches vaingloryHeroAndMatches;
    Summary summary;
    Heroes heroes;
    Items items;
    MatchesHistory history;

    public MatchesHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchesHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchesHistory newInstance(String param1, String param2) {
        MatchesHistory fragment = new MatchesHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_matches_history, container, false);

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

        // Find item list view
        matchListView = (ListView) view.findViewById(R.id.matchesListView);

        // Initialize match history
        vaingloryHeroAndMatches = new VaingloryHeroAndMatches();

        // Get data from the main page
        final Bundle datatoSummaryPage = this.getArguments();

        //Create array of heroes
        final String heroesDatabase = loadJSONFromAsset();

        // Check if there is data, and get the position of the hero inside the JSON file
        if (datatoSummaryPage != null) {

            new Thread (){

                public void run() {
                    vaingloryHeroAndMatches.dataRaw = datatoSummaryPage.getString("Raw");
                    Log.i("DataFromMain", vaingloryHeroAndMatches.dataRaw);
                    Log.i("data received", datatoSummaryPage.getString("Player"));

                    //Create default player
                    vaingloryHeroAndMatches.setPlayer(datatoSummaryPage.getString("Player"));
                    vaingloryHeroAndMatches.setServerLoc(datatoSummaryPage.getString("Server"));
                    vaingloryHeroAndMatches.getMatchesHistory();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (vaingloryHeroAndMatches.matches.matchID != null) {

                                // remove loading animation
                                dialog.dismiss();

                                String[] web = new String[vaingloryHeroAndMatches.matches.matchID.length];

                                for (int i = 0; i < vaingloryHeroAndMatches.matches.matchID.length; i++)
                                    web[i] = datatoSummaryPage.getString("Player");


                                CustomList3 adapter = new CustomList3(getActivity(), web, vaingloryHeroAndMatches,heroesDatabase);
                                matchListView.setAdapter(adapter);
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




//            vaingloryHeroAndMatches.dataRaw = datatoSummaryPage.getString("Raw");
//            Log.i("DataFromMain",vaingloryHeroAndMatches.dataRaw);
//            Log.i("data received",datatoSummaryPage.getString("Player") );
//
//            //Create default player
//            vaingloryHeroAndMatches.setPlayer(datatoSummaryPage.getString("Player"));
//            vaingloryHeroAndMatches.setServerLoc(datatoSummaryPage.getString("Server"));
//            vaingloryHeroAndMatches.getMatchesHistory();
//
//            if (vaingloryHeroAndMatches.matches.matchID != null) {
//
//
//                String[] web = new String[vaingloryHeroAndMatches.matches.matchID.length];
//
//                for (int i = 0; i < vaingloryHeroAndMatches.matches.matchID.length; i++)
//                    web[i] = datatoSummaryPage.getString("Player");
//
//
//                CustomList3 adapter = new CustomList3(getActivity(), web, vaingloryHeroAndMatches,heroesDatabase);
//                matchListView.setAdapter(adapter);
//            } else {
//                Log.i("Not found","Not found");
//                Toast.makeText(getActivity().getApplicationContext(),"Player not found",Toast.LENGTH_LONG).show();
//            }



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
