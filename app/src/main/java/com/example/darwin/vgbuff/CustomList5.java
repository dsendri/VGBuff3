package com.example.darwin.vgbuff;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class CustomList5 extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private final Integer[] totalGames;
    private  final  Integer[] totalWins;
    private final Integer[] totalKills;
    private  final  Integer[] totalDeaths;
    private final Integer[] totalAssists;
    private final Integer[] mostKills;
    private  final  Integer[] mostDeaths;
    private final Integer[] mostAssists;
    private final int sortSelection;

    Integer[] sortedPos;

    public CustomList5(Activity context,
                       String[] web, Integer[] imageId, Integer[] totalGames, Integer[] totalWins, Integer[] totalKills, Integer[] totalDeaths, Integer[] totalAssists, Integer[] mostKills, Integer[] mostDeaths, Integer[] mostAssists, int sortSelection) {
        super(context, R.layout.list_view_image5, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.totalGames = totalGames;
        this.totalWins = totalWins;
        this.totalKills = totalKills;
        this.totalDeaths = totalDeaths;
        this.totalAssists = totalAssists;
        this.mostKills = mostKills;
        this.mostDeaths = mostDeaths;
        this.mostAssists = mostAssists;
        this.sortSelection = sortSelection;

        // Sort the totalGames Played
        sortedPos = new Integer[totalGames.length];

        // alphabetically
        if (sortSelection == 0){

            for (int i = 0; i < web.length; i++) sortedPos[i] = web.length - i - 1;

        } else if (sortSelection == 1){

            // Sort the most game played
            CustomList5.ArrayIndexComparator comparator = new CustomList5.ArrayIndexComparator(totalGames);
            sortedPos = comparator.createIndexArray();
            Arrays.sort(sortedPos,comparator);

        } else if (sortSelection == 2){

            // Sort the most game played
            CustomList5.ArrayIndexComparator comparator = new CustomList5.ArrayIndexComparator(totalWins);
            sortedPos = comparator.createIndexArray();
            Arrays.sort(sortedPos,comparator);
        } else if (sortSelection == 3){

            Integer[] percentWin = new Integer[totalWins.length];

            for (int i = 0; i < web.length; i++) {

                if (totalGames[i] != 0)
                    percentWin[i] = totalWins[i]*100/totalGames[i];
                else
                    percentWin[i] = 0;
            }



            // Sort the %Win
            CustomList5.ArrayIndexComparator comparator = new CustomList5.ArrayIndexComparator(percentWin);
            sortedPos = comparator.createIndexArray();
            Arrays.sort(sortedPos,comparator);

        }


    }

    public class ArrayIndexComparator implements Comparator<Integer>
    {
        private final Integer[] array;

        public ArrayIndexComparator(Integer[] array)
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
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.list_view_image5, null, true);

        // Initialize Roles
        String[] Captains = new String[] {"adagio","ardan","catherine","flicker","fortress","lance","lyra","phinn"};
        String[] Junglers = new String[] {"baptiste","grumpjaw","alpha","glaive","joule","koshka","krul","ozo","petal","reim","rona","taka"};
        String[] Carries = new String[] {"baron","blackfeather","celeste","gwen","idris","kestrel","ringo","samuel","saw","skaarf","skye","vox"};

        // Initialze Views
        TextView txtTitle = (TextView) rowView.findViewById(R.id.gameMode);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.hero);
        ImageView roleView = (ImageView) rowView.findViewById(R.id.item3);
        TextView result = (TextView) rowView.findViewById(R.id.result);
        TextView avgKDA = (TextView) rowView.findViewById(R.id.kda);
        TextView mostKillsView = (TextView) rowView.findViewById(R.id.mostKill);
        TextView mostDeathsView = (TextView) rowView.findViewById(R.id.mostDeaths);
        TextView mostAssistsView = (TextView) rowView.findViewById(R.id.mostAssist);

        // Initialize decimal format
        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(1);
        df.setRoundingMode(RoundingMode.HALF_UP);

        String tempResult;
        String tempKDA;

        int sortedPosition = sortedPos[sortedPos.length - 1 - position];

        // Output for the result and KDA
        if (totalGames[sortedPosition] == 0) {
            tempResult = "% Win : 0% (0/0)";
            tempKDA = "Avg K/D/A : 0/0/0";
        } else {
            tempResult = "% Win : "+ df.format((double) totalWins[sortedPosition]/(totalGames[sortedPosition])*100) + "% (" + String.valueOf(totalWins[sortedPosition])+"/"+ String.valueOf(totalGames[sortedPosition]) + ")";
            tempKDA = "Avg K/D/A : "+ df.format((double) totalKills[sortedPosition]/(totalGames[sortedPosition]))+"/"+df.format((double) totalDeaths[sortedPosition]/(totalGames[sortedPosition]))+"/"+df.format((double) totalAssists[sortedPosition]/(totalGames[sortedPosition]));
        }

        // Show role for each hero
        if (Arrays.asList(Captains).contains(web[sortedPosition].toLowerCase())) {
            roleView.setImageResource(R.drawable.captain_small);
        }
        else if (Arrays.asList(Junglers).contains(web[sortedPosition].toLowerCase())) {
            roleView.setImageResource(R.drawable.jungler_small);
        }
        else if (Arrays.asList(Carries).contains(web[sortedPosition].toLowerCase())) {
            roleView.setImageResource(R.drawable.carry_small);
        }

        // Show record and all data
        String tempMostKills = "Most Kills: "+ String.valueOf(mostKills[sortedPosition]);
        mostKillsView.setText(tempMostKills);

        String tempMostDeaths = "Most Deaths: "+ String.valueOf(mostDeaths[sortedPosition]);
        mostDeathsView.setText(tempMostDeaths);

        String tempMostAssists = "Most Assists: "+ String.valueOf(mostAssists[sortedPosition]);
        mostAssistsView.setText(tempMostAssists);

        avgKDA.setText(tempKDA);
        result.setText(tempResult);
        txtTitle.setText(web[sortedPosition]);
        imageView.setImageResource(imageId[sortedPosition]);
        return rowView;
    }
}