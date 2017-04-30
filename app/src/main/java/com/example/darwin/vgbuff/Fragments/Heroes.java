package com.example.darwin.vgbuff.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.darwin.vgbuff.CustomList;
import com.example.darwin.vgbuff.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Heroes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Heroes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Heroes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Initialize ListView
    ListView heroesListView;

    // Initialize View
    View view;

    // Heroes Database arrays
    String[] heroes;
    Integer[] heroesImage;

    // Fragment for hero detail
    FragmentManager fragmentManager;

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

    private OnFragmentInteractionListener mListener;

    public Heroes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Heroes.
     */
    // TODO: Rename and change types and number of parameters
    public static Heroes newInstance(String param1, String param2) {
        Heroes fragment = new Heroes();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_heroes, container, false);

        // Find heroesListView
        heroesListView = (ListView) view.findViewById(R.id.heroesListView);

        //Create array of heroes
        final String heroesDatabase = loadJSONFromAsset();
        try {

                // Open hero data base
                JSONObject vgDatabase = new JSONObject(heroesDatabase);

                // Get heroes data and create JSON array of it
                JSONArray heroesJsonArr = new JSONArray(vgDatabase.getString("heroes"));
                heroes = new String[heroesJsonArr.length()];
                heroesImage = new Integer[heroesJsonArr.length()];

                // Get id of the hero's image thumbnail
                for (int i = 0; i<heroesJsonArr.length();i++){
                    heroes[i]= heroesJsonArr.getJSONObject(i).getString("hero");
                    Log.i("hero",heroesJsonArr.getJSONObject(i).getString("hero"));
                    heroesImage[i] = getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());
                }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        // Create custom list layout for the listView
        CustomList adapter = new CustomList(getActivity(), heroes, heroesImage);

        // Attach the custom list layout to the listview
        heroesListView.setAdapter(adapter);

        // Set onclick listener when list view is tapped
        heroesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Open Fragment HeroesPage and set back button to go to previous fragment
                fragmentManager = getActivity().getSupportFragmentManager();

                // Create hero detail page fragment
                Fragment heroesPage = new HeroesPage();

                // Create a bundle to send a data
                Bundle dataToHeroesPage = new Bundle();
                dataToHeroesPage.putInt("position",position);
                heroesPage.setArguments(dataToHeroesPage);

                // Open Fragment
                fragmentManager.beginTransaction().replace(R.id.content_frame, heroesPage).addToBackStack("tag").commit();
            }
        });

        Log.i("database",heroesDatabase);


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
