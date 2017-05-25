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
import java.util.Arrays;

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


    public CustomList5(Activity context,
                       String[] web, Integer[] imageId, Integer[] totalGames, Integer[] totalWins, Integer[] totalKills, Integer[] totalDeaths, Integer[] totalAssists, Integer[] mostKills, Integer[] mostDeaths, Integer[] mostAssists) {
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

        // Output for the result and KDA
        if (totalGames[position] == 0) {
            tempResult = "% Win : 0% (0/0)";
            tempKDA = "Avg K/D/A : 0/0/0";
        } else {
            tempResult = "% Win : "+ df.format((double) totalWins[position]/(totalGames[position])*100) + "% (" + String.valueOf(totalWins[position])+"/"+ String.valueOf(totalGames[position]) + ")";
            tempKDA = "Avg K/D/A : "+ df.format((double) totalKills[position]/(totalGames[position]))+"/"+df.format((double) totalDeaths[position]/(totalGames[position]))+"/"+df.format((double) totalAssists[position]/(totalGames[position]));
        }

        // Show role for each hero
        if (Arrays.asList(Captains).contains(web[position].toLowerCase())) {
            roleView.setImageResource(R.drawable.captain_small);
        }
        else if (Arrays.asList(Junglers).contains(web[position].toLowerCase())) {
            roleView.setImageResource(R.drawable.jungler_small);
        }
        else if (Arrays.asList(Carries).contains(web[position].toLowerCase())) {
            roleView.setImageResource(R.drawable.carry_small);
        }

        // Show record and all data
        String tempMostKills = "Most Kills: "+ String.valueOf(mostKills[position]);
        mostKillsView.setText(tempMostKills);

        String tempMostDeaths = "Most Deaths: "+ String.valueOf(mostDeaths[position]);
        mostDeathsView.setText(tempMostDeaths);

        String tempMostAssists = "Most Assists: "+ String.valueOf(mostAssists[position]);
        mostAssistsView.setText(tempMostAssists);

        avgKDA.setText(tempKDA);
        result.setText(tempResult);
        txtTitle.setText(web[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}