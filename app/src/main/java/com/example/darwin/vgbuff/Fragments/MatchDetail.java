package com.example.darwin.vgbuff.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darwin.vgbuff.CustomList3;
import com.example.darwin.vgbuff.R;
import com.example.darwin.vgbuff.VaingloryHeroAndMatches;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.SignInButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    ScrollView detailView;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2909: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Granted");
                    if (detailView != null) {

                        Log.i("width",String.valueOf(detailView.getWidth()));
                        Log.i("length",String.valueOf(detailView.getHeight()));

                        Bitmap image = Bitmap.createBitmap(detailView.getChildAt(0).getWidth(),
                                detailView.getChildAt(0).getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas b = new Canvas(image);
                        detailView.draw(b);

                        String pathofBmp = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), image,"match detail", null);
                        Uri bmpUri = Uri.parse(pathofBmp);

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sendIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        sendIntent.setType("image/png");
                        startActivity(Intent.createChooser(sendIntent,"Send to:"));
                    }
                } else {
                    Log.e("Permission", "Denied");
                    Toast.makeText(getActivity().getApplicationContext(),"Access Denied",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

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

    public String getSkillTier(int skillTier){

        // Get the ranked level

        final String gold = "#FFD700";
        final String silver = "#C0C0C0";
        final String bronze = "#cd7f32";
        String ranked;
        if (skillTier / 3 == 0) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>JUST BEGINNING</font>   ";
        } else if (skillTier / 3 == 1) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>GETTING THERE</font>   ";
        } else if (skillTier / 3 == 2) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>ROCK SOLID</font>   ";
        } else if (skillTier / 3 == 3) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>WORTHY FOE</font>   ";
        } else if (skillTier / 3 == 4) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>GOT SWAGGER</font>   ";
        } else if (skillTier / 3 == 5) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>CREDIBLE THREAT</font>   ";
        } else if (skillTier / 3 == 6) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>THE HOTNESS</font>   ";
        } else if (skillTier / 3 == 7) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>SIMPLY AMAZING</font>   ";
        } else if (skillTier / 3 == 8) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'>PINNACKLE OF AWESOME</font>   ";
        } else if (skillTier / 3 == 9) {

            final String colorTier;

            if (skillTier % 3 == 0) colorTier = bronze;
            else if (skillTier % 3 == 1) colorTier = silver;
            else colorTier = gold;

            ranked = "<font color='" + colorTier + "'VAINGLORIOUS</font>   ";
        } else {

            final String colorTier;

            colorTier = bronze;


            ranked = "<font color='" + colorTier + "'>UNRANKED</font>   ";
        }
        return ranked;
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
        detailView = (ScrollView) view.findViewById(R.id.detail);

        // Initialize admob
        MobileAds.initialize(getActivity().getApplicationContext(), "ca-app-pub-7644346723158631~1016068907");

        AdView mAdView = (AdView) view.findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        // player 4
        final TextView userView4 = (TextView) view.findViewById(R.id.gameMode4);
        final TextView kdaView4 = (TextView) view.findViewById(R.id.kda4);
        final TextView resultView4 = (TextView) view.findViewById(R.id.result4);
        final TextView minionsView4 = (TextView) view.findViewById(R.id.minions4);
        final TextView krakenView4 = (TextView) view.findViewById(R.id.kraken4);
        final TextView turretView4 = (TextView) view.findViewById(R.id.turret4);
        final TextView dateView4 = (TextView) view.findViewById(R.id.time4);

        final ImageView heroView4 = (ImageView) view.findViewById(R.id.hero4);
        final ImageView item1View4 = (ImageView) view.findViewById(R.id.item14);
        final ImageView item2View4 = (ImageView) view.findViewById(R.id.item24);
        final ImageView item3View4 = (ImageView) view.findViewById(R.id.item34);
        final ImageView item4View4 = (ImageView) view.findViewById(R.id.item44);
        final ImageView item5View4 = (ImageView) view.findViewById(R.id.item54);
        final ImageView item6View4 = (ImageView) view.findViewById(R.id.item64);

        // player 5
        final TextView userView5 = (TextView) view.findViewById(R.id.gameMode5);
        final TextView kdaView5 = (TextView) view.findViewById(R.id.kda5);
        final TextView resultView5 = (TextView) view.findViewById(R.id.result5);
        final TextView minionsView5 = (TextView) view.findViewById(R.id.minions5);
        final TextView krakenView5 = (TextView) view.findViewById(R.id.kraken5);
        final TextView turretView5 = (TextView) view.findViewById(R.id.turret5);
        final TextView dateView5 = (TextView) view.findViewById(R.id.time5);

        final ImageView heroView5 = (ImageView) view.findViewById(R.id.hero5);
        final ImageView item1View5 = (ImageView) view.findViewById(R.id.item15);
        final ImageView item2View5 = (ImageView) view.findViewById(R.id.item25);
        final ImageView item3View5 = (ImageView) view.findViewById(R.id.item35);
        final ImageView item4View5 = (ImageView) view.findViewById(R.id.item45);
        final ImageView item5View5 = (ImageView) view.findViewById(R.id.item55);
        final ImageView item6View5 = (ImageView) view.findViewById(R.id.item65);

        // player 6
        final TextView userView6 = (TextView) view.findViewById(R.id.gameMode6);
        final TextView kdaView6 = (TextView) view.findViewById(R.id.kda6);
        final TextView resultView6 = (TextView) view.findViewById(R.id.result6);
        final TextView minionsView6 = (TextView) view.findViewById(R.id.minions6);
        final TextView krakenView6 = (TextView) view.findViewById(R.id.kraken6);
        final TextView turretView6 = (TextView) view.findViewById(R.id.turret6);
        final TextView dateView6 = (TextView) view.findViewById(R.id.time6);

        final ImageView heroView6 = (ImageView) view.findViewById(R.id.hero6);
        final ImageView item1View6 = (ImageView) view.findViewById(R.id.item16);
        final ImageView item2View6 = (ImageView) view.findViewById(R.id.item26);
        final ImageView item3View6 = (ImageView) view.findViewById(R.id.item36);
        final ImageView item4View6 = (ImageView) view.findViewById(R.id.item46);
        final ImageView item5View6 = (ImageView) view.findViewById(R.id.item56);
        final ImageView item6View6 = (ImageView) view.findViewById(R.id.item66);

        // summmary
        final TextView blueView = (TextView) view.findViewById(R.id.blue);
        final TextView redView = (TextView) view.findViewById(R.id.red);
        final TextView resultBlueView = (TextView) view.findViewById(R.id.resultBlue);
        final TextView resultRedView = (TextView) view.findViewById(R.id.resultRed);
        final TextView killsBlueView = (TextView) view.findViewById(R.id.killsBlue);
        final TextView killsRedView = (TextView) view.findViewById(R.id.killsRed);
        final TextView krakenBlueView = (TextView) view.findViewById(R.id.krakenBlue);
        final TextView krakenRedView = (TextView) view.findViewById(R.id.krakenRed);
        final TextView aceBlueView = (TextView) view.findViewById(R.id.aceBlue);
        final TextView aceRedView = (TextView) view.findViewById(R.id.aceRed);
        final TextView goldBlueView = (TextView) view.findViewById(R.id.goldBlue);
        final TextView goldRedView = (TextView) view.findViewById(R.id.goldRed);
        final TextView teamblueView = (TextView) view.findViewById(R.id.teamblue);
        final TextView teamredView = (TextView) view.findViewById(R.id.teamred);
        final TextView durationView = (TextView) view.findViewById(R.id.matchDetail);

        final Bundle dataToMatchDetailPage = this.getArguments();

        // shareButton Init
        final Button shareButton = (Button) view.findViewById(R.id.shareButton);


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

                                // sharebutton onclick listener
                                shareButton.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (!Settings.System.canWrite(getContext())) {
                                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                        Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
                                            }
                                        }
                                    }
                                });


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

                                // set font
                                Typeface titleFont = Typeface.createFromAsset(getActivity().getAssets(),"woodcutternoise.ttf");
                                teamblueView.setTypeface(titleFont);
                                teamredView.setTypeface(titleFont);
                                blueView.setTypeface(titleFont);
                                redView.setTypeface(titleFont);
                                //killsBlueView.setTypeface(titleFont);
                                //killsRedView.setTypeface(titleFont);
                                resultBlueView.setTypeface(titleFont);
                                resultRedView.setTypeface(titleFont);
                                //krakenBlueView.setTypeface(titleFont);
                                //krakenRedView.setTypeface(titleFont);
                                //aceBlueView.setTypeface(titleFont);
                                //aceRedView.setTypeface(titleFont);
                                //goldBlueView.setTypeface(titleFont);
                                //goldRedView.setTypeface(titleFont);
                                durationView.setTypeface(titleFont);

                                // Format duration
                                String seconds;
                                if ( vaingloryHeroAndMatches.matches.duration[sortedPos[sortedPos.length-1-matchPos]]%60 < 10)
                                    seconds = "0" + String.valueOf((vaingloryHeroAndMatches.matches.duration[sortedPos[sortedPos.length-1-matchPos]])%60);
                                else
                                    seconds = String.valueOf((vaingloryHeroAndMatches.matches.duration[sortedPos[sortedPos.length-1-matchPos]])%60);

                                                    // set summary
                                blueView.setText("TEAM BLUE");
                                redView.setText("TEAM RED");
                                durationView.setText("MATCH DETAIL\n"+String.valueOf((vaingloryHeroAndMatches.matches.duration[sortedPos[sortedPos.length-1-matchPos]])/60)+":"+seconds);
                                killsBlueView.setText("KILLS: "+String.valueOf(vaingloryHeroAndMatches.matches.heroKills1[sortedPos[sortedPos.length-1-matchPos]]));
                                killsRedView.setText("KILLS: "+String.valueOf(vaingloryHeroAndMatches.matches.heroKills2[sortedPos[sortedPos.length-1-matchPos]]));
                                krakenBlueView.setText("KRAKENS: "+String.valueOf(vaingloryHeroAndMatches.matches.kraken1[sortedPos[sortedPos.length-1-matchPos]]));
                                krakenRedView.setText("KRAKENS: "+String.valueOf(vaingloryHeroAndMatches.matches.kraken2[sortedPos[sortedPos.length-1-matchPos]]));
                                aceBlueView.setText("ACES: "+String.valueOf(vaingloryHeroAndMatches.matches.ace1[sortedPos[sortedPos.length-1-matchPos]]));
                                aceRedView.setText("ACES: "+String.valueOf(vaingloryHeroAndMatches.matches.ace2[sortedPos[sortedPos.length-1-matchPos]]));
                                goldBlueView.setText("GOLD: "+String.valueOf(vaingloryHeroAndMatches.matches.gold1[sortedPos[sortedPos.length-1-matchPos]]));
                                goldRedView.setText("GOLD: "+String.valueOf(vaingloryHeroAndMatches.matches.gold2[sortedPos[sortedPos.length-1-matchPos]]));

                                // set user name 1
                                userView.setText(vaingloryHeroAndMatches.matches.player11[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView.setText("K/D/A: "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].assists));

                                // Get the ranked level
                                resultView.setText(Html.fromHtml(getSkillTier(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].skillTier)));

                                if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].win) {
                                    teamblueView.setText("TEAM BLUE: VICTORY");
                                    resultBlueView.setText("VICTORY");
                                } else {
                                    teamblueView.setText("TEAM BLUE: DEFEAT");
                                    resultBlueView.setText("DEFEAT");
                                }
                                minionsView.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].turret));

                                // set user name 2
                                userView2.setText(vaingloryHeroAndMatches.matches.player12[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView2.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView2.setText("K/D/A: "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].assists));

                                // Get the ranked level
                                resultView2.setText(Html.fromHtml(getSkillTier(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].skillTier)));

                                minionsView2.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView2.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView2.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].turret));

                                // set user name 3
                                userView3.setText(vaingloryHeroAndMatches.matches.player13[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView3.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView3.setText("K/D/A: "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].assists));

                                // Get the ranked level
                                resultView3.setText(Html.fromHtml(getSkillTier(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].skillTier)));

                                minionsView3.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView3.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView3.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].turret));

                                // set user name 4
                                userView4.setText(vaingloryHeroAndMatches.matches.player21[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView4.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView4.setText("K/D/A: "+String.valueOf(vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].assists));

                                // Get the ranked level
                                resultView4.setText(Html.fromHtml(getSkillTier(vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].skillTier)));

                                if (vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].win) {
                                    teamredView.setText("TEAM RED: VICTORY");
                                    resultRedView.setText("VICTORY");
                                } else {
                                    teamredView.setText("TEAM RED: DEFEAT");
                                    resultRedView.setText("DEFEAT");
                                }
                                minionsView4.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView4.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView4.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].turret));

                                // set user name 5
                                userView5.setText(vaingloryHeroAndMatches.matches.player22[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView5.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView5.setText("K/D/A: "+String.valueOf(vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].assists));

                                // Get the ranked level
                                resultView5.setText(Html.fromHtml(getSkillTier(vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].skillTier)));

                                minionsView5.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView5.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView5.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].turret));

                                // set user name 6
                                userView6.setText(vaingloryHeroAndMatches.matches.player23[sortedPos[sortedPos.length-1-matchPos]].userName);

                                dateView6.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-matchPos]]));
                                kdaView6.setText("K/D/A: "+String.valueOf(vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].kills) +" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].deaths)+" / "+String.valueOf(vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].assists));

                                // Get the ranked level
                                resultView6.setText(Html.fromHtml(getSkillTier(vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].skillTier)));

                                minionsView6.setText("Minions: "+String.valueOf(vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].minionKills));
                                krakenView6.setText("Krakens: "+String.valueOf(vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].kraken));
                                turretView6.setText("Turrets: "+String.valueOf(vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].turret));


                                // Item images dan hero images
                                String[] item1 = new String[6];
                                String[] item2 = new String[6];
                                String[] item3 = new String[6];
                                String[] item4 = new String[6];
                                String[] item5 = new String[6];
                                String[] item6 = new String[6];

                                Integer[] itemsImage1 = new Integer[6];
                                Integer[] itemsImage2 = new Integer[6];
                                Integer[] itemsImage3 = new Integer[6];
                                Integer[] itemsImage4 = new Integer[6];
                                Integer[] itemsImage5 = new Integer[6];
                                Integer[] itemsImage6 = new Integer[6];

                                String[] heroes = new String[6];
                                Integer[] heroesImage = new Integer[6];

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

                                        // Find hero
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].actor.substring(1,vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))) {
                                            heroes[0] = heroesJsonArr.getJSONObject(i).getString("hero");
                                            heroesImage[0] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());
                                        }

                                        if (vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].actor.substring(1,vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))) {
                                            heroes[1] = heroesJsonArr.getJSONObject(i).getString("hero");
                                            heroesImage[1] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());
                                        }

                                        if (vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].actor.substring(1,vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))) {
                                            heroes[2] = heroesJsonArr.getJSONObject(i).getString("hero");
                                            heroesImage[2] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());
                                        }

                                        if (vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].actor.substring(1,vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))) {
                                            heroes[3] = heroesJsonArr.getJSONObject(i).getString("hero");
                                            heroesImage[3] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());
                                        }

                                        if (vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].actor.substring(1,vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))) {
                                            heroes[4] = heroesJsonArr.getJSONObject(i).getString("hero");
                                            heroesImage[4] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());
                                        }

                                        if (vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].actor.substring(1,vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))) {
                                            heroes[5] = heroesJsonArr.getJSONObject(i).getString("hero");
                                            heroesImage[5] = getActivity().getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",getActivity().getPackageName());
                                        }


                                        //Log.i("hero",heroesJsonArr.getJSONObject(i).getString("hero"));

                                    }


                                    for (int i = 0; i < itemsJsonArr.length(); i++) {

                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[0].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item1[0] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item0", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item1[0];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage1[0] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename0", fileName);


                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[1].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item2[0] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item1", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item2[0];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage2[0] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename1", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[2].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item3[0] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item2", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item3[0];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage3[0] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename2", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[3].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item4[0] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item3", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item4[0];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage4[0] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename3", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[4].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item5[0] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item4", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item5[0];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage5[0] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename4", fileName);

                                        }
                                        if (vaingloryHeroAndMatches.matches.parti11[sortedPos[sortedPos.length-1-matchPos]].items[5].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item6[0] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item5", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item6[0];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage6[0] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename5", fileName);

                                        }

                                        if (vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].items[0].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item1[1] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item0", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item1[1];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage1[1] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename0", fileName);


                                        }
                                        if (vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].items[1].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item2[1] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item1", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item2[1];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage2[1] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename1", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].items[2].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item3[1] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item2", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item3[1];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage3[1] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename2", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].items[3].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item4[1] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item3", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item4[1];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage4[1] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename3", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].items[4].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item5[1] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item4", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item5[1];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage5[1] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename4", fileName);

                                        }
                                        if (vaingloryHeroAndMatches.matches.parti12[sortedPos[sortedPos.length-1-matchPos]].items[5].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item6[1] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item5", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item6[1];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage6[1] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename5", fileName);

                                        }

                                        if (vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].items[0].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item1[2] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item0", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item1[2];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage1[2] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename0", fileName);


                                        }
                                        if (vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].items[1].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item2[2] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item1", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item2[2];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage2[2] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename1", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].items[2].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item3[2] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item2", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item3[2];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage3[2] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename2", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].items[3].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item4[2] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item3", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item4[2];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage4[2] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename3", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].items[4].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item5[2] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item4", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item5[2];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage5[2] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename4", fileName);

                                        }
                                        if (vaingloryHeroAndMatches.matches.parti13[sortedPos[sortedPos.length-1-matchPos]].items[5].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item6[2] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item5", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item6[2];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage6[2] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename5", fileName);

                                        }

                                        if (vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].items[0].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item1[3] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item0", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item1[3];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage1[3] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename0", fileName);


                                        }
                                        if (vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].items[1].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item2[3] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item1", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item2[3];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage2[3] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename1", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].items[2].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item3[3] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item2", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item3[3];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage3[3] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename2", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].items[3].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item4[3] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item3", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item4[3];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage4[3] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename3", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].items[4].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item5[3] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item4", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item5[3];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage5[3] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename4", fileName);

                                        }
                                        if (vaingloryHeroAndMatches.matches.parti21[sortedPos[sortedPos.length-1-matchPos]].items[5].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item6[3] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item5", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item6[3];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage6[3] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename5", fileName);

                                        }

                                        if (vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].items[0].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item1[4] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item0", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item1[4];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage1[4] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename0", fileName);


                                        }
                                        if (vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].items[1].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item2[4] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item1", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item2[4];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage2[4] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename1", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].items[2].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item3[4] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item2", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item3[4];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage3[4] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename2", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].items[3].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item4[4] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item3", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item4[4];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage4[4] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename3", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].items[4].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item5[4] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item4", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item5[4];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage5[4] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename4", fileName);

                                        }
                                        if (vaingloryHeroAndMatches.matches.parti22[sortedPos[sortedPos.length-1-matchPos]].items[5].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item6[4] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item5", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item6[4];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage6[4] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename5", fileName);

                                        }

                                        if (vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].items[0].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item1[5] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item0", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item1[5];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage1[5] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename0", fileName);


                                        }
                                        if (vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].items[1].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item2[5] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item1", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item2[5];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage2[5] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename1", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].items[2].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item3[5] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item2", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item3[5];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage3[5] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename2", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].items[3].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item4[5] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item3", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item4[5];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage4[5] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename3", fileName);
                                        }
                                        if (vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].items[4].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item5[5] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item4", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item5[5];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage5[5] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename4", fileName);

                                        }
                                        if (vaingloryHeroAndMatches.matches.parti23[sortedPos[sortedPos.length-1-matchPos]].items[5].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                                            item6[5] = itemsJsonArr.getJSONObject(i).getString("item");

                                            Log.i("item5", itemsJsonArr.getJSONObject(i).getString("item"));

                                            // Create appropriate file call
                                            String itemName = item6[5];
                                            String[] arr = itemName.split(" ");
                                            String fileName = "item";

                                            //Concatenate file name with underscore
                                            for (String ss : arr) {
                                                fileName = fileName + "_" + ss.toLowerCase();
                                            }

                                            itemsImage6[5] = getActivity().getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
                                            Log.i("filename5", fileName);

                                        }

                                    }
                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }

                                // Set Hero and Item Images
                                heroView.setImageResource(heroesImage[0]);
                                heroView2.setImageResource(heroesImage[1]);
                                heroView3.setImageResource(heroesImage[2]);
                                heroView4.setImageResource(heroesImage[3]);
                                heroView5.setImageResource(heroesImage[4]);
                                heroView6.setImageResource(heroesImage[5]);

                                if (itemsImage1[0]!= null)
                                    item1View.setImageResource(itemsImage1[0]);
                                if (itemsImage2[0]!= null)
                                    item2View.setImageResource(itemsImage2[0]);
                                if (itemsImage3[0]!= null)
                                    item3View.setImageResource(itemsImage3[0]);
                                if (itemsImage4[0]!= null)
                                    item4View.setImageResource(itemsImage4[0]);
                                if (itemsImage5[0]!= null)
                                    item5View.setImageResource(itemsImage5[0]);
                                if (itemsImage6[0]!= null)
                                    item6View.setImageResource(itemsImage6[0]);

                                if (itemsImage1[1]!= null)
                                    item1View2.setImageResource(itemsImage1[1]);
                                if (itemsImage2[1]!= null)
                                    item2View2.setImageResource(itemsImage2[1]);
                                if (itemsImage3[1]!= null)
                                    item3View2.setImageResource(itemsImage3[1]);
                                if (itemsImage4[1]!= null)
                                    item4View2.setImageResource(itemsImage4[1]);
                                if (itemsImage5[1]!= null)
                                    item5View2.setImageResource(itemsImage5[1]);
                                if (itemsImage6[1]!= null)
                                    item6View2.setImageResource(itemsImage6[1]);

                                if (itemsImage1[2]!= null)
                                    item1View3.setImageResource(itemsImage1[2]);
                                if (itemsImage2[2]!= null)
                                    item2View3.setImageResource(itemsImage2[2]);
                                if (itemsImage3[2]!= null)
                                    item3View3.setImageResource(itemsImage3[2]);
                                if (itemsImage4[2]!= null)
                                    item4View3.setImageResource(itemsImage4[2]);
                                if (itemsImage5[2]!= null)
                                    item5View3.setImageResource(itemsImage5[2]);
                                if (itemsImage6[2]!= null)
                                    item6View3.setImageResource(itemsImage6[2]);

                                if (itemsImage1[3]!= null)
                                    item1View4.setImageResource(itemsImage1[3]);
                                if (itemsImage2[3]!= null)
                                    item2View4.setImageResource(itemsImage2[3]);
                                if (itemsImage3[3]!= null)
                                    item3View4.setImageResource(itemsImage3[3]);
                                if (itemsImage4[3]!= null)
                                    item4View4.setImageResource(itemsImage4[3]);
                                if (itemsImage5[3]!= null)
                                    item5View4.setImageResource(itemsImage5[3]);
                                if (itemsImage6[3]!= null)
                                    item6View4.setImageResource(itemsImage6[3]);

                                if (itemsImage1[4]!= null)
                                    item1View5.setImageResource(itemsImage1[4]);
                                if (itemsImage2[4]!= null)
                                    item2View5.setImageResource(itemsImage2[4]);
                                if (itemsImage3[4]!= null)
                                    item3View5.setImageResource(itemsImage3[4]);
                                if (itemsImage4[4]!= null)
                                    item4View5.setImageResource(itemsImage4[4]);
                                if (itemsImage5[4]!= null)
                                    item5View5.setImageResource(itemsImage5[4]);
                                if (itemsImage6[4]!= null)
                                    item6View5.setImageResource(itemsImage6[4]);

                                if (itemsImage1[5]!= null)
                                    item1View6.setImageResource(itemsImage1[5]);
                                if (itemsImage2[5]!= null)
                                    item2View6.setImageResource(itemsImage2[5]);
                                if (itemsImage3[5]!= null)
                                    item3View6.setImageResource(itemsImage3[5]);
                                if (itemsImage4[5]!= null)
                                    item4View6.setImageResource(itemsImage4[5]);
                                if (itemsImage5[5]!= null)
                                    item5View6.setImageResource(itemsImage5[5]);
                                if (itemsImage6[5]!= null)
                                    item6View6.setImageResource(itemsImage6[5]);

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
