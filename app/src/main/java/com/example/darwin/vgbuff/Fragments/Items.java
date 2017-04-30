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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.darwin.vgbuff.CustomList;
import com.example.darwin.vgbuff.CustomList2;
import com.example.darwin.vgbuff.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Items.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Items#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Items extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Variables
    View view;

    // Initialize List View
    ListView itemListView;

    // Items Database arrays
    String[] items;
    Integer[] itemsImage;
    String[] categories;

    // Fragment for item detail
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

    public Items() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Items.
     */
    // TODO: Rename and change types and number of parameters
    public static Items newInstance(String param1, String param2) {
        Items fragment = new Items();
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
        view = inflater.inflate(R.layout.fragment_items, container, false);

        // Find item list view
        itemListView = (ListView) view.findViewById(R.id.itemListView);

        //Create array of heroes
        final String heroesDatabase = loadJSONFromAsset();
        try {

            JSONObject vgDatabase = new JSONObject(heroesDatabase);
            JSONArray itemsJsonArr = new JSONArray(vgDatabase.getString("items"));
            items = new String[itemsJsonArr.length()];
            itemsImage = new Integer[itemsJsonArr.length()];
            categories = new String[itemsJsonArr.length()];


            for (int i = 0; i<itemsJsonArr.length();i++){
                items[i]= itemsJsonArr.getJSONObject(i).getString("item");
                Log.i("item",itemsJsonArr.getJSONObject(i).getString("item"));
                categories[i] = itemsJsonArr.getJSONObject(i).getString("category");

                // Create appropriate file call
                String itemName = items[i];
                String[] arr = itemName.split(" ");
                String fileName = "item";

                //Concatenate file name with underscore
                for ( String ss : arr) {
                    fileName = fileName + "_" + ss.toLowerCase();
                }

                itemsImage[i] = getResources().getIdentifier(fileName,"drawable",getActivity().getPackageName());
                Log.i("filename",fileName);
            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        CustomList2 adapter = new CustomList2(getActivity(), items, itemsImage,categories);
        itemListView.setAdapter(adapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Open Fragment HeroesPage and set back button to go to previous fragment
                fragmentManager = getActivity().getSupportFragmentManager();

                // Create hero detail page fragment
                Fragment itemPage = new ItemPage();

                // Create a bundle to send a data
                Bundle dataToItemPage = new Bundle();
                dataToItemPage.putInt("position",position);
                itemPage.setArguments(dataToItemPage);

                // Open Fragment
                fragmentManager.beginTransaction().replace(R.id.content_frame, itemPage).addToBackStack("tag").commit();
            }
        });

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
