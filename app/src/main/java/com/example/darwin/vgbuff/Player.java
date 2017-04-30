package com.example.darwin.vgbuff;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by darwin on 4/2/2017.
 */

public class Player {

    // Variables

    // User
    public String playerName;

    // Loc
    public String serverLoc;

    // Raw Data from player API Call
    public String resultRaw;

    // Stats
    public String level;
    public String totalWin;
    public String totalgames;
    public String lifeTimeGold;
    public String lossStreak;
    public String winStreak;
    public String totalXP;
    public String playedRank;
    public String playerID;
    public int arrayLocPlayer;
    public int mostPlayedHero;
    public int mostPlayedHeroCount;

    JSONObject playerStats;

    // HeroPlayed
    public int[] heroPlayed = new int[32];

    // Get number of hero played in the last 30 days
    void getNumberOfHeroPlayed(){

        // Reinitialize mostplayedhero position to 0
        mostPlayedHero = 0;

        // Set all hero count to 0
        for (int i = 0; i< heroPlayed.length;i++){
            heroPlayed[i] = 0;
        }

        try {

            // Get the first player from the array
            JSONArray playerHeroArr = new JSONArray(playerStats.getString("included"));

            // String for hero name in json file
            String actor;

            // Find the play count of each hero
            for (int i = 0; i < playerHeroArr.length(); i++) {

                // Check if type of data is participant
                if (playerHeroArr.getJSONObject(i).getString("type").equals("participant")){

                    // Check if the data is equal to player id
                    if (playerHeroArr.getJSONObject(i).getJSONObject("relationships").getJSONObject("player").getJSONObject("data").getString("id").equals(playerID)) {

                        actor = playerHeroArr.getJSONObject(i).getJSONObject("attributes").getString("actor");
                        //Log.i("actor", actor);

                        switch (actor){

                            case "*Adagio*":
                                heroPlayed[0]++;
                                break;
                            case "*Alpha*":
                                heroPlayed[1]++;
                                break;
                            case "*Ardan*":
                                heroPlayed[2]++;
                                break;
                            case "*Baptiste":
                                heroPlayed[3]++;
                                break;
                            case "*Baron*":
                                heroPlayed[4]++;
                                break;
                            case "*Blackfeather*":
                                heroPlayed[5]++;
                                break;
                            case "*Catherine*":
                                heroPlayed[6]++;
                                break;
                            case "*Celeste*":
                                heroPlayed[7]++;
                                break;
                            case "*Flicker*":
                                heroPlayed[8]++;
                                break;
                            case "*Fortress*":
                                heroPlayed[9]++;
                                break;
                            case "*Glaive*":
                                heroPlayed[10]++;
                                break;
                            case "*Grumpjaw*":
                                heroPlayed[11]++;
                                break;
                            case "*Gwen*":
                                heroPlayed[12]++;
                                break;
                            case "*Idris*":
                                heroPlayed[13]++;
                                break;
                            case "*Joule*":
                                heroPlayed[14]++;
                                break;
                            case "*Kestrel*":
                                heroPlayed[15]++;
                                break;
                            case "*Koshka*":
                                heroPlayed[16]++;
                                break;
                            case "*Krul*":
                                heroPlayed[17]++;
                                break;
                            case "*Lance*":
                                heroPlayed[18]++;
                                break;
                            case "*Lyra*":
                                heroPlayed[19]++;
                                break;
                            case "*Ozo*":
                                heroPlayed[20]++;
                                break;
                            case "*Petal*":
                                heroPlayed[21]++;
                                break;
                            case "*Phinn*":
                                heroPlayed[22]++;
                                break;
                            case "*Reim*":
                                heroPlayed[23]++;
                                break;
                            case "*Ringo*":
                                heroPlayed[24]++;
                                break;
                            case "*Rona*":
                                heroPlayed[25]++;
                                break;
                            case "*Samuel*":
                                heroPlayed[26]++;
                                break;
                            case "*SAW*":
                                heroPlayed[27]++;
                                break;
                            case "*Skaarf*":
                                heroPlayed[28]++;
                                break;
                            case "*Skye*":
                                heroPlayed[29]++;
                                break;
                            case "*Taka*":
                                heroPlayed[30]++;
                                break;
                            default:
                                heroPlayed[31]++;
                                break;
                        }

                    }
                }

            }



            // Find most played hero
            mostPlayedHeroCount = heroPlayed[0];
            for (int y = 0; y < heroPlayed.length; y++) {
                //Log.i("count",y+": "+String.valueOf(heroPlayed[y]));
                if (heroPlayed[y] > mostPlayedHeroCount) {
                    mostPlayedHeroCount = heroPlayed[y];
                    mostPlayedHero = y;
                    Log.i("MostPlayedHero",String.valueOf(y));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    void searchPlayerFromJSON(String name,String playerResultRaw){

        resultRaw = playerResultRaw;

        //Log.i("Result Raw", playerResultRaw);

        try {

            // Get JSON Object
            playerStats = new JSONObject(resultRaw);

            // Get the first player from the array
            JSONArray playerArr = new JSONArray(playerStats.getString("included"));

            for (int i = 0; i < playerArr.length(); i++) {

                //Log.i("name", playerArr.getJSONObject(i).getJSONObject("attributes").getString("name"));
                //Log.i("player", name);
                //Log.i("data", playerResultRaw);

                if (playerArr.getJSONObject(i).getString("type").equals("player")) {

                    //Log.i("loc","player");

                    //check player Name
                    if (name.equals(playerArr.getJSONObject(i).getJSONObject("attributes").getString("name"))) {

                        // Save the value to the variables
                        playerName = playerArr.getJSONObject(i).getJSONObject("attributes").getString("name");
                        playerID = playerArr.getJSONObject(i).getString("id");
                        arrayLocPlayer = i;
                        level = playerArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("level");
                        totalWin = playerArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("wins");
                        totalgames = playerArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("played");
                        lifeTimeGold = playerArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("lifetimeGold");
                        lossStreak = playerArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("lossStreak");
                        winStreak = playerArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("winStreak");
                        totalXP = playerArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("xp");
                        playedRank = playerArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("played_ranked");

                        //Log.i("found", playerName);
                        //Log.i("playerID", playerID);
                        //Log.i("level", level);
                        //Log.i("total wins",totalWin);
                        //Log.i("gold",lifeTimeGold);
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
