package com.example.darwin.vgbuff.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darwin.vgbuff.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Variables
    View view;

    TextView itemName;
    TextView itemCost;
    TextView itemDesc;
    TextView itemType;
    ImageView itemProfileView;

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

    public ItemPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemPage newInstance(String param1, String param2) {
        ItemPage fragment = new ItemPage();
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
        view = inflater.inflate(R.layout.fragment_item_page, container, false);

        itemName = (TextView) view.findViewById(R.id.itemName);
        itemCost = (TextView) view.findViewById(R.id.itemCost);
        itemType = (TextView) view.findViewById(R.id.itemType);
        itemDesc = (TextView) view.findViewById(R.id.itemDesc);
        itemProfileView = (ImageView) view.findViewById(R.id.itemProfileView);

        Bundle dataToItemPage = this.getArguments();

        final String heroesDatabase = loadJSONFromAsset();

        // Initialize the data communication from the heroes fragment
        int itemPos=-1;
        String item= "";

        // Check if there is data, and get the position of the hero inside the JSON file
        if (dataToItemPage != null) {
            itemPos = dataToItemPage.getInt("position");
        }

        // Get hero name from hero location
        JSONObject vgDatabase = null;


        try {

            // Get corresponding hero name
            vgDatabase = new JSONObject(heroesDatabase);
            JSONArray itemJsonArr = new JSONArray(vgDatabase.getString("items"));
            item = itemJsonArr.getJSONObject(itemPos).getString("item");

            // Create appropriate file call
            String itemNameTemp = item;
            String[] arr = itemNameTemp.split(" ");
            String fileName = "item";

            //Concatenate file name with underscore
            for ( String ss : arr) {
                fileName = fileName + "_" + ss.toLowerCase();
            }

            // Show Item Description
            Log.i("item",fileName);
            int imageId = getResources().getIdentifier(fileName,"drawable",getActivity().getPackageName());
            itemProfileView.setImageResource(imageId);

            String colorFont = "#00eeee"; // Light Blue

            // Show Item Name
            itemName.setText(item);

            // Show Item Cost
            itemCost.setText( Html.fromHtml("<font color='"+colorFont+"'>Cost :  </font>"+itemJsonArr.getJSONObject(itemPos).getString("cost")));

            // Show Item Type
            itemType.setText(Html.fromHtml("<font color='"+colorFont+"'>Category :  </font>"+itemJsonArr.getJSONObject(itemPos).getString("category")));

            // Show Item Desc
            itemDesc.setText(itemJsonArr.getJSONObject(itemPos).getString("desc"));



        } catch (JSONException e) {
            e.printStackTrace();
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
