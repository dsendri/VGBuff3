package com.example.darwin.vgbuff.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeroesPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeroesPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeroesPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // View Init
    ImageView heroesProfileView;
    ImageView ability1ImageView;
    ImageView ability2ImageView;
    ImageView ability3ImageView;
    ImageView ability4ImageView;
    TextView heroNameView;
    TextView hpView;
    TextView regenView;
    TextView epView;
    TextView epRegenView;
    TextView wpDmgView;
    TextView atSpdView;
    TextView armorView;
    TextView shieldView;
    TextView arView;
    TextView mvSpdView;
    TextView ability1TextView;
    TextView ability2TextView;
    TextView ability2CDManaView;
    TextView ability3TextView;
    TextView ability3CDManaView;
    TextView ability4TextView;
    TextView ability4CDManaView;
    TextView heroicPerk1;
    TextView heroicPerk2;
    TextView heroicPerk3;
    TextView heroicPerk4;


    String heroName;

    View view;

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

    public HeroesPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeroesPage.
     */
    // TODO: Rename and change types and number of parameters
    public static HeroesPage newInstance(String param1, String param2) {
        HeroesPage fragment = new HeroesPage();
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
        view = inflater.inflate(R.layout.fragment_heroes_page, container, false);

        // Find necessary views
        heroNameView = (TextView) view.findViewById(R.id.heroNameView);
        heroesProfileView = (ImageView) view.findViewById(R.id.itemProfileView);
        hpView = (TextView) view.findViewById(R.id.hpView);
        regenView = (TextView) view.findViewById(R.id.regenView);
        epView = (TextView) view.findViewById(R.id.epView);
        epRegenView = (TextView) view.findViewById(R.id.epRegenView);
        wpDmgView = (TextView) view.findViewById(R.id.wpDmgView);
        atSpdView = (TextView) view.findViewById(R.id.atSpdView);
        armorView = (TextView) view.findViewById(R.id.armorView);
        shieldView = (TextView) view.findViewById(R.id.shieldView);
        arView = (TextView) view.findViewById(R.id.arView);
        mvSpdView = (TextView) view.findViewById(R.id.mvSpdView);
        ability1TextView = (TextView) view.findViewById(R.id.ability1TextView);
        ability1ImageView = (ImageView) view.findViewById(R.id.ability1ImageView);
        ability2TextView = (TextView) view.findViewById(R.id.ability2TextView);
        ability2ImageView = (ImageView) view.findViewById(R.id.ability2ImageView);
        ability2CDManaView = (TextView) view.findViewById(R.id.ability2CDManaView);
        ability3TextView = (TextView) view.findViewById(R.id.ability3TextView);
        ability3ImageView = (ImageView) view.findViewById(R.id.ability3ImageView);
        ability3CDManaView = (TextView) view.findViewById(R.id.ability3CDManaView);
        ability4TextView = (TextView) view.findViewById(R.id.ability4TextView);
        ability4ImageView = (ImageView) view.findViewById(R.id.ability4ImageView);
        ability4CDManaView = (TextView) view.findViewById(R.id.ability4CDManaView);
        heroicPerk1 = (TextView) view.findViewById(R.id.itemName);
        heroicPerk2 = (TextView) view.findViewById(R.id.heroicPerk2);
        heroicPerk3 = (TextView) view.findViewById(R.id.heroicPerk3);
        heroicPerk4 = (TextView) view.findViewById(R.id.heroicPerk4);


        Bundle dataToHeroesPage = this.getArguments();

        final String heroesDatabase = loadJSONFromAsset();

        // Initialize the data communication from the heroes fragment
        int heroPos=-1;
        String heroName= "";

        // Check if there is data, and get the position of the hero inside the JSON file
        if (dataToHeroesPage != null) {
            heroPos = dataToHeroesPage.getInt("position");
            Log.i("Fragment Hero",heroName);
        }

        // Get hero name from hero location
        JSONObject vgDatabase = null;

        try {

            // Get corresponding hero name
            vgDatabase = new JSONObject(heroesDatabase);
            JSONArray heroesJsonArr = new JSONArray(vgDatabase.getString("heroes"));
            heroName = heroesJsonArr.getJSONObject(heroPos).getString("hero");

            // Get correponding hero stats
            String heroStatsValueJSON = heroesJsonArr.getJSONObject(heroPos).getString("hero_stats");
            JSONArray heroStatsValue = new JSONArray(heroStatsValueJSON);

            // Set hero statistic
            hpView.setText("HP\n"+Double.toString(heroStatsValue.getDouble(0))+" (+"+Double.toString(heroStatsValue.getDouble(1))+")");
            regenView.setText("HP Regen\n"+Double.toString(heroStatsValue.getDouble(2))+" (+"+Double.toString(heroStatsValue.getDouble(3))+")");
            epView.setText("EP\n"+Double.toString(heroStatsValue.getDouble(4))+" (+"+Double.toString(heroStatsValue.getDouble(5))+")");
            epRegenView.setText("EP Regen\n"+Double.toString(heroStatsValue.getDouble(6))+" (+"+Double.toString(heroStatsValue.getDouble(7))+")");
            wpDmgView.setText("Wp Dmg\n"+Double.toString(heroStatsValue.getDouble(8))+" (+"+Double.toString(heroStatsValue.getDouble(9))+")");
            atSpdView.setText("Atk Spd\n"+Double.toString(heroStatsValue.getDouble(10))+"% (+"+Double.toString(heroStatsValue.getDouble(11))+")");
            armorView.setText("Armor\n"+Double.toString(heroStatsValue.getDouble(12))+" (+"+Double.toString(heroStatsValue.getDouble(13))+")");
            shieldView.setText("Shield\n"+Double.toString(heroStatsValue.getDouble(14))+" (+"+Double.toString(heroStatsValue.getDouble(15))+")");
            arView.setText("Atk Range\n"+Double.toString(heroStatsValue.getDouble(16)));
            mvSpdView.setText("Move Speed\n"+Double.toString(heroStatsValue.getDouble(17)));

            // Set hero Ability 1
            int ability1imageId = getResources().getIdentifier("heroes_"+heroName.toLowerCase()+"_1","drawable",getActivity().getPackageName());
            heroicPerk1.setText("Heroic Perk: "+heroesJsonArr.getJSONObject(heroPos).getString("ability1"));
            ability1ImageView.setImageResource(ability1imageId);
            ability1TextView.setText(heroesJsonArr.getJSONObject(heroPos).getString("ability1")+"\n\n"+
                    heroesJsonArr.getJSONObject(heroPos).getString("ability1_desc")
            );

            // Set hero Ability 2
            // Get correponding hero stats
            String CD1ValueJSON = heroesJsonArr.getJSONObject(heroPos).getString("ability2_cd");
            JSONArray CD1Value = new JSONArray(CD1ValueJSON);
            String Mana1ValueJSON = heroesJsonArr.getJSONObject(heroPos).getString("ability2_mana");
            JSONArray Mana1Value = new JSONArray(Mana1ValueJSON);

            int ability2imageId = getResources().getIdentifier("heroes_"+heroName.toLowerCase()+"_2","drawable",getActivity().getPackageName());
            heroicPerk2.setText("Slot A : "+heroesJsonArr.getJSONObject(heroPos).getString("ability2"));
            ability2ImageView.setImageResource(ability2imageId);
            ability2CDManaView.setText("CD: "+Double.toString(CD1Value.getDouble(0))+"/"
                    +Double.toString(CD1Value.getDouble(1))+"/"
                    +Double.toString(CD1Value.getDouble(2))+"/"
                    +Double.toString(CD1Value.getDouble(3))+"/"
                    +Double.toString(CD1Value.getDouble(4))+"\n"
                    +"Mana: "+Double.toString(Mana1Value.getDouble(0))+"/"
                    +Double.toString(Mana1Value.getDouble(1))+"/"
                    +Double.toString(Mana1Value.getDouble(2))+"/"
                    +Double.toString(Mana1Value.getDouble(3))+"/"
                    +Double.toString(Mana1Value.getDouble(4))

            );
            ability2TextView.setText(
                    heroesJsonArr.getJSONObject(heroPos).getString("ability2_desc")+"\n\n"+
                    heroesJsonArr.getJSONObject(heroPos).getString("ability2_detail")
            );

            // Set hero Ability 3
            // Get correponding hero stats
            String CD2ValueJSON = heroesJsonArr.getJSONObject(heroPos).getString("ability3_cd");
            JSONArray CD2Value = new JSONArray(CD2ValueJSON);
            String Mana2ValueJSON = heroesJsonArr.getJSONObject(heroPos).getString("ability3_mana");
            JSONArray Mana2Value = new JSONArray(Mana2ValueJSON);

            int ability3imageId = getResources().getIdentifier("heroes_"+heroName.toLowerCase()+"_3","drawable",getActivity().getPackageName());
            heroicPerk3.setText("Slot B : "+heroesJsonArr.getJSONObject(heroPos).getString("ability3"));
            ability3ImageView.setImageResource(ability3imageId);
            ability3CDManaView.setText("CD: "+Double.toString(CD2Value.getDouble(0))+"/"
                    +Double.toString(CD2Value.getDouble(1))+"/"
                    +Double.toString(CD2Value.getDouble(2))+"/"
                    +Double.toString(CD2Value.getDouble(3))+"/"
                    +Double.toString(CD2Value.getDouble(4))+"\n"
                    +"Mana: "+Double.toString(Mana2Value.getDouble(0))+"/"
                    +Double.toString(Mana2Value.getDouble(1))+"/"
                    +Double.toString(Mana2Value.getDouble(2))+"/"
                    +Double.toString(Mana2Value.getDouble(3))+"/"
                    +Double.toString(Mana2Value.getDouble(4))

            );
            ability3TextView.setText(
                    heroesJsonArr.getJSONObject(heroPos).getString("ability3_desc")+"\n\n"+
                    heroesJsonArr.getJSONObject(heroPos).getString("ability3_detail")
            );

            // Set hero Ability 4
            // Get correponding hero stats
            String CD3ValueJSON = heroesJsonArr.getJSONObject(heroPos).getString("ability4_cd");
            JSONArray CD3Value = new JSONArray(CD3ValueJSON);
            String Mana3ValueJSON = heroesJsonArr.getJSONObject(heroPos).getString("ability4_mana");
            JSONArray Mana3Value = new JSONArray(Mana3ValueJSON);

            int ability4imageId = getResources().getIdentifier("heroes_"+heroName.toLowerCase()+"_4","drawable",getActivity().getPackageName());
            heroicPerk4.setText("Slot C : "+heroesJsonArr.getJSONObject(heroPos).getString("ability4"));
            ability4ImageView.setImageResource(ability4imageId);
            ability4CDManaView.setText("CD: "+Double.toString(CD3Value.getDouble(0))+"/"
                    +Double.toString(CD3Value.getDouble(1))+"/"
                    +Double.toString(CD3Value.getDouble(2))+"\n"
                    +"Mana: "+Double.toString(Mana3Value.getDouble(0))+"/"
                    +Double.toString(Mana3Value.getDouble(1))+"/"
                    +Double.toString(Mana3Value.getDouble(2))

            );
            ability4TextView.setText(
                    heroesJsonArr.getJSONObject(heroPos).getString("ability4_desc")+"\n\n"+
                    heroesJsonArr.getJSONObject(heroPos).getString("ability4_detail")
            );


        } catch (JSONException e) {

            e.printStackTrace();

        }

        // Show Profile of the hero
        Log.i("hero","heroes_"+heroName.toLowerCase()+"_profile");
        int imageId = getResources().getIdentifier("heroes_"+heroName.toLowerCase()+"_profile","drawable",getActivity().getPackageName());
        heroesProfileView.setImageResource(imageId);

        // Show Hero Name
        heroNameView.setText(heroName);



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
