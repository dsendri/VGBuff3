package com.example.darwin.vgbuff;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.darwin.vgbuff.Fragments.Heroes;
import com.example.darwin.vgbuff.Fragments.HeroesPage;
import com.example.darwin.vgbuff.Fragments.ItemPage;
import com.example.darwin.vgbuff.Fragments.Items;
import com.example.darwin.vgbuff.Fragments.MatchesHistory;
import com.example.darwin.vgbuff.Fragments.Summary;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Summary.OnFragmentInteractionListener, Heroes.OnFragmentInteractionListener, Items.OnFragmentInteractionListener, HeroesPage.OnFragmentInteractionListener, ItemPage.OnFragmentInteractionListener, MatchesHistory.OnFragmentInteractionListener {

    // This is a fragment manager to control main menu fragments
    FragmentManager fragmentManager;
    Bundle datatoSummaryPage;

    // Hero fav
    String heroFav;
    VaingloryHeroAndMatches vaingloryHeroAndMatches;
    Summary summary;
    Heroes heroes;
    Items items;
    MatchesHistory history;

    NavigationView navigationView;
    ImageView heroFaveView;
    TextView userNameNavView;

    // Doesn't save state when pausing app
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set default hero picture as Adagio
        heroFav = "Adagio";

        // Initialize match history
        vaingloryHeroAndMatches = new VaingloryHeroAndMatches();

        //Create default player
        SharedPreferences prefs = getSharedPreferences("Favorite Player", MODE_PRIVATE);
        String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        String server = prefs.getString("server", "sg"); //0 is the default value.

        Log.i("name", name);
        Log.i("server", server);


        vaingloryHeroAndMatches.setPlayer(name);
        vaingloryHeroAndMatches.setServerLoc(server);



        //vaingloryHeroAndMatches.setPlayer("santadoge");
        //vaingloryHeroAndMatches.setServerLoc("sg");
        vaingloryHeroAndMatches.setContext(this);
        vaingloryHeroAndMatches.getRawData();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // set font for user name in the navigation drawer
        Typeface titleFont=Typeface.createFromAsset(getAssets(),"woodcutternoise.ttf");

        // set user name
        userNameNavView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userNameNav);

        // set the hero thumbnail
        heroFaveView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.favHero);

        // get hero id from its name
        int imageId = getResources().getIdentifier("heroes_"+heroFav.toLowerCase()+"_thumb","drawable",getPackageName());
        heroFaveView.setImageResource(imageId);

        // attach the font
        userNameNavView.setTypeface(titleFont);

        // Initialize fragments
        summary = new Summary();
        heroes = new Heroes();
        items = new Items();
        history = new MatchesHistory();


        // Create a bundle to send a Raw Data, player and server
        datatoSummaryPage = new Bundle();
        datatoSummaryPage.putString("Raw",vaingloryHeroAndMatches.dataRaw);
        datatoSummaryPage.putString("Player",vaingloryHeroAndMatches.user);
        datatoSummaryPage.putString("Server",vaingloryHeroAndMatches.serverLoc);
        summary.setArguments(datatoSummaryPage);
        history.setArguments(datatoSummaryPage);

        navigationView.getHeaderView(0).findViewById(R.id.userNameNav);

        // On the first page, open the Account Summary Page
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, summary).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Open a coresponding fragment whenever a menu is tapped
        if (id == R.id.nav_summary) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, summary).commit();

        } else if (id == R.id.nav_heroes) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, heroes).commit();

        } else if (id == R.id.nav_items) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, items).commit();

        } else if (id == R.id.nav_match) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, history).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDataPass(String hero, String server, String username,String raw) {

        if (hero != "") {
            // Get most played hero data from fragment summary
            heroFav = hero;
            int imageId = getResources().getIdentifier("heroes_" + heroFav.toLowerCase() + "_thumb", "drawable", getPackageName());
            Log.i("data server", server);
            Log.i("data user", username);

            userNameNavView.setText(username);
            heroFaveView.setImageResource(imageId);
        } else
        {
            userNameNavView.setText("Not Found");
            heroFaveView.setImageResource(getResources().getIdentifier("heroes_adagio_thumb","drawable",getPackageName()));
        }

        // Initialize match history
        vaingloryHeroAndMatches = new VaingloryHeroAndMatches();

        vaingloryHeroAndMatches.setPlayer(username);
        vaingloryHeroAndMatches.setServerLoc(server);
        vaingloryHeroAndMatches.setContext(this);
        vaingloryHeroAndMatches.dataRaw = raw;

        // Create a bundle to send a Raw Data, player and server
        datatoSummaryPage.putString("Raw",vaingloryHeroAndMatches.dataRaw);
        datatoSummaryPage.putString("Player",vaingloryHeroAndMatches.user);
        datatoSummaryPage.putString("Server",vaingloryHeroAndMatches.serverLoc);



    }


}
