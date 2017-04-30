package com.example.darwin.vgbuff;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Match {


    public class UserPlayer {

        public String userID;
        public String userName;

    }

    public class Participant {

        public String participantID;
        public String actor;
        public String[] items;
        public Boolean win;
        public int minionKills;
        public int kills;
        public int deaths;
        public int assists;
        public int gold;
        public int turret;
        public int kraken;
        public String userIDConnectedTo;
        public int skillTier;

        Participant(){

            items = new String[6];

            for (int j = 0; j <6 ; j++) items[j] = "null";
        }

    }

    public class Roster {

        public String rosterID;
        public int gold;
        public int heroKills;
        public int krakenCaptures;
        public String side;
        public int turretKills;
        public int turretRemaining;
        public int acesEarned;
        public String[] participants;

        // constructor for roster
        Roster(){
            participants = new String[3];
        }
    }

    // Raw Data JSON
    String resultRaw;

    // JSONObjects and Arrays
    JSONObject matchesStats;
    JSONObject rosterStats;
    JSONArray rosterStatArr;

    // Arraylist for roster, user and participant
    ArrayList<UserPlayer> userArr;
    ArrayList<Participant> participantArr;
    ArrayList<Roster> rosterArr;

    // Match History Variables
    public String[] rawCreatedAt;
    public int[] duration;
    public String[] rawGameMode;
    public String[] endGameReason;
    public String[] matchID;

    // Team stats 1
    public String[] rosterID1;
    public int[] ace1;
    public int[] gold1;
    public int[] heroKills1;
    public int[] kraken1;
    public String[] side1;
    public int[] turretKills1;
    public int[] turretRemaining1;
    public String[] roster1player1;
    public String[] roster1player2;
    public String[] roster1player3;
    public String[] roster1player1Name;
    public String[] roster1player2Name;
    public String[] roster1player3Name;
    public String[] roster1player1User;
    public String[] roster1player2User;
    public String[] roster1player3User;
    public Boolean[] resultMatch1;
    public UserPlayer[] player11;
    public UserPlayer[] player12;
    public UserPlayer[] player13;
    public Participant[] parti11;
    public Participant[] parti12;
    public Participant[] parti13;
    public UserPlayer[] myPlayer;
    public Participant[] myParticipant;


    // Team stats 2
    public String[] rosterID2;
    public int[] ace2;
    public int[] gold2;
    public int[] heroKills2;
    public int[] kraken2;
    public String[] side2;
    public int[] turretKills2;
    public int[] turretRemaining2;
    public String[] roster2player1;
    public String[] roster2player2;
    public String[] roster2player3;
    public String[] roster2player1Name;
    public String[] roster2player2Name;
    public String[] roster2player3Name;
    public String[] roster2player1User;
    public String[] roster2player2User;
    public String[] roster2player3User;
    public Boolean[] resultMatch2;
    public UserPlayer[] player21;
    public UserPlayer[] player22;
    public UserPlayer[] player23;
    public Participant[] parti21;
    public Participant[] parti22;
    public Participant[] parti23;

    int getParticipantHero (String id){

        // Find participant
        for (int j = 0; j < participantArr.size(); j++){

            // find the hero
            if (participantArr.get(j).participantID.equals(id) ){
                return j;
            }
        }

        return -1;
    }

    int  getUserName (String id){

        // Find user
        for (int j = 0; j < userArr.size(); j++){

            // find the user id
            if (userArr.get(j).userID.equals(id) ){
                return j;
            }
        }

        return -1;
    }

    public void getMatchesDetail(String user, String dataRaw){

        resultRaw = dataRaw;

        try {

            // Get JSON Object
            matchesStats = new JSONObject(resultRaw);

            // Get matches into array
            JSONArray matchesArr = new JSONArray(matchesStats.getString("data"));

            // Get included array
            JSONArray includedArr = new JSONArray(matchesStats.getString("included"));

            // Initialize array
            matchID = new String[matchesArr.length()];
            rawCreatedAt = new String[matchesArr.length()];
            duration = new int[matchesArr.length()];
            rawGameMode = new String[matchesArr.length()];
            endGameReason = new String[matchesArr.length()];
            rosterID1 = new String[matchesArr.length()];
            rosterID2 = new String[matchesArr.length()];
            ace1 =  new int[matchesArr.length()];
            gold1 = new int[matchesArr.length()];
            heroKills1 = new int[matchesArr.length()];
            kraken1 = new int[matchesArr.length()];
            side1 = new String[matchesArr.length()];
            turretKills1 = new int[matchesArr.length()];
            turretRemaining1 = new int[matchesArr.length()];
            roster1player1 = new String[matchesArr.length()];
            roster1player2 = new String[matchesArr.length()];
            roster1player3 = new String[matchesArr.length()];
            roster1player1Name = new String[matchesArr.length()];
            roster1player2Name = new String[matchesArr.length()];
            roster1player3Name = new String[matchesArr.length()];
            ace2 =  new int[matchesArr.length()];
            gold2 = new int[matchesArr.length()];
            heroKills2 = new int[matchesArr.length()];
            kraken2 = new int[matchesArr.length()];
            side2 = new String[matchesArr.length()];
            turretKills2 = new int[matchesArr.length()];
            turretRemaining2 = new int[matchesArr.length()];
            roster2player1 = new String[matchesArr.length()];
            roster2player2 = new String[matchesArr.length()];
            roster2player3 = new String[matchesArr.length()];
            roster2player1Name = new String[matchesArr.length()];
            roster2player2Name = new String[matchesArr.length()];
            roster2player3Name = new String[matchesArr.length()];
            roster1player1User = new String[matchesArr.length()];
            roster1player2User = new String[matchesArr.length()];
            roster1player3User = new String[matchesArr.length()];
            roster2player1User = new String[matchesArr.length()];
            roster2player2User = new String[matchesArr.length()];
            roster2player3User = new String[matchesArr.length()];
            resultMatch1 = new Boolean[matchesArr.length()];
            resultMatch2 = new Boolean[matchesArr.length()];
            player11 = new UserPlayer[matchesArr.length()];
            player12 = new UserPlayer[matchesArr.length()];
            player13 = new UserPlayer[matchesArr.length()];
            parti11 = new Participant[matchesArr.length()];
            parti12 = new Participant[matchesArr.length()];
            parti13 = new Participant[matchesArr.length()];
            player21 = new UserPlayer[matchesArr.length()];
            player22 = new UserPlayer[matchesArr.length()];
            player23 = new UserPlayer[matchesArr.length()];
            parti21 = new Participant[matchesArr.length()];
            parti22 = new Participant[matchesArr.length()];
            parti23 = new Participant[matchesArr.length()];
            myPlayer = new UserPlayer[matchesArr.length()];
            myParticipant = new Participant[matchesArr.length()];

            userArr = new ArrayList<UserPlayer>();
            participantArr = new ArrayList<Participant>();
            rosterArr = new ArrayList<Roster>();

            // variable to count
            int i;

            // Get Roster, Player and Participants
            for ( i = 0; i < includedArr.length(); i++ ){

                try {
                    if (includedArr.getJSONObject(i).getString("type").equals("player")){

                        // Create temp player
                        UserPlayer temp = new UserPlayer();
                        temp.userID = includedArr.getJSONObject(i).getString("id");
                        temp.userName = includedArr.getJSONObject(i).getJSONObject("attributes").getString("name");

                        // Add temp player to array
                        userArr.add(temp);

                        //Log.i("player ID arraylist",userArr.get(userArr.size()-1).userID);
                        //Log.i("player name arraylist",userArr.get(userArr.size()-1).userName);

                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }

            // Get Roster, Player and Participants
            for ( i = 0; i < includedArr.length(); i++ ){

                try {
                    if (includedArr.getJSONObject(i).getString("type").equals("participant")){

                        // Create temp participant
                        Participant temp = new Participant();
                        temp.participantID = includedArr.getJSONObject(i).getString("id");

                        JSONObject tempStat = includedArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats");
                        temp.actor = includedArr.getJSONObject(i).getJSONObject("attributes").getString("actor");
                        temp.assists = tempStat.getInt("assists");
                        temp.deaths = tempStat.getInt("deaths");
                        temp.gold = tempStat.getInt("gold");
                        temp.kills = tempStat.getInt("kills");
                        temp.minionKills = tempStat.getInt("minionKills");
                        temp.kraken = tempStat.getInt("krakenCaptures");
                        temp.turret = tempStat.getInt("turretCaptures");
                        temp.win = tempStat.getBoolean("winner");
                        temp.skillTier = tempStat.getInt("skillTier");
                        temp.userIDConnectedTo = includedArr.getJSONObject(i).getJSONObject("relationships").getJSONObject("player").getJSONObject("data").getString("id");

                        JSONArray itemsPlayerHas = new JSONArray(tempStat.getString("items"));

                        for (int itemcount = 0; itemcount < itemsPlayerHas.length() ; itemcount++){

                            temp.items[itemcount] = itemsPlayerHas.getString(itemcount);

                        }

                        participantArr.add(temp);

                        //Log.i("parti id", participantArr.get(participantArr.size()-1).participantID);
                        //Log.i("parti actor", participantArr.get(participantArr.size()-1).actor);
                        //Log.i("parti connected id", participantArr.get(participantArr.size()-1).userIDConnectedTo);
                        //Log.i("parti item 1", participantArr.get(participantArr.size()-1).items[0]);
                        //Log.i("parti item 2", participantArr.get(participantArr.size()-1).items[1]);
                        //Log.i("parti item 3", participantArr.get(participantArr.size()-1).items[2]);
                        //Log.i("parti item 4", participantArr.get(participantArr.size()-1).items[3]);
                        //Log.i("parti item 5", participantArr.get(participantArr.size()-1).items[4]);
                        //Log.i("parti item 6", participantArr.get(participantArr.size()-1).items[5]);

                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }

            // Get Roster, Player and Participants
            for ( i = 0; i < includedArr.length(); i++ ) {

                try {
                    if (includedArr.getJSONObject(i).getString("type").equals("roster")) {

                        // Create temp roster
                        Roster temp = new Roster();
                        temp.rosterID = includedArr.getJSONObject(i).getString("id");

                        JSONObject tempStat = includedArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats");

                        temp.acesEarned = tempStat.getInt("acesEarned");
                        temp.gold = tempStat.getInt("gold");
                        temp.heroKills = tempStat.getInt("heroKills");
                        temp.side = tempStat.getString("side");
                        temp.krakenCaptures = tempStat.getInt("krakenCaptures");
                        temp.turretKills = tempStat.getInt("turretKills");
                        temp.turretRemaining = tempStat.getInt("turretsRemaining");

                        //Log.i("Roster",temp.rosterID);

                        JSONArray participantsArr = new JSONArray(includedArr.getJSONObject(i).getJSONObject("relationships").getJSONObject("participants").getString("data"));

                        for (int particount = 0; particount < 3; particount++) {

                            // check if the party is less than 3
                            if (participantsArr.length() > particount)
                                temp.participants[particount] = participantsArr.getJSONObject(particount).getString("id");
                            else
                                // add dummy participant
                                temp.participants[particount] = participantsArr.getJSONObject(participantsArr.length()-1).getString("id");

                        }

                        rosterArr.add(temp);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }








            /*
            // Get Roster, Player and Participants
            for ( i = 0; i < includedArr.length(); i++ ){

                if (includedArr.getJSONObject(i).getString("type").equals("player")){

                    // Create temp player
                    UserPlayer temp = new UserPlayer();
                    temp.userID = includedArr.getJSONObject(i).getString("id");
                    temp.userName = includedArr.getJSONObject(i).getJSONObject("attributes").getString("name");

                    // Add temp player to array
                    userArr.add(temp);

                    Log.i("player ID arraylist",userArr.get(userArr.size()-1).userID);
                    Log.i("player name arraylist",userArr.get(userArr.size()-1).userName);

                }

                if (includedArr.getJSONObject(i).getString("type").equals("participant")){

                    // Create temp participant
                    Participant temp = new Participant();
                    temp.participantID = includedArr.getJSONObject(i).getString("id");

                    JSONObject tempStat = includedArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats");
                    temp.actor = includedArr.getJSONObject(i).getJSONObject("attributes").getString("actor");
                    temp.assists = tempStat.getInt("assists");
                    temp.deaths = tempStat.getInt("deaths");
                    temp.gold = tempStat.getInt("gold");
                    temp.kills = tempStat.getInt("kills");
                    temp.minionKills = tempStat.getInt("minionKills");
                    temp.kraken = tempStat.getInt("krakenCaptures");
                    temp.turret = tempStat.getInt("turretCaptures");
                    temp.win = tempStat.getBoolean("winner");
                    temp.userIDConnectedTo = includedArr.getJSONObject(i).getJSONObject("relationships").getJSONObject("player").getJSONObject("data").getString("id");

                    JSONArray itemsPlayerHas = new JSONArray(tempStat.getString("items"));

                    for (int itemcount = 0; itemcount < itemsPlayerHas.length() ; itemcount++){

                        temp.items[itemcount] = itemsPlayerHas.getString(itemcount);

                    }

                   participantArr.add(temp);

                    Log.i("parti id", participantArr.get(participantArr.size()-1).participantID);
                    Log.i("parti actor", participantArr.get(participantArr.size()-1).actor);
                    Log.i("parti connected id", participantArr.get(participantArr.size()-1).userIDConnectedTo);
                    Log.i("parti item 1", participantArr.get(participantArr.size()-1).items[0]);
                    Log.i("parti item 2", participantArr.get(participantArr.size()-1).items[1]);
                    Log.i("parti item 3", participantArr.get(participantArr.size()-1).items[2]);
                    Log.i("parti item 4", participantArr.get(participantArr.size()-1).items[3]);
                    Log.i("parti item 5", participantArr.get(participantArr.size()-1).items[4]);
                    Log.i("parti item 6", participantArr.get(participantArr.size()-1).items[5]);

                }

                if (includedArr.getJSONObject(i).getString("type").equals("roster")){

                    // Create temp roster
                    Roster temp = new Roster();
                    temp.rosterID = includedArr.getJSONObject(i).getString("id");

                    JSONObject tempStat = includedArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats");

                    temp.acesEarned = tempStat.getInt("acesEarned");
                    temp.gold = tempStat.getInt("gold");
                    temp.heroKills = tempStat.getInt("heroKills");
                    temp.side = tempStat.getString("side");
                    temp.krakenCaptures = tempStat.getInt("krakenCaptures");
                    temp.turretKills = tempStat.getInt("turretKills");
                    temp.turretRemaining = tempStat.getInt("turretsRemaining");

                    JSONArray participantsArr = new JSONArray(includedArr.getJSONObject(i).getJSONObject("relationships").getJSONObject("participants").getString("data"));

                    for (int particount = 0; particount < 3 ; particount++){

                        temp.participants[particount] = participantsArr.getJSONObject(particount).getString("id");

                    }

                    rosterArr.add(temp);


                }
            }*/

            // Get all matches in the last 30 days
            for (i = 0; i < matchesArr.length(); i++) {

                // Get match summary
                matchID[i] = matchesArr.getJSONObject(i).getString("id");
                rawCreatedAt[i] = matchesArr.getJSONObject(i).getJSONObject("attributes").getString("createdAt");
                duration[i] = matchesArr.getJSONObject(i).getJSONObject("attributes").getInt("duration");
                rawGameMode[i] = matchesArr.getJSONObject(i).getJSONObject("attributes").getString("gameMode");
                endGameReason[i] = matchesArr.getJSONObject(i).getJSONObject("attributes").getJSONObject("stats").getString("endGameReason");

                // Get array for roster
                JSONArray rostersInTheMatch = new JSONArray(matchesArr.getJSONObject(i).getJSONObject("relationships").getJSONObject("rosters").getString("data"));
                rosterID1[i] = rostersInTheMatch.getJSONObject(0).getString("id");
                rosterID2[i] = rostersInTheMatch.getJSONObject(1).getString("id");

                // Find related roster
                for (int j = 0; j < rosterArr.size(); j++){

                    // Get data for team 1
                    if (rosterArr.get(j).rosterID.equals(rosterID1[i]) ){

                        ace1[i] = rosterArr.get(j).acesEarned;
                        gold1[i] = rosterArr.get(j).gold;
                        heroKills1[i] = rosterArr.get(j).heroKills;
                        kraken1[i] = rosterArr.get(j).krakenCaptures;
                        side1[i] = rosterArr.get(j).side;
                        turretKills1[i] = rosterArr.get(j).turretKills;
                        turretRemaining1[i] = rosterArr.get(j).turretRemaining;
                        roster1player1[i] = rosterArr.get(j).participants[0];
                        roster1player2[i] = rosterArr.get(j).participants[1];
                        roster1player3[i] = rosterArr.get(j).participants[2];

                        // participant pos
                        int participantPos1 = getParticipantHero(roster1player1[i]);
                        int participantPos2 = getParticipantHero(roster1player2[i]);
                        int participantPos3 = getParticipantHero(roster1player3[i]);

                        // get hero name
                        roster1player1Name[i] = participantArr.get(participantPos1).actor;
                        roster1player2Name[i] = participantArr.get(participantPos2).actor;
                        roster1player3Name[i] = participantArr.get(participantPos3).actor;

                        // user pos
                        int userPos1 = getUserName(participantArr.get(participantPos1).userIDConnectedTo);
                        int userPos2 = getUserName(participantArr.get(participantPos2).userIDConnectedTo);
                        int userPos3 = getUserName(participantArr.get(participantPos3).userIDConnectedTo);

                        // get user name
                        roster1player1User[i] = userArr.get(userPos1).userName;
                        roster1player2User[i] = userArr.get(userPos2).userName;
                        roster1player3User[i] = userArr.get(userPos3).userName;
                        resultMatch1[i] = participantArr.get(participantPos1).win;

                        // player and participant
                        player11[i] = userArr.get(userPos1);
                        player12[i] = userArr.get(userPos2);
                        player13[i] = userArr.get(userPos3);
                        parti11[i] = participantArr.get(participantPos1);
                        parti12[i] = participantArr.get(participantPos2);
                        parti13[i] = participantArr.get(participantPos3);


                    }

                    // Get data for team 2
                    if (rosterArr.get(j).rosterID.equals(rosterID2[i]) ){

                        ace2[i] = rosterArr.get(j).acesEarned;
                        gold2[i] = rosterArr.get(j).gold;
                        heroKills2[i] = rosterArr.get(j).heroKills;
                        kraken2[i] = rosterArr.get(j).krakenCaptures;
                        side2[i] = rosterArr.get(j).side;
                        turretKills2[i] = rosterArr.get(j).turretKills;
                        turretRemaining2[i] = rosterArr.get(j).turretRemaining;
                        roster2player1[i] = rosterArr.get(j).participants[0];
                        roster2player2[i] = rosterArr.get(j).participants[1];
                        roster2player3[i] = rosterArr.get(j).participants[2];

                        // participant pos
                        int participantPos1 = getParticipantHero(roster2player1[i]);
                        int participantPos2 = getParticipantHero(roster2player2[i]);
                        int participantPos3 = getParticipantHero(roster2player3[i]);

                        // get hero name
                        roster2player1Name[i] = participantArr.get(participantPos1).actor;
                        roster2player2Name[i] = participantArr.get(participantPos2).actor;
                        roster2player3Name[i] = participantArr.get(participantPos3).actor;

                        // user pos
                        int userPos1 = getUserName(participantArr.get(participantPos1).userIDConnectedTo);
                        int userPos2 = getUserName(participantArr.get(participantPos2).userIDConnectedTo);
                        int userPos3 = getUserName(participantArr.get(participantPos3).userIDConnectedTo);

                        // get user name
                        roster2player1User[i] = userArr.get(userPos1).userName;
                        roster2player2User[i] = userArr.get(userPos2).userName;
                        roster2player3User[i] = userArr.get(userPos3).userName;
                        resultMatch2[i] = participantArr.get(participantPos1).win;

                        // player and participant
                        player21[i] = userArr.get(userPos1);
                        player22[i] = userArr.get(userPos2);
                        player23[i] = userArr.get(userPos3);
                        parti21[i] = participantArr.get(participantPos1);
                        parti22[i] = participantArr.get(participantPos2);
                        parti23[i] = participantArr.get(participantPos3);

                    }

                }

            }

            for (int z = 0; z < matchesArr.length(); z++){

                // Saving stat for the my user
                if (player11[z].userName.equals(user)){
                    myPlayer[z]=player11[z];
                    myParticipant[z]=parti11[z];
                } else if (player12[z].userName.equals(user)){
                    myPlayer[z]=player12[z];
                    myParticipant[z]=parti12[z];
                } else if (player13[z].userName.equals(user)){
                    myPlayer[z]=player13[z];
                    myParticipant[z]=parti13[z];
                } else if (player21[z].userName.equals(user)){
                    myPlayer[z]=player21[z];
                    myParticipant[z]=parti21[z];
                } else if (player22[z].userName.equals(user)){
                    myPlayer[z]=player22[z];
                    myParticipant[z]=parti22[z];
                } else
                {
                    myPlayer[z]=player23[z];
                    myParticipant[z]=parti23[z];
                }
            }

            //Log.i("Match ID",matchID[0]);
            //Log.i("date",rawCreatedAt[0]);
            //Log.i("duration", String.valueOf(duration[0]));
//            Log.i("Mode", rawGameMode[0]);
//            Log.i("Reason",endGameReason[0]);
//            Log.i("roster1",rosterID1[0]);
//            Log.i("roster1Player1",roster1player1[0]);
//            Log.i("roster1Player1Name",parti11[0].actor);
//            Log.i("roster1Player1User",player11[0].userName);
//            Log.i("roster1Player2",roster1player2[0]);
//            Log.i("roster1Player2Name",parti12[0].actor);
//            Log.i("roster1Player2User",player12[0].userName);
//            Log.i("roster1Player3",roster1player3[0]);
//            Log.i("roster1Player3Name",parti13[0].actor);
//            Log.i("roster1Player3User",player13[0].userName);
//            Log.i("roster1side",side1[0]);
//            Log.i("Win",Boolean.toString(resultMatch1[0]));
//            Log.i("roster2",rosterID2[0]);
//            Log.i("roster2Player1",roster2player1[0]);
//            Log.i("roster2Player1Name",roster2player1Name[0]);
//            Log.i("roster2Player1User",roster2player1User[0]);
//            Log.i("roster2Player2",roster2player2[0]);
//            Log.i("roster2Player2Name",roster2player2Name[0]);
//            Log.i("roster2Player2User",roster2player2User[0]);
//            Log.i("roster2Player3",roster2player3[0]);
//            Log.i("roster2Player3Name",roster2player3Name[0]);
//            Log.i("roster2Player3User",roster2player3User[0]);
//            Log.i("roster2side",side2[0]);
//            Log.i("Win",Boolean.toString(resultMatch2[0]));
//
//            Log.i("My Player",myPlayer[5].userName);
//            Log.i("My Hero",myParticipant[5].actor);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
