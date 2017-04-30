package com.example.darwin.vgbuff.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darwin.vgbuff.CustomList3;
import com.example.darwin.vgbuff.VaingloryHeroAndMatches;
import com.example.darwin.vgbuff.VaingloryStats;

import com.example.darwin.vgbuff.MainActivity;
import com.example.darwin.vgbuff.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Summary.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Summary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Summary extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // Initialize variable for the views
    TextView userView;
    TextView levelView;
    TextView totalWinView;
    TextView totalgamesView;
    TextView lifeTimeGoldView;
    TextView winStreakView;
    TextView totalXPView;
    TextView titleView;
    ImageView heroFavView;
    Spinner serverList;
    Button searchPlayerButton;
    EditText searchText;
    View view;
    VaingloryHeroAndMatches vaingloryHeroAndMatches;
    String colorFont;
    InputMethodManager imm;
    Integer[] sortedPos;
    Date[] datePlayed;
    DateFormat pf;
    NumberFormat df;
    Button saveButton;
    Button refreshButton;

    String[] Junglers;
    String[] Carries;
    String[] Captains;


    public Summary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Summary.
     */
    // TODO: Rename and change types and number of parameters
    public static Summary newInstance(String param1, String param2) {
        Summary fragment = new Summary();
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

        // Get the keyboard
        imm = (InputMethodManager)  getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        // Initialize match history
        vaingloryHeroAndMatches = new VaingloryHeroAndMatches();

        // Initialize Roles
        Captains = new String[] {"*adagio*","*ardan*","*catherine*","*flicker*","*fortress*","*lance*","*lyra*","*phinn*"};
        Junglers = new String[] {"*grumpjaw*","*alpha*","*glaive*","*joule*","*koshka*","*krul*","*ozo*","*petal*","*reim*","*rona*","*taka*"};
        Carries = new String[] {"*baron*","*blackfeather*","*celeste*","*gwen*","*idris*","*kestrel*","*ringo*","*samuel*","*saw*","*skaarf*","*skye*","*vox*"};

        df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(1);
        df.setRoundingMode(RoundingMode.HALF_UP);
    }

/*    // Show Player Summary Function

    void showPlayerSummary( String server, String player){

        //Create default player
        VaingloryStats vaingloryStats = new VaingloryStats();
        vaingloryStats.setPlayer(player);
        vaingloryStats.setServerLoc(server);
        vaingloryStats.getPlayerStats();
        vaingloryStats.playerSummary();

        userView.setText(vaingloryStats.user);
        levelView.setText(Html.fromHtml("<font color='"+colorFont+"'>LEVEL   </font>   "+vaingloryStats.level));
        totalWinView.setText(Html.fromHtml("<font color='"+colorFont+"'>WINS   </font>   "+vaingloryStats.totalWin));
        totalgamesView.setText(Html.fromHtml("<font color='"+colorFont+"'>TOTAL GAMES   </font>   "+vaingloryStats.totalgames));
        lifeTimeGoldView.setText(Html.fromHtml("<font color='"+colorFont+"'>GOLD EARNED   </font>   "+vaingloryStats.lifeTimeGold));
        winStreakView.setText(Html.fromHtml("<font color='"+colorFont+"'>WINNING STREAK   </font>   "+vaingloryStats.winStreak));
        totalXPView.setText(Html.fromHtml("<font color='"+colorFont+"'>TOTAL XP   </font>   "+vaingloryStats.totalXP));

    }*/

    void showPlayerSummaryMatch( String server, String player){

        vaingloryHeroAndMatches.setPlayer(player);
        vaingloryHeroAndMatches.setServerLoc(server);

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

        new Thread(){
            public void run() {

                //Create default player
                vaingloryHeroAndMatches.setContext(getActivity());
                vaingloryHeroAndMatches.getRawData();
                vaingloryHeroAndMatches.getPlayerStats();
                vaingloryHeroAndMatches.getMatchesHistory();

                final String ranked;
                final int winsIn30View;
                final int lossedIn30View;
                final int winningStreakIn30View;
                final String role;

                String tempRole = "CARRY";

                if (vaingloryHeroAndMatches.playerToBeSearched.level != null) {


                    // Set the date
                    TimeZone tz = TimeZone.getTimeZone("UTC");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                    df.setTimeZone(tz);

                    // Sort the date
                    sortedPos = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                    datePlayed = new Date[vaingloryHeroAndMatches.matches.matchID.length];

                    // Parse the string date to date variable type
                    for (int j = 0; j < vaingloryHeroAndMatches.matches.matchID.length; j++) {

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
                    ArrayIndexComparator comparator = new ArrayIndexComparator(datePlayed);
                    sortedPos = comparator.createIndexArray();
                    Arrays.sort(sortedPos, comparator);

                    // Initialize wins and losses for the last 30 days
                    int winsIn30 = 0;
                    int lossesIn30 = 0;
                    int winningStreakIn30 = 0;
                    Boolean winningStreakCountBool = true;
                    int j = 0;

                    // Get the ranked level
                    final int skillTier = vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length - 1]].skillTier;

                    final String gold = "#FFD700";
                    final String silver = "#C0C0C0";
                    final String bronze = "#cd7f32";

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

                    int captain = 0;
                    int jungler = 0;
                    int carry = 0;

                    for (int i = 0; i < vaingloryHeroAndMatches.matches.matchID.length; i++){

                        if (vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-i]].win) {
                            winsIn30++;
                        } else {
                            lossesIn30++;
                        }

                        if (vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-j]].win && winningStreakCountBool){
                            winningStreakCountBool = true;
                            winningStreakIn30++;
                            j++;
                        } else {
                            winningStreakCountBool = false;
                        }

                        if (Arrays.asList(Captains).contains(vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-i]].actor.toLowerCase())) captain++;
                        if (Arrays.asList(Junglers).contains(vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-i]].actor.toLowerCase())) jungler++;
                        if (Arrays.asList(Carries).contains(vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-i]].actor.toLowerCase())) carry++;
                    }

                    Log.i ("CAP,JUNG,CAR",captain + ","+jungler+","+carry);
                    if (captain > jungler && captain > carry) tempRole = "CAPTAIN";
                    else if (jungler > captain && jungler > carry) tempRole = "JUNGLER";
                    else tempRole = "CARRY";

                    winsIn30View = winsIn30;
                    lossedIn30View = lossesIn30;
                    winningStreakIn30View = winningStreakIn30;

                } else {

                    winsIn30View = 0;
                    lossedIn30View = 0;
                    winningStreakIn30View = 0;
                    ranked = "<font color='" + "#cd7f32" + "'>UNRANKED</font>   ";

                }


                role = tempRole;
                final String heroFav;

                //fav Hero
                switch (vaingloryHeroAndMatches.playerToBeSearched.mostPlayedHero) {
                    case 0:
                        heroFav = "Adagio";
                        break;

                    case 1:
                        heroFav = "Alpha";
                        break;

                    case 2:
                        heroFav = "Ardan";
                        break;

                    case 3:
                        heroFav = "Baptiste";
                        break;

                    case 4:
                        heroFav = "Baron";
                        break;

                    case 5:
                        heroFav = "Blackfeather";
                        break;

                    case 6:
                        heroFav = "Catherine";

                        break;
                    case 7:
                        heroFav = "Celeste";

                        break;
                    case 8:
                        heroFav = "Flicker";

                        break;
                    case 9:
                        heroFav = "Fortress";

                        break;
                    case 10:
                        heroFav = "Glaive";

                        break;
                    case 11:
                        heroFav = "Grumpjaw";

                        break;
                    case 12:
                        heroFav = "Gwen";

                        break;
                    case 13:
                        heroFav = "Idris";

                        break;
                    case 14:
                        heroFav = "Joule";

                        break;
                    case 15:
                        heroFav = "Kestrel";

                        break;
                    case 16:
                        heroFav = "Koshka";

                        break;
                    case 17:
                        heroFav = "Krul";

                        break;
                    case 18:
                        heroFav = "Lance";

                        break;
                    case 19:
                        heroFav = "Lyra";

                        break;
                    case 20:
                        heroFav = "Ozo";

                        break;
                    case 21:
                        heroFav = "Petal";

                        break;
                    case 22:
                        heroFav = "Phinn";

                        break;
                    case 23:
                        heroFav = "Reim";

                        break;
                    case 24:
                        heroFav = "Ringo";

                        break;
                    case 25:
                        heroFav = "Rona";

                        break;
                    case 26:
                        heroFav = "Samuel";

                        break;
                    case 27:
                        heroFav = "SAW";

                        break;
                    case 28:
                        heroFav = "Skaarf";

                        break;
                    case 29:
                        heroFav = "Skye";

                        break;
                    case 30:
                        heroFav = "Taka";

                        break;
                    default:
                        heroFav = "Vox";

                        break;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // remove loading animation
                        dialog.dismiss();

                        if (vaingloryHeroAndMatches.playerToBeSearched.level != null) {
                            // set View, does not need another thread
                            userView.setText(vaingloryHeroAndMatches.playerToBeSearched.playerName);
                            totalWinView.setText(Html.fromHtml("<font color='" + colorFont + "'>LEVEL   </font>   " + vaingloryHeroAndMatches.playerToBeSearched.level));
                            winStreakView.setText(Html.fromHtml("<font color='" + colorFont + "'>WINS / LOSSES</font>   " + winsIn30View + " / " + lossedIn30View));
                            levelView.setText(Html.fromHtml(ranked));
                            totalgamesView.setText(Html.fromHtml("<font color='" + colorFont + "'>WINNING STREAK   </font>   " + winningStreakIn30View));
                            lifeTimeGoldView.setText(Html.fromHtml("<font color='" + colorFont + "'>WIN PERCENTAGE   </font>   " + df.format((double) winsIn30View/(lossedIn30View+winsIn30View)*100) + "%"));
                            totalXPView.setText(Html.fromHtml("<font color='" + colorFont + "'>ROLE   </font>   " + role));

                            //Log.i("Hero Favorite",heroFav);
                            mListener.onDataPass(heroFav, vaingloryHeroAndMatches.serverLoc, vaingloryHeroAndMatches.user  ,vaingloryHeroAndMatches.dataRaw);
                            int imageId = getResources().getIdentifier("heroes_" + heroFav.toLowerCase() + "_thumb", "drawable", getActivity().getPackageName());
                            heroFavView.setImageResource(imageId);
                        } else
                        {
                            //Log.i("not found","not found");
                            userView.setText("Not found");
                            totalWinView.setText(Html.fromHtml("<font color='" + colorFont + "'>LEVEL   </font>   "));
                            winStreakView.setText(Html.fromHtml("<font color='" + colorFont + "'>WINS / LOSSES</font>   "));
                            levelView.setText(Html.fromHtml(ranked));
                            totalgamesView.setText(Html.fromHtml("<font color='" + colorFont + "'>WINNING STREAK   </font>   "));
                            lifeTimeGoldView.setText(Html.fromHtml("<font color='" + colorFont + "'>WIN PERCENTAGE   </font>   "));
                            totalXPView.setText(Html.fromHtml("<font color='" + colorFont + "'>ROLE   </font>   "));
                            mListener.onDataPass("", "", "","");
                        }


                    }
                });

            }
        }.start();

    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_summary, container, false);

        // Initialize admob
        MobileAds.initialize(getActivity().getApplicationContext(), "ca-app-pub-7644346723158631~1016068907");

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Find text view to show the result
        userView = (TextView) view.findViewById(R.id.userView);
        levelView = (TextView) view.findViewById(R.id.levelView);
        totalWinView = (TextView) view.findViewById(R.id.winView);
        totalgamesView = (TextView) view.findViewById(R.id.totalGamesView);
        lifeTimeGoldView = (TextView) view.findViewById(R.id.goldView);
        winStreakView = (TextView) view.findViewById(R.id.winStreakView);
        totalXPView = (TextView) view.findViewById(R.id.totalXPView);
        titleView = (TextView) view.findViewById(R.id.titleView);
        heroFavView = (ImageView) view.findViewById(R.id.favHero);

        // set font
        Typeface titleFont = Typeface.createFromAsset(getActivity().getAssets(),"woodcutternoise.ttf");
        titleView.setTypeface(titleFont);
        userView.setTypeface(titleFont);
        levelView.setTypeface(titleFont);
        colorFont = "#00eeee"; // Light Blue

        // Customize layout for spinner
        serverList = (Spinner) view.findViewById(R.id.serverChooser);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.server_arrays,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        serverList.setAdapter(adapter);

        // Find edit text for player search
        searchText = (EditText) view.findViewById(R.id.seachText);

        //configure search button
        searchPlayerButton = (Button) view.findViewById(R.id.searchPlayerButton);
        searchPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //InputMethodManager imm = (InputMethodManager)  getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                //Log.i("server",serverList.getSelectedItem().toString());
                //Log.i("player",searchText.getText().toString());
                showPlayerSummaryMatch(serverList.getSelectedItem().toString(),searchText.getText().toString());
                //showPlayerSummary(serverList.getSelectedItem().toString(),searchText.getText().toString());
            }
        });

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //InputMethodManager imm = (InputMethodManager)  getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    //Log.i("server",serverList.getSelectedItem().toString());
                    //Log.i("player",searchText.getText().toString());

                    showPlayerSummaryMatch(serverList.getSelectedItem().toString(),searchText.getText().toString());
                    return true;
                }
                return false;
            }
        });

        // configure save Button
        saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity().getApplicationContext(),"Player Saved",Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("Favorite Player", getContext().MODE_PRIVATE).edit();
                editor.putString("name", vaingloryHeroAndMatches.user);
                editor.putString("server", vaingloryHeroAndMatches.serverLoc);
                editor.apply();

            }
        });

        refreshButton = (Button) view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPlayerSummaryMatch(vaingloryHeroAndMatches.serverLoc,vaingloryHeroAndMatches.user);

            }
        });


        // Get data from the main page
        final Bundle datatoSummaryPage = this.getArguments();

        // Check if there is data, and get the position of the hero inside the JSON file
        if (datatoSummaryPage != null) {

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

            // New Thread
            new Thread(){
                public void run() {

                    //Create default player
                    vaingloryHeroAndMatches.dataRaw = datatoSummaryPage.getString("Raw");
                    //Log.i("DataFromMain",vaingloryHeroAndMatches.dataRaw);

                    //Create default player
                    vaingloryHeroAndMatches.setPlayer(datatoSummaryPage.getString("Player"));
                    vaingloryHeroAndMatches.setServerLoc(datatoSummaryPage.getString("Server"));
                    vaingloryHeroAndMatches.getPlayerStats();
                    vaingloryHeroAndMatches.getMatchesHistory();


                    final String ranked;
                    final int winsIn30View;
                    final int lossedIn30View;
                    final int winningStreakIn30View;
                    final String role;

                    String tempRole = "CARRY";

                    if (vaingloryHeroAndMatches.playerToBeSearched.level != null) {


                        // Set the date
                        TimeZone tz = TimeZone.getTimeZone("UTC");
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                        df.setTimeZone(tz);

                        // Sort the date
                        sortedPos = new Integer[vaingloryHeroAndMatches.matches.matchID.length];
                        datePlayed = new Date[vaingloryHeroAndMatches.matches.matchID.length];

                        // Parse the string date to date variable type
                        for (int j = 0; j < vaingloryHeroAndMatches.matches.matchID.length; j++) {

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
                        ArrayIndexComparator comparator = new ArrayIndexComparator(datePlayed);
                        sortedPos = comparator.createIndexArray();
                        Arrays.sort(sortedPos, comparator);

                        // Initialize wins and losses for the last 30 days
                        int winsIn30 = 0;
                        int lossesIn30 = 0;
                        int winningStreakIn30 = 0;
                        Boolean winningStreakCountBool = true;
                        int j = 0;

                        // Get the ranked level
                        final int skillTier = vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length - 1]].skillTier;

                        final String gold = "#FFD700";
                        final String silver = "#C0C0C0";
                        final String bronze = "#cd7f32";

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

                        int captain = 0;
                        int jungler = 0;
                        int carry = 0;

                        for (int i = 0; i < vaingloryHeroAndMatches.matches.matchID.length; i++){

                            if (vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-i]].win) {
                                winsIn30++;
                            } else {
                                lossesIn30++;
                            }

                            if (vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-j]].win && winningStreakCountBool){
                                winningStreakCountBool = true;
                                winningStreakIn30++;
                                j++;
                            } else {
                                winningStreakCountBool = false;
                            }

                            if (Arrays.asList(Captains).contains(vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-i]].actor.toLowerCase())) captain++;
                            if (Arrays.asList(Junglers).contains(vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-i]].actor.toLowerCase())) jungler++;
                            if (Arrays.asList(Carries).contains(vaingloryHeroAndMatches.matches.myParticipant[sortedPos[sortedPos.length-1-i]].actor.toLowerCase())) carry++;
                        }

                        Log.i ("CAP,JUNG,CAR",captain + ","+jungler+","+carry);
                        if (captain > jungler && captain > carry) tempRole = "CAPTAIN";
                        else if (jungler > captain && jungler > carry) tempRole = "JUNGLER";
                        else tempRole = "CARRY";

                        winsIn30View = winsIn30;
                        lossedIn30View = lossesIn30;
                        winningStreakIn30View = winningStreakIn30;

                    } else {

                        winsIn30View = 0;
                        lossedIn30View = 0;
                        winningStreakIn30View = 0;
                        ranked = "<font color='" + "#cd7f32" + "'>UNRANKED</font>   ";

                    }


                    role = tempRole;
                    final String heroFav;

                    //fav Hero
                    switch (vaingloryHeroAndMatches.playerToBeSearched.mostPlayedHero) {
                        case 0:
                            heroFav = "Adagio";
                            break;

                        case 1:
                            heroFav = "Alpha";
                            break;

                        case 2:
                            heroFav = "Ardan";
                            break;

                        case 3:
                            heroFav = "Baptiste";
                            break;

                        case 4:
                            heroFav = "Baron";
                            break;

                        case 5:
                            heroFav = "Blackfeather";
                            break;

                        case 6:
                            heroFav = "Catherine";

                            break;
                        case 7:
                            heroFav = "Celeste";

                            break;
                        case 8:
                            heroFav = "Flicker";

                            break;
                        case 9:
                            heroFav = "Fortress";

                            break;
                        case 10:
                            heroFav = "Glaive";

                            break;
                        case 11:
                            heroFav = "Grumpjaw";

                            break;
                        case 12:
                            heroFav = "Gwen";

                            break;
                        case 13:
                            heroFav = "Idris";

                            break;
                        case 14:
                            heroFav = "Joule";

                            break;
                        case 15:
                            heroFav = "Kestrel";

                            break;
                        case 16:
                            heroFav = "Koshka";

                            break;
                        case 17:
                            heroFav = "Krul";

                            break;
                        case 18:
                            heroFav = "Lance";

                            break;
                        case 19:
                            heroFav = "Lyra";

                            break;
                        case 20:
                            heroFav = "Ozo";

                            break;
                        case 21:
                            heroFav = "Petal";

                            break;
                        case 22:
                            heroFav = "Phinn";

                            break;
                        case 23:
                            heroFav = "Reim";

                            break;
                        case 24:
                            heroFav = "Ringo";

                            break;
                        case 25:
                            heroFav = "Rona";

                            break;
                        case 26:
                            heroFav = "Samuel";

                            break;
                        case 27:
                            heroFav = "SAW";

                            break;
                        case 28:
                            heroFav = "Skaarf";

                            break;
                        case 29:
                            heroFav = "Skye";

                            break;
                        case 30:
                            heroFav = "Taka";

                            break;
                        default:
                            heroFav = "Vox";

                            break;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // remove loading animation
                            dialog.dismiss();

                            if (vaingloryHeroAndMatches.playerToBeSearched.level != null) {
                                // set View, does not need another thread
                                userView.setText(vaingloryHeroAndMatches.playerToBeSearched.playerName);
                                totalWinView.setText(Html.fromHtml("<font color='" + colorFont + "'>LEVEL   </font>   " + vaingloryHeroAndMatches.playerToBeSearched.level));
                                winStreakView.setText(Html.fromHtml("<font color='" + colorFont + "'>WINS / LOSSES</font>   " + winsIn30View + " / " + lossedIn30View));
                                levelView.setText(Html.fromHtml(ranked));
                                totalgamesView.setText(Html.fromHtml("<font color='" + colorFont + "'>WINNING STREAK   </font>   " + winningStreakIn30View));
                                lifeTimeGoldView.setText(Html.fromHtml("<font color='" + colorFont + "'>WIN PERCENTAGE   </font>   " + df.format((double) winsIn30View/(lossedIn30View+winsIn30View)*100) + "%"));
                                totalXPView.setText(Html.fromHtml("<font color='" + colorFont + "'>ROLE   </font>   " + role));

                                //Log.i("Hero Favorite",heroFav);
                                mListener.onDataPass(heroFav, vaingloryHeroAndMatches.serverLoc, vaingloryHeroAndMatches.user  ,vaingloryHeroAndMatches.dataRaw);
                                int imageId = getResources().getIdentifier("heroes_" + heroFav.toLowerCase() + "_thumb", "drawable", getActivity().getPackageName());
                                heroFavView.setImageResource(imageId);
                            } else
                            {
                                //Log.i("not found","not found");
                                userView.setText("Not found");
                                totalWinView.setText(Html.fromHtml("<font color='" + colorFont + "'>LEVEL   </font>   "));
                                winStreakView.setText(Html.fromHtml("<font color='" + colorFont + "'>WINS / LOSSES</font>   "));
                                levelView.setText(Html.fromHtml(ranked));
                                totalgamesView.setText(Html.fromHtml("<font color='" + colorFont + "'>WINNING STREAK   </font>   "));
                                lifeTimeGoldView.setText(Html.fromHtml("<font color='" + colorFont + "'>WIN PERCENTAGE   </font>   "));
                                totalXPView.setText(Html.fromHtml("<font color='" + colorFont + "'>ROLE   </font>   "));
                                mListener.onDataPass("", "", "","");
                            }


                        }
                    });

                }
            }.start();

        }

        //showPlayerSummaryMatch("sg","santadoge");

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

        // Send data to main activity
        void onDataPass(String hero, String server, String username,String raw);
    }

}



