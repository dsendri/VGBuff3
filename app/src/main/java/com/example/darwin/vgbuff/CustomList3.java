package com.example.darwin.vgbuff;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

public class CustomList3 extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final VaingloryHeroAndMatches vg;
    private final String heroesDatabase;

    Integer[] sortedPos;
    Date[] datePlayed;
    DateFormat pf;

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

    public CustomList3(Activity context,
                       String[] web, VaingloryHeroAndMatches vg, String database) {
        super(context, R.layout.list_view_image2, web);
        this.context = context;
        this.web = web;
        this.vg = vg;
        this.heroesDatabase = database;

        // Set the date
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);

        // Sort the date
        sortedPos = new Integer[vg.matches.matchID.length];
        datePlayed = new Date[vg.matches.matchID.length];

        // Parse the string date to date variable type
        for (int j = 0; j<vg.matches.matchID.length; j++){

            sortedPos[j] = j;

            try {
                datePlayed[j] = df.parse(vg.matches.rawCreatedAt[j]);
                //Log.i("date",datePlayed[j].toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        //for (int z = 0; z < sortedPos.length ; z++ ) Log.i("unsorted", sortedPos[z].toString());

        // Sort the date and get the indices
        ArrayIndexComparator comparator = new ArrayIndexComparator(datePlayed);
        sortedPos = comparator.createIndexArray();
        Arrays.sort(sortedPos,comparator);


        //for (int z = 0; z < sortedPos.length ; z++ ) Log.i("sorted", sortedPos[z].toString());

        pf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    // Read Json Database
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = context.getAssets().open("vainglory_database.json");

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
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.list_view_image3, null, true);

        TextView gameModeView = (TextView) rowView.findViewById(R.id.userName1);
        TextView kdaView = (TextView) rowView.findViewById(R.id.kda);
        TextView resultView = (TextView) rowView.findViewById(R.id.totalDamage1);
        TextView minionsView = (TextView) rowView.findViewById(R.id.minions);
        TextView krakenView = (TextView) rowView.findViewById(R.id.kraken);
        TextView turretView = (TextView) rowView.findViewById(R.id.turret);
        TextView dateView = (TextView) rowView.findViewById(R.id.time);

        ImageView heroView = (ImageView) rowView.findViewById(R.id.heroImage1);
        ImageView item1View = (ImageView) rowView.findViewById(R.id.item1);
        ImageView item2View = (ImageView) rowView.findViewById(R.id.item2);
        ImageView item3View = (ImageView) rowView.findViewById(R.id.item3);
        ImageView item4View = (ImageView) rowView.findViewById(R.id.item4);
        ImageView item5View = (ImageView) rowView.findViewById(R.id.item5);
        ImageView item6View = (ImageView) rowView.findViewById(R.id.item6);

        // Set statistics and detail

        Log.i("mode",vg.matches.rawGameMode[sortedPos[sortedPos.length-1-position]].toUpperCase());
        if (vg.matches.rawGameMode[sortedPos[sortedPos.length-1-position]].toUpperCase().equals("CASUAL_ARAL")){
            gameModeView.setText("BATTLE ROYALE");
        } else if (vg.matches.rawGameMode[sortedPos[sortedPos.length-1-position]].toUpperCase().equals("BLITZ_PVP_RANKED")) {
            gameModeView.setText("BLITZ");
        } else if (vg.matches.rawGameMode[sortedPos[sortedPos.length-1-position]].toUpperCase().equals("PRIVATE_PARTY_BLITZ_MATCH")) {
            gameModeView.setText("PRIVATE BLITZ");
        } else if (vg.matches.rawGameMode[sortedPos[sortedPos.length-1-position]].toUpperCase().equals("PRIVATE_PARTY_DRAFT_MATCH")) {
            gameModeView.setText("PRIVATE CASUAL");
        } else if (vg.matches.rawGameMode[sortedPos[sortedPos.length-1-position]].toUpperCase().equals("5V5_PVP_CASUAL")) {
            gameModeView.setText("5V5 CASUAL");
        } else {
            gameModeView.setText(vg.matches.rawGameMode[sortedPos[sortedPos.length-1-position]].toUpperCase());
        }

        dateView.setText(pf.format(datePlayed[sortedPos[sortedPos.length-1-position]]));
        kdaView.setText("K/D/A: "+String.valueOf(vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].kills) +" / "+String.valueOf(vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].deaths)+" / "+String.valueOf(vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].assists));
        if (vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].win) {
            resultView.setText("Victory");
        } else {
            resultView.setText("Defeat");
        }
        minionsView.setText("Minions: "+String.valueOf(vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].minionKills));
        krakenView.setText("Krakens: "+String.valueOf(vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].kraken));
        turretView.setText("Turrets: "+String.valueOf(vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].turret));

        String[] item1 = new String[vg.matches.matchID.length];
        String[] item2 = new String[vg.matches.matchID.length];
        String[] item3 = new String[vg.matches.matchID.length];
        String[] item4 = new String[vg.matches.matchID.length];
        String[] item5 = new String[vg.matches.matchID.length];
        String[] item6 = new String[vg.matches.matchID.length];

        Integer[] itemsImage1 = new Integer[vg.matches.matchID.length];
        Integer[] itemsImage2 = new Integer[vg.matches.matchID.length];
        Integer[] itemsImage3 = new Integer[vg.matches.matchID.length];
        Integer[] itemsImage4 = new Integer[vg.matches.matchID.length];
        Integer[] itemsImage5 = new Integer[vg.matches.matchID.length];
        Integer[] itemsImage6 = new Integer[vg.matches.matchID.length];

        String[] heroes = new String[vg.matches.matchID.length];
        Integer[] heroesImage = new Integer[vg.matches.matchID.length];

        //Log.i("Length", String.valueOf(vg.matches.matchID.length));


        JSONArray itemsJsonArr;
        JSONObject vgDatabase;

        // Set Images
        try {


            vgDatabase = new JSONObject(heroesDatabase);
            itemsJsonArr = new JSONArray(vgDatabase.getString("items"));

            // Get heroes data and create JSON array of it
            JSONArray heroesJsonArr = new JSONArray(vgDatabase.getString("heroes"));

            //Log.i("Hero",vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].actor.substring(1,vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].actor.length()-1));

            // Get id of the hero's image thumbnail
            for (int i = 0; i<heroesJsonArr.length();i++){

                if (vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].actor.substring(1,vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].actor.length()-1).equals(heroesJsonArr.getJSONObject(i).getString("hero"))) {
                    heroes[sortedPos[sortedPos.length-1-position]] = heroesJsonArr.getJSONObject(i).getString("hero");
                    heroesImage[sortedPos[sortedPos.length-1-position]] = context.getResources().getIdentifier("heroes_"+(heroesJsonArr.getJSONObject(i).getString("hero")).toLowerCase()+"_thumb","drawable",context.getPackageName());

                }
                //Log.i("hero",heroesJsonArr.getJSONObject(i).getString("hero"));

            }


            for (int i = 0; i < itemsJsonArr.length(); i++) {

                if (vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].items[0].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                    item1[sortedPos[sortedPos.length-1-position]] = itemsJsonArr.getJSONObject(i).getString("item");

                    //Log.i("item0", itemsJsonArr.getJSONObject(i).getString("item"));

                    // Create appropriate file call
                    String itemName = item1[sortedPos[sortedPos.length-1-position]];
                    String[] arr = itemName.split(" ");
                    String fileName = "item";

                    //Concatenate file name with underscore
                    for (String ss : arr) {
                        fileName = fileName + "_" + ss.toLowerCase();
                    }

                    itemsImage1[sortedPos[sortedPos.length-1-position]] = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
                    //Log.i("filename0", fileName);


                }
                if (vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].items[1].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                    item2[sortedPos[sortedPos.length-1-position]] = itemsJsonArr.getJSONObject(i).getString("item");

                    //Log.i("item1", itemsJsonArr.getJSONObject(i).getString("item"));

                    // Create appropriate file call
                    String itemName = item2[sortedPos[sortedPos.length-1-position]];
                    String[] arr = itemName.split(" ");
                    String fileName = "item";

                    //Concatenate file name with underscore
                    for (String ss : arr) {
                        fileName = fileName + "_" + ss.toLowerCase();
                    }

                    itemsImage2[sortedPos[sortedPos.length-1-position]] = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
                    //Log.i("filename1", fileName);
                }
                if (vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].items[2].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                    item3[sortedPos[sortedPos.length-1-position]] = itemsJsonArr.getJSONObject(i).getString("item");

                    //Log.i("item2", itemsJsonArr.getJSONObject(i).getString("item"));

                    // Create appropriate file call
                    String itemName = item3[sortedPos[sortedPos.length-1-position]];
                    String[] arr = itemName.split(" ");
                    String fileName = "item";

                    //Concatenate file name with underscore
                    for (String ss : arr) {
                        fileName = fileName + "_" + ss.toLowerCase();
                    }

                    itemsImage3[sortedPos[sortedPos.length-1-position]] = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
                    //Log.i("filename2", fileName);
                }
                if (vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].items[3].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                    item4[sortedPos[sortedPos.length-1-position]] = itemsJsonArr.getJSONObject(i).getString("item");

                    //Log.i("item3", itemsJsonArr.getJSONObject(i).getString("item"));

                    // Create appropriate file call
                    String itemName = item4[sortedPos[sortedPos.length-1-position]];
                    String[] arr = itemName.split(" ");
                    String fileName = "item";

                    //Concatenate file name with underscore
                    for (String ss : arr) {
                        fileName = fileName + "_" + ss.toLowerCase();
                    }

                    itemsImage4[sortedPos[sortedPos.length-1-position]] = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
                    //Log.i("filename3", fileName);
                }
                if (vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].items[4].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                    item5[sortedPos[sortedPos.length-1-position]] = itemsJsonArr.getJSONObject(i).getString("item");

                    //Log.i("item4", itemsJsonArr.getJSONObject(i).getString("item"));

                    // Create appropriate file call
                    String itemName = item5[sortedPos[sortedPos.length-1-position]];
                    String[] arr = itemName.split(" ");
                    String fileName = "item";

                    //Concatenate file name with underscore
                    for (String ss : arr) {
                        fileName = fileName + "_" + ss.toLowerCase();
                    }

                    itemsImage5[sortedPos[sortedPos.length-1-position]] = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
                    //Log.i("filename4", fileName);

                }
                if (vg.matches.myParticipant[sortedPos[sortedPos.length-1-position]].items[5].equals(itemsJsonArr.getJSONObject(i).getString("item"))) {
                    item6[sortedPos[sortedPos.length-1-position]] = itemsJsonArr.getJSONObject(i).getString("item");

                    //Log.i("item5", itemsJsonArr.getJSONObject(i).getString("item"));

                    // Create appropriate file call
                    String itemName = item6[sortedPos[sortedPos.length-1-position]];
                    String[] arr = itemName.split(" ");
                    String fileName = "item";

                    //Concatenate file name with underscore
                    for (String ss : arr) {
                        fileName = fileName + "_" + ss.toLowerCase();
                    }

                    itemsImage6[sortedPos[sortedPos.length-1-position]] = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
                    //Log.i("filename5", fileName);

                }

            }
        } catch (JSONException e) {

            e.printStackTrace();

        }

        // Set Hero and Item Images
        if (heroesImage[sortedPos[sortedPos.length-1-position]]!= null)
            heroView.setImageResource(heroesImage[sortedPos[sortedPos.length-1-position]]);
        if (itemsImage1[sortedPos[sortedPos.length-1-position]] != null)
            item1View.setImageResource(itemsImage1[sortedPos[sortedPos.length-1-position]]);
        if (itemsImage2[sortedPos[sortedPos.length-1-position]] != null)
            item2View.setImageResource(itemsImage2[sortedPos[sortedPos.length-1-position]]);
        if (itemsImage3[sortedPos[sortedPos.length-1-position]] != null)
            item3View.setImageResource(itemsImage3[sortedPos[sortedPos.length-1-position]]);
        if (itemsImage4[sortedPos[sortedPos.length-1-position]] != null)
            item4View.setImageResource(itemsImage4[sortedPos[sortedPos.length-1-position]]);
        if (itemsImage5[sortedPos[sortedPos.length-1-position]] != null)
            item5View.setImageResource(itemsImage5[sortedPos[sortedPos.length-1-position]]);
        if (itemsImage6[sortedPos[sortedPos.length-1-position]] != null)
            item6View.setImageResource(itemsImage6[sortedPos[sortedPos.length-1-position]]);


        return rowView;
    }
}