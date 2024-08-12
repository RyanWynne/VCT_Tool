/*
	Need to:
		- Rework intlRating
		- Check aggregates for balancing issues (intl rating needs more influences in some games (CN v ?) and less in others (FNC v GENG)
		- Incorporate a 'last 5-10 matches' bias
		- Make data dynamic in champions functions, i.e. Abyss w/r changes depending on a team's prior results
		- Randomize whether a team bans/picks their worst/best opponents best/worst
		- State that the same team cannot play themselves
		- Reword the required input for champions functions
		- Look for opportunities to reduce redundancies with functions
		- displayData not showing internationalRating
		- Error handling
		- Make champions groups non-deterministic
 */

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.util.Random;
 import java.util.Scanner;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Collection;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.Iterator;
 import java.util.List;
 public class VCT_Main {
     
     public static Scanner input = new Scanner(System.in);
     
     public static ArrayList<Team>[] seeding(ArrayList<Team> teams) {
         //need to review how it works + is deterministic (?!?!?!?!?!?)
         List<Integer> regions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
         List<Integer> seeds = new ArrayList<>(Arrays.asList(0, 1, 2, 3));   
         Collections.shuffle(regions);
         Collections.shuffle(seeds);
         ArrayList<Team>[] groups = new ArrayList[4];
         for (int i = 0; i < 4; i++) {
             groups[i] = new ArrayList<>();
         }
         for (int i = 0; i < teams.size(); i++) {
             int seed = i / 4;          // Determine the seed level (0, 1, 2, 3)
             int region = i % 4;        // Determine the region (0, 1, 2, 3)
             int groupIndex = (seed + region) % 4;  // Calculate the correct group for this team?????????????
             groups[groupIndex].add(teams.get(i));
         }
         
         // Create a list of indices for sorting by seed
         for (ArrayList<Team> group : groups) {
             // Sort teams in the group by their original input order (i.e., seed)
             group.sort(Comparator.comparingInt(team -> teams.indexOf(team)));
         }
 
         int counter = 0;
         for (int i = 0; i < 4; i++) {
             switch(counter) {
             case 0:
                 System.out.print("Group A:\n");
                 break;
             case 1:
                 System.out.print("Group B:\n");
                 break;
             case 2:
                 System.out.print("Group C:\n");
                 break;
             case 3:
                 System.out.print("Group D:\n");
                 break;
             }            
             for (Team team : groups[i]) {
                 System.out.println(team.name + " (" + team.region + ")");
             }
             System.out.println();
             counter++;
         }
         return groups;
     }
     
     public static ArrayList<Team>[] seedingSilent(ArrayList<Team> teams) {
         //need to review how it works + is deterministic (?!?!?!?!?!?)
         List<Integer> regions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
         List<Integer> seeds = new ArrayList<>(Arrays.asList(0, 1, 2, 3));   
         Collections.shuffle(regions);
         Collections.shuffle(seeds);
         ArrayList<Team>[] groups = new ArrayList[4];
         for (int i = 0; i < 4; i++) {
             groups[i] = new ArrayList<>();
         }
         for (int i = 0; i < teams.size(); i++) {
             int seed = i / 4;          // Determine the seed level (0, 1, 2, 3)
             int region = i % 4;        // Determine the region (0, 1, 2, 3)
             int groupIndex = (seed + region) % 4;  // Calculate the correct group for this team?????????????
             groups[groupIndex].add(teams.get(i));
         }
         
         // Create a list of indices for sorting by seed
         for (ArrayList<Team> group : groups) {
             // Sort teams in the group by their original input order (i.e., seed)
             group.sort(Comparator.comparingInt(team -> teams.indexOf(team)));
         }
         return groups;
     }
     
     public static ArrayList<Team> groupStage(ArrayList<Team>[] groups) {
         ArrayList<Team> qualifiedTeams = new ArrayList<>();  
         int counter = 0;
         for (ArrayList<Team> group : groups) {
             switch(counter) {
             case 0:
                 System.out.print("Group A:\n");
                 break;
             case 1:
                 System.out.print("Group B:\n");
                 break;
             case 2:
                 System.out.print("Group C:\n");
                 break;
             case 3:
                 System.out.print("Group D:\n");
                 break;
             }
             Team seed1 = group.get(0);
             Team seed2 = group.get(1);
             Team seed3 = group.get(2);
             Team seed4 = group.get(3);
 
             // Simulate all group stage matches
             Team winnerM1 = Bo3GameTourn(seed1, seed4);
             Team winnerM2 = Bo3GameTourn(seed2, seed3);
             Team loserM1 = (winnerM1 == seed1) ? seed4 : seed1;
             Team loserM2 = (winnerM2 == seed2) ? seed3 : seed2;
 
             Team winnerM3 = Bo3GameTourn(winnerM1, winnerM2);
             Team loserM3 = (winnerM3 == winnerM1) ? winnerM2 : winnerM1;
             
             Team winnerM4 = Bo3GameTourn(loserM1, loserM2);
             Team loserM4 = (winnerM4 == loserM1) ? loserM2 : loserM1;
             
             Team winnerM5 = Bo3GameTourn(winnerM4, loserM3);
 
             // GSEED#1 and GSEED#2 from the group
             Team gSeed1 = winnerM3;
             Team gSeed2 = winnerM5;
 
             qualifiedTeams.add(gSeed1);
             qualifiedTeams.add(gSeed2);
             
             System.out.println(gSeed1.name + " and " + gSeed2.name + " progress\n");
             counter++;
 
             System.out.println(seed1.getName()+"_____\r\n"
                             + "        \\_"+winnerM1.getName()+"____\r\n"
                             + seed4.getName()+"_____/        \\\r\n"
                             + "                  \\_"+winnerM3.getName()+"\r\n"
                             + seed2.getName()+"_____          /\r\n"
                             + "        \\_"+winnerM2.getName()+"____/\r\n"
                             + seed3.getName()+"_____/\r\n"
                             + "\r\n"
                             + loserM1.getName()+"_____  "+loserM3.getName()+" ______"+winnerM5.getName()+"\r\n"
                             + "        \\_"+winnerM4.getName()+"\r\n"
                             + loserM2.getName()+ "_____/\n");
         }
         return qualifiedTeams;
     }
 
     public static ArrayList<Team> groupStageSilent(ArrayList<Team>[] groups) {
         ArrayList<Team> qualifiedTeams = new ArrayList<>();  
         for (ArrayList<Team> group : groups) {
             Team seed1 = group.get(0);
             Team seed2 = group.get(1);
             Team seed3 = group.get(2);
             Team seed4 = group.get(3);
 
             // Simulate all group stage matches
             Team winnerM1 = Bo3GameTournSilent(seed1, seed4);
             Team winnerM2 = Bo3GameTournSilent(seed2, seed3);
             Team loserM1 = (winnerM1 == seed1) ? seed4 : seed1;
             Team loserM2 = (winnerM2 == seed2) ? seed3 : seed2;
 
             Team winnerM3 = Bo3GameTournSilent(winnerM1, winnerM2);
             Team loserM3 = (winnerM3 == winnerM1) ? winnerM2 : winnerM1;
             
             Team winnerM4 = Bo3GameTournSilent(loserM1, loserM2);
             Team loserM4 = (winnerM4 == loserM1) ? loserM2 : loserM1;
             
             Team winnerM5 = Bo3GameTournSilent(winnerM4, loserM3);
 
             // GSEED#1 and GSEED#2 from the group
             Team gSeed1 = winnerM3;
             Team gSeed2 = winnerM5;
 
             qualifiedTeams.add(gSeed1);
             qualifiedTeams.add(gSeed2);
         }
         return qualifiedTeams;
     }
     
     public static ArrayList<Team> knockoutPairing(ArrayList<Team> qualified) {
         ArrayList<Team> pairings = new ArrayList<>();
         // Separate the teams into seed1 and seed2
         ArrayList<Team> seed1 = new ArrayList<>();
         ArrayList<Team> seed2 = new ArrayList<>();
         for (int i =0; i<qualified.size(); i++) {
             if (i % 2 == 0) {
                 seed1.add(qualified.get(i));
             } else {
                 seed2.add(qualified.get(i));
             }
         }
 
         // Random object for generating random indices
         Random rand = new Random();
         int successfulPairs = 0;
         // Pairing 1st seeds with random 2nd seeds
         for(int i=0; successfulPairs < 4; i++) {
             int counter = 0;
             ArrayList<Team> seed2Copy = seed2;
             if(i>4 || counter>10) {
                 i = 0;
                 pairings.removeAll(pairings);
                 seed2 = seed2Copy;
             }
             Team firstSeed = seed1.get(i);
             Team secondSeed = null;
             boolean validPairFound = false;
             while (!validPairFound) {
                 if(counter>10) {
                     break;
                 }
                 int oddIndex = rand.nextInt(seed2.size());
                 secondSeed = seed2.get(oddIndex);
 
                 // Check if the 2nd seed is not the immediate next team after the 1st seed in the list
                 int evenIndex = qualified.indexOf(firstSeed);
                 if (evenIndex + 1 < qualified.size() && qualified.get(evenIndex + 1) == secondSeed) {
                     counter++;
                     continue; // If invalid, try another 2nd seed
                 }
                 validPairFound = true;
             }
             pairings.add(firstSeed);
             pairings.add(secondSeed);
             successfulPairs++;
             seed2.remove(secondSeed);
         }
         return pairings;
     }
     
     public static Team knockoutStage(ArrayList<Team> qualified) {
         qualified = knockoutPairing(qualified);
         
         Team groupAOne = qualified.get(0);
         Team groupAOneOpp = qualified.get(1);
         Team groupBOne = qualified.get(2);
         Team groupBOneOpp = qualified.get(3);
         Team groupCOne = qualified.get(4);
         Team groupCOneOpp = qualified.get(5);
         Team groupDOne = qualified.get(6);
         Team groupDOneOpp = qualified.get(7);
         
         //Upper quarter finals
         System.out.println("Upper quarterfinals:");
         Team winnerM1 = Bo3GameTourn(groupAOne, groupAOneOpp);
         Team winnerM2 = Bo3GameTourn(groupBOne, groupBOneOpp);
         Team winnerM3 = Bo3GameTourn(groupCOne, groupCOneOpp);
         Team winnerM4 = Bo3GameTourn(groupDOne, groupDOneOpp);
         Team loserM1 = (winnerM1 == groupAOne) ? groupAOneOpp : groupAOne;
         Team loserM2 = (winnerM2 == groupBOne) ? groupBOneOpp : groupBOne;
         Team loserM3 = (winnerM3 == groupCOne) ? groupCOneOpp : groupCOne;
         Team loserM4 = (winnerM4 == groupDOne) ? groupDOneOpp : groupDOne;
         
         //Lower Round 1
         System.out.println("\nLower round 1:");
         Team winnerM5 = Bo3GameTourn(loserM1, loserM2);
         Team winnerM6 = Bo3GameTourn(loserM3, loserM4);
         Team loserM5 = (winnerM5 == loserM1) ? loserM2 : loserM1;
         Team loserM6 = (winnerM6 == loserM3) ? loserM4 : loserM3;
         
         //Upper semi finals
         System.out.println("\nSemi finals:");
         Team winnerM7 = Bo3GameTourn(winnerM1, winnerM2);
         Team winnerM8 = Bo3GameTourn(winnerM3, winnerM4);
         Team loserM7 = (winnerM7 == winnerM1) ? winnerM2 : winnerM1;
         Team loserM8 = (winnerM8 == winnerM3) ? winnerM4 : winnerM3;
 
         //Lower Round 2
         System.out.println("\nLower round 2:");
         Team winnerM9 = Bo3GameTourn(winnerM5, loserM7);
         Team loserM9 = (winnerM9 == winnerM5) ? loserM7 : winnerM5;
         Team winnerM10 = Bo3GameTourn(winnerM6, loserM8);
         Team loserM10 = (winnerM10 == winnerM6) ? loserM8 : winnerM6;
 
         //Upper final
         System.out.println("\nUpper finals:");
         Team winnerM11 = Bo3GameTourn(winnerM7, winnerM8);
         Team loserM11 = (winnerM11 == winnerM7) ? winnerM8 : winnerM7;
 
         //Lower Round 3
         System.out.println("\nLower round 3:");
         Team winnerM12 = Bo3GameTourn(winnerM9, winnerM10);
         Team loserM12 = (winnerM12 == winnerM9) ? winnerM10 : winnerM9;
 
         //Lower final
         System.out.println("\nLower final:");
         Team winnerM13 = Bo5GameTourn(winnerM12, loserM11);
 
         //Grand final
         System.out.println("\nGrand finals:");
         Team champion = Bo5GameTourn(winnerM11, winnerM13);
         System.out.print("\nUpper Bracket\r\n"
                 + groupAOne.getName()+"______\r\n"
                 + "         \\_"+winnerM1.getName()+"_____\r\n"
                 + groupAOneOpp.getName()+"______/         \\\r\n"
                 + "                    \\_"+winnerM7.getName()+"__\r\n"
                 + groupBOne.getName()+"______           /      \\\r\n"
                 + "         \\_"+winnerM2.getName()+"_____/        \\\r\n"
                 + groupBOneOpp.getName()+"______/                   \\\r\n"
                 + "                              \\____________"+winnerM11.getName()+"___\r\n"
                 + groupCOne.getName()+"______                     /                  \\\r\n"
                 + "         \\_"+winnerM3.getName()+"____           /                    \\\r\n"
                 + groupCOneOpp.getName()+"______/        \\         /                      \\\r\n"
                 + "                   \\_"+winnerM8.getName()+"___/                        \\\r\n"
                 + groupDOne.getName()+"______          /                                 \\\r\n"
                 + "         \\_"+winnerM4.getName()+"____/                                   \\_________"+champion.getName()+"\r\n"
                 + groupDOneOpp.getName()+"______/                                            /\r\n"
                 + "                                                     /\r\n"
                 + "Lower Bracket                                       /\r\n"
                 + loserM1.getName()+"______  "+loserM7.getName()+"                                     /\r\n"
                 + "         \\_"+winnerM5.getName()+"_______                             /\r\n"
                 + loserM2.getName()+"______/           \\_"+winnerM9.getName()+"___                    /\r\n"
                 + "                             \\       "+loserM11.getName()+"___"+winnerM13.getName()+"_/\r\n"
                 + "                              \\______"+winnerM12.getName()+"\r\n"
                 + "                              /\r\n"
                 + "                      _"+winnerM10.getName()+"___/\r\n"
                 + loserM3.getName()+"______  "+loserM8.getName()+"_______/          \r\n"
                 + "         \\_"+winnerM6.getName()+"    \r\n"
                 + loserM4.getName()+"______/\r\n");
                
         System.out.println("\nYour VCT Champions winner is " + champion.getName());
         
         return champion;
     }
 
     public static Team knockoutStageSilent(ArrayList<Team> qualified) {
         qualified = knockoutPairing(qualified);
         
         Team groupAOne = qualified.get(0);
         Team groupAOneOpp = qualified.get(1);
         Team groupBOne = qualified.get(2);
         Team groupBOneOpp = qualified.get(3);
         Team groupCOne = qualified.get(4);
         Team groupCOneOpp = qualified.get(5);
         Team groupDOne = qualified.get(6);
         Team groupDOneOpp = qualified.get(7);
         
         //Upper quarter finals
         Team winnerM1 = Bo3GameTournSilent(groupAOne, groupAOneOpp);
         Team winnerM2 = Bo3GameTournSilent(groupBOne, groupBOneOpp);
         Team winnerM3 = Bo3GameTournSilent(groupCOne, groupCOneOpp);
         Team winnerM4 = Bo3GameTournSilent(groupDOne, groupDOneOpp);
         Team loserM1 = (winnerM1 == groupAOne) ? groupAOneOpp : groupAOne;
         Team loserM2 = (winnerM2 == groupBOne) ? groupBOneOpp : groupBOne;
         Team loserM3 = (winnerM3 == groupCOne) ? groupCOneOpp : groupCOne;
         Team loserM4 = (winnerM4 == groupDOne) ? groupDOneOpp : groupDOne;
         
         //Lower Round 1
         Team winnerM5 = Bo3GameTournSilent(loserM1, loserM2);
         Team winnerM6 = Bo3GameTournSilent(loserM3, loserM4);
         Team loserM5 = (winnerM5 == loserM1) ? loserM2 : loserM1;
         Team loserM6 = (winnerM6 == loserM3) ? loserM4 : loserM3;
         
         //Upper semi finals
         Team winnerM7 = Bo3GameTournSilent(winnerM1, winnerM2);
         Team winnerM8 = Bo3GameTournSilent(winnerM3, winnerM4);
         Team loserM7 = (winnerM7 == winnerM1) ? winnerM2 : winnerM1;
         Team loserM8 = (winnerM8 == winnerM3) ? winnerM4 : winnerM3;
 
         //Lower Round 2
         Team winnerM9 = Bo3GameTournSilent(loserM5, loserM6);
         Team loserM9 = (winnerM9 == loserM5) ? loserM6 : loserM5;
 
         //Upper final
         Team winnerM10 = Bo3GameTournSilent(winnerM7, winnerM8);
 
         //Lower Round 3
         Team winnerM11 = Bo3GameTournSilent(winnerM9, loserM7);
         Team loserM11 = (winnerM11 == winnerM9) ? loserM7 : winnerM9;
 
         //Lower final
         Team winnerM12 = Bo5GameTournSilent(winnerM11, loserM8);
 
         //Grand final
         Team champion = Bo5GameTournSilent(winnerM10, winnerM12);
         
         return champion;
       }
     
     public static void champions(ArrayList<Team> teams) {
         ArrayList<Team>[] seededGroups = seeding(teams);
         ArrayList<Team> knockoutTeams = groupStage(seededGroups);
         knockoutStage(knockoutTeams);
     }
     
     public static void championsAgg(ArrayList<Team> teams) {
         for(int i = 0; i<1000; i++) {
             ArrayList<Team>[] seededGroups = seedingSilent(teams);
             ArrayList<Team> knockoutTeams = groupStageSilent(seededGroups);
             Team champion = knockoutStageSilent(knockoutTeams);
             if(champion.getName().equalsIgnoreCase(teams.get(0).name))
                 teams.get(0).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(1).name))
                 teams.get(1).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(2).name))
                 teams.get(2).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(3).name))
                 teams.get(3).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(4).name))
                 teams.get(4).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(5).name))
                 teams.get(5).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(6).name))
                 teams.get(6).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(7).name))
                 teams.get(7).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(8).name))
                 teams.get(8).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(9).name))
                 teams.get(9).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(10).name))
                 teams.get(10).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(11).name))
                 teams.get(11).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(12).name))
                 teams.get(12).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(13).name))
                 teams.get(13).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(14).name))
                 teams.get(14).champsWon++;
             else if(champion.getName().equalsIgnoreCase(teams.get(15).name))
                 teams.get(15).champsWon++;
         }
         Collections.sort(teams, Team.champsOrder);
         for(Team t:teams) {
             System.out.println(t.getName() + " won " + ((float)t.champsWon*100/1000) + "% of the time.");
         }
     }
     
     public static float[] probabilities(float teamA, float teamB) {
         float [] percentages = new float[2];
         float Sa = teamA/(1-teamA);
         float Sb = teamB/(1-teamB);
         float total = Sa + Sb;
         float Pa = Sa/total;
         float Pb = Sb/total;
         if(Pa<0.2) {
             Pb = (float) (Pb-0.2);
             Pa = (float) 0.2;
         }
         if(Pb<0.2) {
             Pa = (float) (Pa-0.2);
             Pb = (float) 0.2;
         }
         percentages[0] = Pa;
         percentages[1] = Pb;
         return percentages;
     }
     
     public static void playMap(Map map, Team teamA, Team teamB) {
         teamA.resetScore();
         teamB.resetScore();
         System.out.print("Current map: " + map.name.toUpperCase() + "\n");
         int totalRounds = 0;
         int teamAEco = 0;
         int teamBEco = 0;
         float[] teamARates = teamA.mapDetails(map.name);
         float[] teamBRates = teamB.mapDetails(map.name);
         float teamAAttPerc = probabilities(teamARates[1],teamBRates[1])[0];
         float teamBAttPerc = probabilities(teamARates[1],teamBRates[1])[1];
         if(teamA.region.equalsIgnoreCase(teamB.region)) {
             if(teamA.rating>teamB.rating+70) {
                 teamAAttPerc += 0.1;
                 if(teamAAttPerc>0.75)
                     teamAAttPerc = (float) 0.75;
                 teamBAttPerc -= 0.1;
                 if(teamBAttPerc<0.25)
                     teamBAttPerc = (float) 0.25;
             }
             else if(teamB.rating>teamA.rating+70){
                 teamAAttPerc -= 0.1;
                 if(teamAAttPerc<0.25)
                     teamAAttPerc = (float) 0.25;
                 teamBAttPerc += 0.1;
                 if(teamBAttPerc<0.75)
                     teamBAttPerc = (float) 0.75;
             }
         }
         else {
             if(teamA.intlRating>teamB.intlRating+80) {
                 teamAAttPerc += 0.1;
                 if(teamAAttPerc>0.75)
                     teamAAttPerc = (float) 0.75;
                 teamBAttPerc -= 0.1;
                 if(teamBAttPerc<0.25)
                     teamBAttPerc = (float) 0.25;
             }
             else if(teamB.intlRating>teamA.intlRating+80){
                 teamAAttPerc -= 0.1;
                 if(teamAAttPerc<0.25)
                     teamAAttPerc = (float) 0.25;
                 teamBAttPerc += 0.1;
                 if(teamBAttPerc<0.75)
                     teamBAttPerc = (float) 0.75;
             }
         }
         boolean teamAWin = true;
         Random rand = new Random();
         while(!((teamA.score >= 13 && teamA.score >= teamB.score + 2) || 
                  (teamB.score >= 13 && teamB.score >= teamA.score + 2))) {
             System.out.print(teamA.name + " " + teamA.score + ":" + teamB.score + " " +teamB.name + "\n");
             float currentTeamAAttPerc = teamAAttPerc;
             float currentTeamBAttPerc = teamBAttPerc;
             //1st pistol
             if(totalRounds==0) {
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     float outcome = rand.nextFloat();
                     if(outcome>teamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco<4)
                         teamAEco++;
                     teamA.winRound();
                     if(teamBEco!=0)
                         teamBEco--;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco<4)
                         teamBEco++;
                     teamB.winRound();
                     if(teamAEco!=0)
                         teamAEco--;
                     totalRounds++;
                 }
             }
             //round 2
             else if (totalRounds==1) {
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     if(teamBEco == 0) {
                         currentTeamAAttPerc += 0.5;
                         if(currentTeamAAttPerc > 0.9)
                             currentTeamAAttPerc = (float) 0.9;
                     }
                     else if (teamAEco == 0) {
                         currentTeamAAttPerc -= 0.5;
                         if(currentTeamAAttPerc > 0.1)
                             currentTeamAAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>currentTeamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     if(teamAEco == 0) {
                         currentTeamBAttPerc += 0.5;
                         if(currentTeamBAttPerc > 0.9)
                             currentTeamBAttPerc = (float) 0.9;
                     }
                     else if (teamBEco == 0) {
                         currentTeamBAttPerc -= 0.5;
                         if(currentTeamBAttPerc > 0.1)
                             currentTeamBAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco==0)
                         teamAEco += 2;
                     else
                         teamAEco++;
                     teamA.winRound();
                     if(teamBEco==0)
                         teamBEco++;
                     else
                         teamBEco--;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco==0)
                         teamBEco += 2;
                     teamB.winRound();
                     if(teamAEco==0)
                         teamAEco++;
                     else
                         teamAEco--;
                     totalRounds++;
                 }
             }
             //2nd pistol
             else if(totalRounds==12) {
                 //swap halves
                 map.setAttSide(map.defenders);
                 map.setDefSide(map.attackers);
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     float outcome = rand.nextFloat();
                     if(outcome>teamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco<4)
                         teamAEco++;
                     teamA.winRound();
                     if(teamBEco!=0)
                         teamBEco--;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco<4)
                         teamBEco++;
                     teamB.winRound();
                     if(teamAEco!=0)
                         teamAEco--;
                     totalRounds++;
                 }
             }
             //round 13
             else if (totalRounds==13) {
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     if(teamBEco == 0) {
                         currentTeamAAttPerc += 0.5;
                         if(currentTeamAAttPerc > 0.9)
                             currentTeamAAttPerc = (float) 0.9;
                     }
                     else if (teamAEco == 0) {
                         currentTeamAAttPerc -= 0.5;
                         if(currentTeamAAttPerc > 0.1)
                             currentTeamAAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>currentTeamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     if(teamAEco == 0) {
                         currentTeamBAttPerc += 0.5;
                         if(currentTeamBAttPerc > 0.9)
                             currentTeamBAttPerc = (float) 0.9;
                     }
                     else if (teamBEco == 0) {
                         currentTeamBAttPerc -= 0.5;
                         if(currentTeamBAttPerc > 0.1)
                             currentTeamBAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco==0)
                         teamAEco += 2;
                     else
                         teamAEco++;
                     teamA.winRound();
                     if(teamBEco==0)
                         teamBEco++;
                     else
                         teamBEco--;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco==0)
                         teamBEco += 2;
                     teamB.winRound();
                     if(teamAEco==0)
                         teamAEco++;
                     else
                         teamAEco--;
                     totalRounds++;
                 }
             }
             //regular round
             else {
                 //insert eco checker, no % lower than .10 allowed
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     if(teamBEco == 0) {
                         currentTeamAAttPerc += 0.5;
                         if(currentTeamAAttPerc > 0.9)
                             currentTeamAAttPerc = (float) 0.9;
                     }
                     else if (teamAEco == 0) {
                         currentTeamAAttPerc -= 0.5;
                         if(currentTeamAAttPerc > 0.1)
                             currentTeamAAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>currentTeamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     if(teamAEco == 0) {
                         currentTeamBAttPerc += 0.5;
                         if(currentTeamBAttPerc > 0.9)
                             currentTeamBAttPerc = (float) 0.9;
                     }
                     else if (teamBEco == 0) {
                         currentTeamBAttPerc -= 0.5;
                         if(currentTeamBAttPerc > 0.1)
                             currentTeamBAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco<4)
                         teamAEco ++;
                     teamA.winRound();
                     if(teamBEco!=0)
                         teamBEco--;
                     else
                         teamBEco++;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco<4)
                         teamBEco ++;
                     teamB.winRound();
                     if(teamAEco!=0)
                         teamAEco--;
                     else
                         teamAEco++;
                     totalRounds++;
                 }
             }
         }
         System.out.print(teamA.name + " " + teamA.score + ":" + teamB.score + " " +teamB.name + "\n");
         if(teamA.score>teamB.score)
             teamA.mapsWon++;		
         else
             teamB.mapsWon++;
     }
 
     public static void playMapSilent(Map map, Team teamA, Team teamB) {
         teamA.resetScore();
         teamB.resetScore();
         int totalRounds = 0;
         int teamAEco = 0;
         int teamBEco = 0;
         float[] teamARates = teamA.mapDetails(map.name);
         float[] teamBRates = teamB.mapDetails(map.name);
         float teamAAttPerc = probabilities(teamARates[1],teamBRates[1])[0];
         float teamBAttPerc = probabilities(teamARates[1],teamBRates[1])[1];
         if(teamA.region.equalsIgnoreCase(teamB.region)) {
             if(teamA.rating>teamB.rating+70) {
                 teamAAttPerc += 0.1;
                 if(teamAAttPerc>0.75)
                     teamAAttPerc = (float) 0.75;
                 teamBAttPerc -= 0.1;
                 if(teamBAttPerc<0.25)
                     teamBAttPerc = (float) 0.25;
             }
             else if(teamB.rating>teamA.rating+70){
                 teamAAttPerc -= 0.1;
                 if(teamAAttPerc<0.25)
                     teamAAttPerc = (float) 0.25;
                 teamBAttPerc += 0.1;
                 if(teamBAttPerc<0.75)
                     teamBAttPerc = (float) 0.75;
             }
         }
         else {
             if(teamA.intlRating>teamB.intlRating+70) {
                 teamAAttPerc += 0.1;
                 if(teamAAttPerc>0.75)
                     teamAAttPerc = (float) 0.75;
                 teamBAttPerc -= 0.1;
                 if(teamBAttPerc<0.25)
                     teamBAttPerc = (float) 0.25;
             }
             else if(teamB.intlRating>teamA.intlRating+70){
                 teamAAttPerc -= 0.1;
                 if(teamAAttPerc<0.25)
                     teamAAttPerc = (float) 0.25;
                 teamBAttPerc += 0.1;
                 if(teamBAttPerc<0.75)
                     teamBAttPerc = (float) 0.75;
             }
         }
         boolean teamAWin = true;
         Random rand = new Random();
         while(!((teamA.score >= 13 && teamA.score >= teamB.score + 2) || 
                  (teamB.score >= 13 && teamB.score >= teamA.score + 2))) {
             float currentTeamAAttPerc = teamAAttPerc;
             float currentTeamBAttPerc = teamBAttPerc;
             //1st pistol
             if(totalRounds==0) {
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     float outcome = rand.nextFloat();
                     if(outcome>teamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco<4)
                         teamAEco++;
                     teamA.winRound();
                     if(teamBEco!=0)
                         teamBEco--;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco<4)
                         teamBEco++;
                     teamB.winRound();
                     if(teamAEco!=0)
                         teamAEco--;
                     totalRounds++;
                 }
             }
             //round 2
             else if (totalRounds==1) {
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     if(teamBEco == 0) {
                         currentTeamAAttPerc += 0.5;
                         if(currentTeamAAttPerc > 0.9)
                             currentTeamAAttPerc = (float) 0.9;
                     }
                     else if (teamAEco == 0) {
                         currentTeamAAttPerc -= 0.5;
                         if(currentTeamAAttPerc > 0.1)
                             currentTeamAAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>currentTeamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     if(teamAEco == 0) {
                         currentTeamBAttPerc += 0.5;
                         if(currentTeamBAttPerc > 0.9)
                             currentTeamBAttPerc = (float) 0.9;
                     }
                     else if (teamBEco == 0) {
                         currentTeamBAttPerc -= 0.5;
                         if(currentTeamBAttPerc > 0.1)
                             currentTeamBAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco==0)
                         teamAEco += 2;
                     else
                         teamAEco++;
                     teamA.winRound();
                     if(teamBEco==0)
                         teamBEco++;
                     else
                         teamBEco--;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco==0)
                         teamBEco += 2;
                     teamB.winRound();
                     if(teamAEco==0)
                         teamAEco++;
                     else
                         teamAEco--;
                     totalRounds++;
                 }
             }
             //2nd pistol
             else if(totalRounds==12) {
                 //swap halves
                 map.setAttSide(map.defenders);
                 map.setDefSide(map.attackers);
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     float outcome = rand.nextFloat();
                     if(outcome>teamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco<4)
                         teamAEco++;
                     teamA.winRound();
                     if(teamBEco!=0)
                         teamBEco--;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco<4)
                         teamBEco++;
                     teamB.winRound();
                     if(teamAEco!=0)
                         teamAEco--;
                     totalRounds++;
                 }
             }
             //round 13
             else if (totalRounds==13) {
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     if(teamBEco == 0) {
                         currentTeamAAttPerc += 0.5;
                         if(currentTeamAAttPerc > 0.9)
                             currentTeamAAttPerc = (float) 0.9;
                     }
                     else if (teamAEco == 0) {
                         currentTeamAAttPerc -= 0.5;
                         if(currentTeamAAttPerc > 0.1)
                             currentTeamAAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>currentTeamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     if(teamAEco == 0) {
                         currentTeamBAttPerc += 0.5;
                         if(currentTeamBAttPerc > 0.9)
                             currentTeamBAttPerc = (float) 0.9;
                     }
                     else if (teamBEco == 0) {
                         currentTeamBAttPerc -= 0.5;
                         if(currentTeamBAttPerc > 0.1)
                             currentTeamBAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco==0)
                         teamAEco += 2;
                     else
                         teamAEco++;
                     teamA.winRound();
                     if(teamBEco==0)
                         teamBEco++;
                     else
                         teamBEco--;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco==0)
                         teamBEco += 2;
                     teamB.winRound();
                     if(teamAEco==0)
                         teamAEco++;
                     else
                         teamAEco--;
                     totalRounds++;
                 }
             }
             //regular round
             else {
                 //insert eco checker, no % lower than .10 allowed
                 if(map.attackers.name.equalsIgnoreCase(teamA.name)) {
                     if(teamBEco == 0) {
                         currentTeamAAttPerc += 0.5;
                         if(currentTeamAAttPerc > 0.9)
                             currentTeamAAttPerc = (float) 0.9;
                     }
                     else if (teamAEco == 0) {
                         currentTeamAAttPerc -= 0.5;
                         if(currentTeamAAttPerc > 0.1)
                             currentTeamAAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>currentTeamAAttPerc) {
                         teamAWin = false;
                     }
                     else
                         teamAWin = true;
                 }
                 else if(map.attackers.name.equalsIgnoreCase(teamB.name)) {
                     if(teamAEco == 0) {
                         currentTeamBAttPerc += 0.5;
                         if(currentTeamBAttPerc > 0.9)
                             currentTeamBAttPerc = (float) 0.9;
                     }
                     else if (teamBEco == 0) {
                         currentTeamBAttPerc -= 0.5;
                         if(currentTeamBAttPerc > 0.1)
                             currentTeamBAttPerc = (float) 0.1;
                     }
                     float outcome = rand.nextFloat();
                     if(outcome>teamBAttPerc) {
                         teamAWin = true;
                     }
                     else
                         teamAWin = false;
                 }
                 if(teamAWin==true) {
                     if(teamAEco<4)
                         teamAEco ++;
                     teamA.winRound();
                     if(teamBEco!=0)
                         teamBEco--;
                     else
                         teamBEco++;
                     totalRounds++;
                 }
                 else {
                     if(teamBEco<4)
                         teamBEco ++;
                     teamB.winRound();
                     if(teamAEco!=0)
                         teamAEco--;
                     else
                         teamAEco++;
                     totalRounds++;
                 }
             }
         }
         if(teamA.score>teamB.score)
             teamA.mapsWon++;		
         else
             teamB.mapsWon++;
     }
     
     public static void Bo3Game(Team teamA, Team teamB) {
         Map[] gameMaps = Bo3MapSelect(teamA, teamB);
         System.out.println("Matchup: "+teamA.name +" vs "+ teamB.name);
         System.out.print(gameMaps[0].name.toUpperCase() +"\n"+ gameMaps[1].name.toUpperCase()
                 +"\n"+ gameMaps[2].name.toUpperCase() + "\n");
         teamA.resetMapsWon();
         teamB.resetMapsWon();
         playMap(gameMaps[0], teamA, teamB);
         playMap(gameMaps[1], teamA, teamB);
         if(teamA.mapsWon==1 && teamB.mapsWon==1)
             playMap(gameMaps[2], teamA, teamB);
         if(teamA.mapsWon==2)
             System.out.println(teamA.name + " WINS " + teamA.mapsWon + ":" + teamB.mapsWon + " AGAINST " + teamB.name + "!");
         else
             System.out.println(teamB.name + " WINS " + teamB.mapsWon + ":" + teamA.mapsWon + " AGAINST " + teamA.name + "!");
     }
     
     public static Team Bo3GameTourn(Team teamA, Team teamB) {
         Map[] gameMaps = Bo3MapSelect(teamA, teamB);	
         teamA.resetMapsWon();
         teamB.resetMapsWon();
         playMapSilent(gameMaps[0], teamA, teamB);
         playMapSilent(gameMaps[1], teamA, teamB);
         if(teamA.mapsWon==1 && teamB.mapsWon==1)
             playMapSilent(gameMaps[2], teamA, teamB);
         if(teamA.mapsWon==2) {
             System.out.print(teamA.name + " beats " + teamB.name + "\n");
             return teamA;
         }
         else {
             System.out.print(teamB.name + " beats " + teamA.name + "\n");
             return teamB;
         }
     }
     
     public static Team Bo3GameTournSilent(Team teamA, Team teamB) {
         Map[] gameMaps = Bo3MapSelect(teamA, teamB);
         teamA.resetMapsWon();
         teamB.resetMapsWon();
         playMapSilent(gameMaps[0], teamA, teamB);
         playMapSilent(gameMaps[1], teamA, teamB);
         if(teamA.mapsWon==1 && teamB.mapsWon==1)
             playMapSilent(gameMaps[2], teamA, teamB);
         if(teamA.mapsWon==2) 
             return teamA;
         else 
             return teamB;
     }
 
     public static void Bo3GameAgg(Team teamA, Team teamB) {
         int teamAWins = 0;
         int teamBWins = 0;
         for(int i = 0; i<1000; i++) {
             teamA.resetMapsWon();
             teamB.resetMapsWon();
             Map[] gameMaps = Bo3MapSelect(teamA, teamB);
             playMapSilent(gameMaps[0], teamA, teamB);
             playMapSilent(gameMaps[1], teamA, teamB);
             if(teamA.mapsWon==1 && teamB.mapsWon==1)
                 playMapSilent(gameMaps[2], teamA, teamB);
             if(teamA.mapsWon==2)
                 teamAWins++;
             else if(teamB.mapsWon==2)
                 teamBWins++;
         }
         System.out.println(teamA.name + " won " + (float)teamAWins/10 + "% of the time.\n" + 
                 teamB.name + " won " + (float)teamBWins/10+ "% of the time.");
 
     }
     
     public static void Bo5Game(Team teamA, Team teamB) {
         Map[] gameMaps = Bo5MapSelect(teamA, teamB);
         System.out.println("Matchup: "+teamA.name +" vs "+ teamB.name);
         System.out.print(gameMaps[0].name.toUpperCase() +"\n"+ gameMaps[1].name.toUpperCase()
                 +"\n"+ gameMaps[2].name.toUpperCase() + "\n" + gameMaps[3].name.toUpperCase()
                 + "\n" + gameMaps[4].name.toUpperCase() + "\n");
         teamA.resetMapsWon();
         teamB.resetMapsWon();
         playMap(gameMaps[0], teamA, teamB);
         playMap(gameMaps[1], teamA, teamB);
         playMap(gameMaps[2], teamA, teamB);
         if(teamA.mapsWon!=3 && teamB.mapsWon!=3)
             playMap(gameMaps[3], teamA, teamB);
         if(teamA.mapsWon!=3 && teamB.mapsWon!=3)
             playMap(gameMaps[4], teamA, teamB);
         if(teamA.mapsWon==3)
             System.out.println(teamA.name + " WINS " + teamA.mapsWon + ":" + teamB.mapsWon + " AGAINST " + teamB.name + "!");
         else
             System.out.println(teamB.name + " WINS " + teamB.mapsWon + ":" + teamA.mapsWon + " AGAINST " + teamA.name + "!");
     }
     
     public static Team Bo5GameTourn(Team teamA, Team teamB) {
         Map[] gameMaps = Bo5MapSelectTourn(teamA, teamB);
         teamA.resetMapsWon();
         teamB.resetMapsWon();
         playMapSilent(gameMaps[0], teamA, teamB);
         playMapSilent(gameMaps[1], teamA, teamB);
         playMapSilent(gameMaps[2], teamA, teamB);
         if(teamA.mapsWon!=3 && teamB.mapsWon!=3)
             playMapSilent(gameMaps[3], teamA, teamB);
         if(teamA.mapsWon!=3 && teamB.mapsWon!=3)
             playMapSilent(gameMaps[4], teamA, teamB);
         if(teamA.mapsWon==3) {
             System.out.print(teamA.name + " beats " + teamB.name + "\n");
             return teamA;
         }
         else {
             System.out.print(teamB.name + " beats " + teamA.name + "\n");
             return teamB;
         }
     }
     
     public static Team Bo5GameTournSilent(Team teamA, Team teamB) {
         Map[] gameMaps = Bo5MapSelectTourn(teamA, teamB);
         teamA.resetMapsWon();
         teamB.resetMapsWon();
         playMapSilent(gameMaps[0], teamA, teamB);
         playMapSilent(gameMaps[1], teamA, teamB);
         playMapSilent(gameMaps[2], teamA, teamB);
         if(teamA.mapsWon!=3 && teamB.mapsWon!=3)
             playMapSilent(gameMaps[3], teamA, teamB);
         if(teamA.mapsWon!=3 && teamB.mapsWon!=3)
             playMapSilent(gameMaps[4], teamA, teamB);
         if(teamA.mapsWon==3) 
             return teamA;
         else 
             return teamB;
     }
     
     public static void Bo5GameAgg(Team teamA, Team teamB) {
         int teamAWins = 0;
         int teamBWins = 0;
         for(int i = 0; i<1000; i++) {
             teamA.resetMapsWon();
             teamB.resetMapsWon();
             Map[] gameMaps = Bo5MapSelect(teamA, teamB);
             playMapSilent(gameMaps[0], teamA, teamB);
             playMapSilent(gameMaps[1], teamA, teamB);
             playMapSilent(gameMaps[2], teamA, teamB);
             if(teamA.mapsWon!=3 && teamB.mapsWon!=3)
                 playMapSilent(gameMaps[3], teamA, teamB);
             if(teamA.mapsWon!=3 && teamB.mapsWon!=3)
                 playMapSilent(gameMaps[4], teamA, teamB);
             if(teamA.mapsWon==3)
                 teamAWins++;			
             else if(teamB.mapsWon==3)
                 teamBWins++;
             }
         System.out.println(teamA.name + " won " + (float)teamAWins/10 + "% of the time.\n" + 
                 teamB.name + " won " + (float)teamBWins/10+ "% of the time.");
     }
     
     public static Map[] Bo3MapSelect(Team teamA, Team teamB) {
         ArrayList <Map> allMaps = new ArrayList<Map>();
         Map bind = new Map("bind", 41.04, 58.96, 65, 35, 84.62, 100);
         allMaps.add(bind);
         Map haven = new Map("haven", 47.82, 82.18, 46.43, 53.57, 85, 84.17);
         allMaps.add(haven);
         Map ascent = new Map("ascent", 51.54, 48.56, 41.54, 58.56, 89.12, 89.08);
         allMaps.add(ascent);
         Map icebox = new Map("icebox", 47.11, 52.89, 50.74, 49.26, 82.32, 82.32);
         allMaps.add(icebox);
         Map lotus = new Map("lotus", 46.04, 53.96, 48.01, 51.99, 82.19, 95.45);
         allMaps.add(lotus);
         Map sunset = new Map("sunset", 50.07, 49.93, 49.54, 50.46, 91.5, 90.46);
         allMaps.add(sunset);
         Map abyss = new Map("abyss", 49.09, 50.91, 70.84, 29.16, 83.34, 100);
         allMaps.add(abyss);
         
         Map[] gameMaps = new Map[3];
         
         //same region: compare rating
         if(teamA.region.equalsIgnoreCase(teamB.region)) {
             if(teamA.rating>teamB.rating) {
                 //teamA bans 
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[0] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[0].setAttSide(teamB);
                             gameMaps[0].setDefSide(teamA);
                         }
                         else {
                             gameMaps[0].setAttSide(teamA);
                             gameMaps[0].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[1] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[1].setAttSide(teamA);
                             gameMaps[1].setDefSide(teamB);
                         }
                         else {
                             gameMaps[1].setAttSide(teamB);
                             gameMaps[1].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //add final map to gameMaps
                 gameMaps[2] = allMaps.get(0);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[2].setAttSide(teamA);
                             gameMaps[2].setDefSide(teamB);
                         }
                         else {
                             gameMaps[2].setAttSide(teamB);
                             gameMaps[2].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }		
             }
             else {
                 //teamB bans 
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamB picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[0] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[0].setAttSide(teamA);
                             gameMaps[0].setDefSide(teamB);
                         }
                         else {
                             gameMaps[0].setAttSide(teamB);
                             gameMaps[0].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[1] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[1].setAttSide(teamB);
                             gameMaps[1].setDefSide(teamA);
                         }
                         else {
                             gameMaps[1].setAttSide(teamA);
                             gameMaps[1].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamB bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //add final map to gameMaps
                 gameMaps[2] = allMaps.get(0);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[2].setAttSide(teamB);
                             gameMaps[2].setDefSide(teamA);
                         }
                         else {
                             gameMaps[2].setAttSide(teamA);
                             gameMaps[2].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }		
             }
         }
         //different region: compare intlRating
         else {
             if(teamA.intlRating>teamB.intlRating) {
                 //teamA bans 
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //allMaps.remove(ban(allMaps, teamA, teamB));
                 //teamB bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[0] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[0].setAttSide(teamB);
                             gameMaps[0].setDefSide(teamA);
                         }
                         else {
                             gameMaps[0].setAttSide(teamA);
                             gameMaps[0].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[1] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[1].setAttSide(teamA);
                             gameMaps[1].setDefSide(teamB);
                         }
                         else {
                             gameMaps[1].setAttSide(teamB);
                             gameMaps[1].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //add final map to gameMaps
                 gameMaps[2] = allMaps.get(0);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[2].setAttSide(teamA);
                             gameMaps[2].setDefSide(teamB);
                         }
                         else {
                             gameMaps[2].setAttSide(teamB);
                             gameMaps[2].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }		
             }
             else {
                 //teamB bans 
                 //remove that map from the ArrayList
                 /*for(Map m : allMaps) {
                     if(m.getName().equalsIgnoreCase(ban(allMaps, teamB, teamA).name))
                         allMaps.remove(m);
                 }*/
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[0] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[0].setAttSide(teamA);
                             gameMaps[0].setDefSide(teamB);
                         }
                         else {
                             gameMaps[0].setAttSide(teamB);
                             gameMaps[0].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[1] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[1].setAttSide(teamB);
                             gameMaps[1].setDefSide(teamA);
                         }
                         else {
                             gameMaps[1].setAttSide(teamA);
                             gameMaps[1].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //add final map to gameMaps
                 gameMaps[2] = allMaps.get(0);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[2].setAttSide(teamB);
                             gameMaps[2].setDefSide(teamA);
                         }
                         else {
                             gameMaps[2].setAttSide(teamA);
                             gameMaps[2].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }		
             }
         }
         return gameMaps;
     }
     
     public static Map[] Bo3MapSelectTourn(Team teamA, Team teamB) {
         ArrayList <Map> allMaps = new ArrayList<Map>();
         Map bind = new Map("bind", 41.04, 58.96, 65, 35, 84.62, 100);
         allMaps.add(bind);
         Map haven = new Map("haven", 47.82, 82.18, 46.43, 53.57, 85, 84.17);
         allMaps.add(haven);
         Map ascent = new Map("ascent", 51.54, 48.56, 41.54, 58.56, 89.12, 89.08);
         allMaps.add(ascent);
         Map icebox = new Map("icebox", 47.11, 52.89, 50.74, 49.26, 82.32, 82.32);
         allMaps.add(icebox);
         Map lotus = new Map("lotus", 46.04, 53.96, 48.01, 51.99, 82.19, 95.45);
         allMaps.add(lotus);
         Map sunset = new Map("sunset", 50.07, 49.93, 49.54, 50.46, 91.5, 90.46);
         allMaps.add(sunset);
         Map abyss = new Map("abyss", 49.09, 50.91, 70.84, 29.16, 83.34, 100);
         allMaps.add(abyss);
         
         Map[] gameMaps = new Map[3];
         
         //teamA bans 
         //remove that map from the ArrayList
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                 iterator.remove();
                 break;
             }
         }
         //teamB bans
         //remove that map from the ArrayList
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                 iterator.remove();
                 break;
             }
         }				
         //teamA picks
         //add that map to gameMaps and remove from the ArrayList
         gameMaps[0] = pick(allMaps, teamA, teamB);
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                 float[] teamAMapRate = teamA.mapDetails(map.name);
                 float[] teamBMapRate = teamB.mapDetails(map.name);
                 if(teamAMapRate[1]>teamBMapRate[2]){
                     gameMaps[0].setAttSide(teamB);
                     gameMaps[0].setDefSide(teamA);
                 }
                 else {
                     gameMaps[0].setAttSide(teamA);
                     gameMaps[0].setDefSide(teamB);
                 }
                 iterator.remove();
                 break;
             }
         }
         //teamB picks
         //add that map to gameMaps and remove from the ArrayList
         gameMaps[1] = pick(allMaps, teamB, teamA);
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                 float[] teamAMapRate = teamA.mapDetails(map.name);
                 float[] teamBMapRate = teamB.mapDetails(map.name);
                 if(teamAMapRate[1]>teamBMapRate[2]){
                     gameMaps[1].setAttSide(teamA);
                     gameMaps[1].setDefSide(teamB);
                 }
                 else {
                     gameMaps[1].setAttSide(teamB);
                     gameMaps[1].setDefSide(teamA);
                 }
                 iterator.remove();
                 break;
             }
         }				
         //teamA bans
         //remove that map from the ArrayList
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                 iterator.remove();
                 break;
             }
         }
         //teamB bans
         //remove that map from the ArrayList
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                 iterator.remove();
                 break;
             }
         }				
         //add final map to gameMaps
         gameMaps[2] = allMaps.get(0);
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                 float[] teamAMapRate = teamA.mapDetails(map.name);
                 float[] teamBMapRate = teamB.mapDetails(map.name);
                 if(teamAMapRate[1]>teamBMapRate[2]){
                     gameMaps[2].setAttSide(teamA);
                     gameMaps[2].setDefSide(teamB);
                 }
                 else {
                     gameMaps[2].setAttSide(teamB);
                     gameMaps[2].setDefSide(teamA);
                 }
                 iterator.remove();
                 break;
             }
         }		
         return gameMaps;
     }
     
     public static Map[] Bo5MapSelect(Team teamA, Team teamB) {
         ArrayList <Map> allMaps = new ArrayList<Map>();
         Map bind = new Map("bind", 41.04, 58.96, 65, 35, 84.62, 100);
         allMaps.add(bind);
         Map haven = new Map("haven", 47.82, 82.18, 46.43, 53.57, 85, 84.17);
         allMaps.add(haven);
         Map ascent = new Map("ascent", 51.54, 48.56, 41.54, 58.56, 89.12, 89.08);
         allMaps.add(ascent);
         Map icebox = new Map("icebox", 47.11, 52.89, 50.74, 49.26, 82.32, 82.32);
         allMaps.add(icebox);
         Map lotus = new Map("lotus", 46.04, 53.96, 48.01, 51.99, 82.19, 95.45);
         allMaps.add(lotus);
         Map sunset = new Map("sunset", 50.07, 49.93, 49.54, 50.46, 91.5, 90.46);
         allMaps.add(sunset);
         Map abyss = new Map("abyss", 49.09, 50.91, 70.84, 29.16, 83.34, 100);
         allMaps.add(abyss);
         
         Map[] gameMaps = new Map[5];
         
         //same region: compare rating
         if(teamA.region.equalsIgnoreCase(teamB.region)) {
             if(teamA.rating>teamB.rating) {
                 //teamA bans 
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[0] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[0].setAttSide(teamB);
                             gameMaps[0].setDefSide(teamA);
                         }
                         else {
                             gameMaps[0].setAttSide(teamA);
                             gameMaps[0].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[1] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[1].setAttSide(teamA);
                             gameMaps[1].setDefSide(teamB);
                         }
                         else {
                             gameMaps[1].setAttSide(teamB);
                             gameMaps[1].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA picks
                 //remove that map from the ArrayList
                 gameMaps[2] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[2].setAttSide(teamB);
                             gameMaps[2].setDefSide(teamA);
                         }
                         else {
                             gameMaps[2].setAttSide(teamA);
                             gameMaps[2].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //remove that map from the ArrayList
                 gameMaps[3] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[3].setAttSide(teamA);
                             gameMaps[3].setDefSide(teamB);
                         }
                         else {
                             gameMaps[3].setAttSide(teamB);
                             gameMaps[3].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }			
                 //add final map to gameMaps
                 gameMaps[4] = allMaps.get(0);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[4].setAttSide(teamB);
                             gameMaps[4].setDefSide(teamA);
                         }
                         else {
                             gameMaps[4].setAttSide(teamA);
                             gameMaps[4].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }		
             }
             else {
                 //teamB bans 
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamB bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamB picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[0] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[0].setAttSide(teamA);
                             gameMaps[0].setDefSide(teamB);
                         }
                         else {
                             gameMaps[0].setAttSide(teamB);
                             gameMaps[0].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[1] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[1].setAttSide(teamB);
                             gameMaps[1].setDefSide(teamA);
                         }
                         else {
                             gameMaps[1].setAttSide(teamA);
                             gameMaps[1].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamB picks
                 //remove that map from the ArrayList
                 gameMaps[2] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[2].setAttSide(teamA);
                             gameMaps[2].setDefSide(teamB);
                         }
                         else {
                             gameMaps[2].setAttSide(teamB);
                             gameMaps[2].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }				
                 //teamA picks
                 //remove that map from the ArrayList
                 gameMaps[3] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[3].setAttSide(teamB);
                             gameMaps[3].setDefSide(teamA);
                         }
                         else {
                             gameMaps[3].setAttSide(teamA);
                             gameMaps[3].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }	
                 //add final map to gameMaps
                 gameMaps[4] = allMaps.get(0);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[4].setAttSide(teamB);
                             gameMaps[4].setDefSide(teamA);
                         }
                         else {
                             gameMaps[4].setAttSide(teamA);
                             gameMaps[4].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }		
             }
         }
         //different region: compare intlRating
         else {
             if(teamA.intlRating>teamB.intlRating) {
                 //teamA bans 
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                         iterator.remove();
                         break;
                     }
                 }		
                 //teamA picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[0] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[0].setAttSide(teamB);
                             gameMaps[0].setDefSide(teamA);
                         }
                         else {
                             gameMaps[0].setAttSide(teamA);
                             gameMaps[0].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[1] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[1].setAttSide(teamA);
                             gameMaps[1].setDefSide(teamB);
                         }
                         else {
                             gameMaps[1].setAttSide(teamB);
                             gameMaps[1].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA picks
                 //remove that map from the ArrayList
                 gameMaps[2] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[2].setAttSide(teamB);
                             gameMaps[2].setDefSide(teamA);
                         }
                         else {
                             gameMaps[2].setAttSide(teamA);
                             gameMaps[2].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //remove that map from the ArrayList
                 gameMaps[3] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[3].setAttSide(teamA);
                             gameMaps[3].setDefSide(teamB);
                         }
                         else {
                             gameMaps[3].setAttSide(teamB);
                             gameMaps[3].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //add final map to gameMaps
                 gameMaps[4] = allMaps.get(0);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[4].setAttSide(teamA);
                             gameMaps[4].setDefSide(teamB);
                         }
                         else {
                             gameMaps[4].setAttSide(teamB);
                             gameMaps[4].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }		
             }
             else {
                 //teamB bans 
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB bans
                 //remove that map from the ArrayList
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(ban(allMaps, teamB, teamA).name)) {
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[0] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[0].setAttSide(teamA);
                             gameMaps[0].setDefSide(teamB);
                         }
                         else {
                             gameMaps[0].setAttSide(teamB);
                             gameMaps[0].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA picks
                 //add that map to gameMaps and remove from the ArrayList
                 gameMaps[1] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[1].setAttSide(teamB);
                             gameMaps[1].setDefSide(teamA);
                         }
                         else {
                             gameMaps[1].setAttSide(teamA);
                             gameMaps[1].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamB picks
                 //remove that map from the ArrayList
                 gameMaps[2] = pick(allMaps, teamB, teamA);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[2].setAttSide(teamA);
                             gameMaps[2].setDefSide(teamB);
                         }
                         else {
                             gameMaps[2].setAttSide(teamB);
                             gameMaps[2].setDefSide(teamA);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //teamA picks
                 //remove that map from the ArrayList
                 gameMaps[3] = pick(allMaps, teamA, teamB);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[3].setAttSide(teamB);
                             gameMaps[3].setDefSide(teamA);
                         }
                         else {
                             gameMaps[3].setAttSide(teamA);
                             gameMaps[3].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }
                 //add final map to gameMaps
                 gameMaps[4] = allMaps.get(0);
                 for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
                     Map map = iterator.next();
                     if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                         float[] teamAMapRate = teamA.mapDetails(map.name);
                         float[] teamBMapRate = teamB.mapDetails(map.name);
                         if(teamAMapRate[1]>teamBMapRate[2]){
                             gameMaps[4].setAttSide(teamB);
                             gameMaps[4].setDefSide(teamA);
                         }
                         else {
                             gameMaps[4].setAttSide(teamA);
                             gameMaps[4].setDefSide(teamB);
                         }
                         iterator.remove();
                         break;
                     }
                 }		
             }
         }
         return gameMaps;
     }
 
     public static Map[] Bo5MapSelectTourn(Team teamA, Team teamB) {
         ArrayList <Map> allMaps = new ArrayList<Map>();
         Map bind = new Map("bind", 41.04, 58.96, 65, 35, 84.62, 100);
         allMaps.add(bind);
         Map haven = new Map("haven", 47.82, 82.18, 46.43, 53.57, 85, 84.17);
         allMaps.add(haven);
         Map ascent = new Map("ascent", 51.54, 48.56, 41.54, 58.56, 89.12, 89.08);
         allMaps.add(ascent);
         Map icebox = new Map("icebox", 47.11, 52.89, 50.74, 49.26, 82.32, 82.32);
         allMaps.add(icebox);
         Map lotus = new Map("lotus", 46.04, 53.96, 48.01, 51.99, 82.19, 95.45);
         allMaps.add(lotus);
         Map sunset = new Map("sunset", 50.07, 49.93, 49.54, 50.46, 91.5, 90.46);
         allMaps.add(sunset);
         Map abyss = new Map("abyss", 49.09, 50.91, 70.84, 29.16, 83.34, 100);
         allMaps.add(abyss);
         
         Map[] gameMaps = new Map[5];
         
         //teamA bans 
         //remove that map from the ArrayList
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                 iterator.remove();
                 break;
             }
         }
         //teamA bans
         //remove that map from the ArrayList
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(ban(allMaps, teamA, teamB).name)) {
                 iterator.remove();
                 break;
             }
         }				
         //teamA picks
         //add that map to gameMaps and remove from the ArrayList
         gameMaps[0] = pick(allMaps, teamA, teamB);
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                 float[] teamAMapRate = teamA.mapDetails(map.name);
                 float[] teamBMapRate = teamB.mapDetails(map.name);
                 if(teamAMapRate[1]>teamBMapRate[2]){
                     gameMaps[0].setAttSide(teamB);
                     gameMaps[0].setDefSide(teamA);
                 }
                 else {
                     gameMaps[0].setAttSide(teamA);
                     gameMaps[0].setDefSide(teamB);
                 }
                 iterator.remove();
                 break;
             }
         }
         //teamB picks
         //add that map to gameMaps and remove from the ArrayList
         gameMaps[1] = pick(allMaps, teamB, teamA);
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                 float[] teamAMapRate = teamA.mapDetails(map.name);
                 float[] teamBMapRate = teamB.mapDetails(map.name);
                 if(teamAMapRate[1]>teamBMapRate[2]){
                     gameMaps[1].setAttSide(teamA);
                     gameMaps[1].setDefSide(teamB);
                 }
                 else {
                     gameMaps[1].setAttSide(teamB);
                     gameMaps[1].setDefSide(teamA);
                 }
                 iterator.remove();
                 break;
             }
         }				
         //teamA picks
         //remove that map from the ArrayList
         gameMaps[2] = pick(allMaps, teamA, teamB);
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(pick(allMaps, teamA, teamB).name)) {
                 float[] teamAMapRate = teamA.mapDetails(map.name);
                 float[] teamBMapRate = teamB.mapDetails(map.name);
                 if(teamAMapRate[1]>teamBMapRate[2]){
                     gameMaps[2].setAttSide(teamB);
                     gameMaps[2].setDefSide(teamA);
                 }
                 else {
                     gameMaps[2].setAttSide(teamA);
                     gameMaps[2].setDefSide(teamB);
                 }
                 iterator.remove();
                 break;
             }
         }
         //teamB picks
         //remove that map from the ArrayList
         gameMaps[3] = pick(allMaps, teamB, teamA);
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                 float[] teamAMapRate = teamA.mapDetails(map.name);
                 float[] teamBMapRate = teamB.mapDetails(map.name);
                 if(teamAMapRate[1]>teamBMapRate[2]){
                     gameMaps[3].setAttSide(teamA);
                     gameMaps[3].setDefSide(teamB);
                 }
                 else {
                     gameMaps[3].setAttSide(teamB);
                     gameMaps[3].setDefSide(teamA);
                 }
                 iterator.remove();
                 break;
             }
         }			
         //add final map to gameMaps
         gameMaps[4] = allMaps.get(0);
         for (Iterator<Map> iterator = allMaps.iterator(); iterator.hasNext();) {
             Map map = iterator.next();
             if(map.name.equalsIgnoreCase(pick(allMaps, teamB, teamA).name)) {
                 float[] teamAMapRate = teamA.mapDetails(map.name);
                 float[] teamBMapRate = teamB.mapDetails(map.name);
                 if(teamAMapRate[1]>teamBMapRate[2]){
                     gameMaps[4].setAttSide(teamB);
                     gameMaps[4].setDefSide(teamA);
                 }
                 else {
                     gameMaps[4].setAttSide(teamA);
                     gameMaps[4].setDefSide(teamB);
                 }
                 iterator.remove();
                 break;
             }
         }
         return gameMaps;
     }
     
     public static boolean hasMap(ArrayList<Map> availableMaps, String name){
         boolean exist = false;
         for (Map map : availableMaps){
             String mapName = map.getName();
             if(mapName.equalsIgnoreCase(name)){
                 exist = true;
                 break;
             }else{
                 exist = false;
             }
         }
         return exist;
     }
     
     public static Map ban(ArrayList<Map> availableMaps, Team teamBan, Team teamOpp) {
         Map ourWorst = new Map("ourWorst", 0, 0, 0, 0, 0, 0);
         float worst = 101;
         if(worst>teamBan.ascentW && hasMap(availableMaps, "ascent")) {
             ourWorst.setDetails("ascent", 51.54, 48.56, 41.54, 58.56, 89.12, 89.08);
             worst = teamBan.ascentW;
         }
         if(worst>teamBan.bindW && hasMap(availableMaps, "bind")) {
             ourWorst.setDetails("bind", 41.04, 58.96, 65, 35, 84.62, 100);
             worst = teamBan.bindW;
         }
         if(worst>teamBan.havenW && hasMap(availableMaps, "haven")) {
             ourWorst.setDetails("haven", 47.82, 82.18, 46.43, 53.57, 85, 84.17);
             worst = teamBan.havenW;
         }
         if(worst>teamBan.iceboxW && hasMap(availableMaps, "icebox")) {
             ourWorst.setDetails("icebox", 47.11, 52.89, 50.74, 49.26, 82.32, 82.32);
             worst = teamBan.iceboxW;
         }
         if(worst>teamBan.lotusW && hasMap(availableMaps, "lotus")) {
             ourWorst.setDetails("lotus", 46.04, 53.96, 48.01, 51.99, 82.19, 95.45);
             worst = teamBan.lotusW;
         }
         if(worst>teamBan.sunsetW && hasMap(availableMaps, "sunset")) {
             ourWorst.setDetails("sunset", 50.07, 49.93, 49.54, 50.46, 91.5, 90.46);
             worst = teamBan.sunsetW;
         }
         if(worst>teamBan.abyssW && hasMap(availableMaps, "abyss")) {
             ourWorst.setDetails("abyss", 49.09, 50.91, 70.84, 29.16, 83.34, 100);
             worst = teamBan.abyssW;
         }
         
         Map theirBest = new Map("theirBest", 0, 0, 0, 0, 0, 0);
         float best = -1;
         if(best<teamOpp.ascentW && hasMap(availableMaps, "ascent")) {
             theirBest.setDetails("ascent", 51.54, 48.56, 41.54, 58.56, 89.12, 89.08);
             best = teamOpp.ascentW;
         }
         if(best<teamOpp.bindW && hasMap(availableMaps, "bind")) {
             theirBest.setDetails("bind", 41.04, 58.96, 65, 35, 84.62, 100);
             best = teamOpp.bindW;
         }
         if(best<teamOpp.havenW && hasMap(availableMaps, "haven")) {
             theirBest.setDetails("haven", 47.82, 82.18, 46.43, 53.57, 85, 84.17);
             best = teamOpp.havenW;
         }
         if(best<teamOpp.iceboxW && hasMap(availableMaps, "icebox")) {
             theirBest.setDetails("icebox", 47.11, 52.89, 50.74, 49.26, 82.32, 82.32);
             best = teamOpp.iceboxW;
         }
         if(best<teamOpp.lotusW && hasMap(availableMaps, "lotus")) {
             theirBest.setDetails("lotus", 46.04, 53.96, 48.01, 51.99, 82.19, 95.45);
             best = teamOpp.lotusW;
         }
         if(best<teamOpp.sunsetW && hasMap(availableMaps, "sunset")) {
             theirBest.setDetails("sunset", 50.07, 49.93, 49.54, 50.46, 91.5, 90.46);
             best = teamOpp.sunsetW;
         }
         if(best<teamOpp.abyssW && hasMap(availableMaps, "abyss")) {
             theirBest.setDetails("abyss", 49.09, 50.91, 70.84, 29.16, 83.34, 100);
             best = teamOpp.abyssW;
         }
         
         if(best-50 > 50-worst)
             return theirBest;
         else
             return ourWorst;
     }
     
     public static Map pick(ArrayList<Map> availableMaps, Team teamPick, Team teamOpp) {
         Map ourBest = new Map("ourBest", 0, 0, 0, 0, 0, 0);
         float best = -1;
         if(best<teamPick.ascentW && hasMap(availableMaps, "ascent")) {
             ourBest.setDetails("ascent", 51.54, 48.56, 41.54, 58.56, 89.12, 89.08);
             best = teamPick.ascentW;
         }
         if(best<teamPick.bindW && hasMap(availableMaps, "bind")) {
             ourBest.setDetails("bind", 41.04, 58.96, 65, 35, 84.62, 100);
             best = teamPick.bindW;
         }
         if(best<teamPick.havenW && hasMap(availableMaps, "haven")) {
             ourBest.setDetails("haven", 47.82, 82.18, 46.43, 53.57, 85, 84.17);
             best = teamPick.havenW;
         }
         if(best<teamPick.iceboxW && hasMap(availableMaps, "icebox")) {
             ourBest.setDetails("icebox", 47.11, 52.89, 50.74, 49.26, 82.32, 82.32);
             best = teamPick.iceboxW;
         }
         if(best<teamPick.lotusW && hasMap(availableMaps, "lotus")) {
             ourBest.setDetails("lotus", 46.04, 53.96, 48.01, 51.99, 82.19, 95.45);
             best = teamPick.lotusW;
         }
         if(best<teamPick.sunsetW && hasMap(availableMaps, "sunset")) {
             ourBest.setDetails("sunset", 50.07, 49.93, 49.54, 50.46, 91.5, 90.46);
             best = teamPick.sunsetW;
         }
         if(best<teamPick.abyssW && hasMap(availableMaps, "abyss")) {
             ourBest.setDetails("abyss", 49.09, 50.91, 70.84, 29.16, 83.34, 100);
             best = teamPick.abyssW;
         }
         
         Map theirWorst = new Map("theirWorst", 0, 0, 0, 0, 0, 0);
         float worst = 101;
         if(worst>teamOpp.ascentW && hasMap(availableMaps, "ascent")) {
             theirWorst.setDetails("ascent", 51.54, 48.56, 41.54, 58.56, 89.12, 89.08);
             worst = teamOpp.ascentW;
         }
         if(worst>teamOpp.bindW && hasMap(availableMaps, "bind")) {
             theirWorst.setDetails("bind", 41.04, 58.96, 65, 35, 84.62, 100);
             worst = teamOpp.bindW;
         }
         if(worst>teamOpp.havenW && hasMap(availableMaps, "haven")) {
             theirWorst.setDetails("haven", 47.82, 82.18, 46.43, 53.57, 85, 84.17);
             worst = teamOpp.havenW;
         }
         if(worst>teamOpp.iceboxW && hasMap(availableMaps, "icebox")) {
             theirWorst.setDetails("icebox", 47.11, 52.89, 50.74, 49.26, 82.32, 82.32);
             worst = teamOpp.iceboxW;
         }
         if(worst>teamOpp.lotusW && hasMap(availableMaps, "lotus")) {
             theirWorst.setDetails("lotus", 46.04, 53.96, 48.01, 51.99, 82.19, 95.45);
             worst = teamOpp.lotusW;
         }
         if(worst>teamOpp.sunsetW && hasMap(availableMaps, "sunset")) {
             theirWorst.setDetails("sunset", 50.07, 49.93, 49.54, 50.46, 91.5, 90.46);
             worst = teamOpp.sunsetW;
         }
         if(worst>teamOpp.abyssW && hasMap(availableMaps, "abyss")) {
             theirWorst.setDetails("abyss", 49.09, 50.91, 70.84, 29.16, 83.34, 100);
             worst = teamOpp.abyssW;
         }
         
         if(best-50 > 50-worst)
             return ourBest;
         else
             return theirWorst;
     }
 
     public static Team findByName(Collection<Team> listTeams, String tricode) {
          for (Team e: listTeams)
              if(e.name.contentEquals(tricode))  
                  return e;
          return null;
     }
     
     public static ArrayList<Team> parse() {
         ArrayList<Team> teams = new ArrayList<Team>();
         try {
             File inputFile = new File("C:\\Users\\Ryan Wynne\\OneDrive\\Documents\\MyVSCode\\VCT24_Tool\\src\\Setup.txt");
             Scanner scanner = new Scanner(inputFile);
             while(scanner.hasNextLine()) {
                 String currentLine = scanner.nextLine();
                 if (currentLine.startsWith("TEAM:")) {
                     String s = currentLine.replace("TEAM:", "");
                     String[] array = s.split(",");
                     String name = array[0];
                     String region = array[1].replace(" ", "");
                     float[] vals = new float[array.length-2];
                     for (int i = 0; i < array.length-2; i++) {
                         vals[i] = Float.parseFloat(array[i+2]);
                     }
                     Team team = new Team(name, region, vals[0], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6],
                             vals[7], vals[8], vals[9], vals[10], vals[11], vals[12], vals[13], vals[14], vals[15], vals[16], 
                             vals[17], vals[18], vals[19], vals[20], vals[21], vals[22], vals[23], vals[24], vals[25], vals[26], 
                             vals[27], vals[28], vals[29], vals[30], vals[31], vals[32], vals[33], vals[34], 50, 50);
                     team.setGamesPlayed((int)(team.abyssAttSel+team.abyssDefSel+team.sunsetAttSel+team.sunsetDefSel
                             +team.ascentAttSel+team.ascentDefSel+team.bindAttSel+team.bindDefSel
                             +team.havenAttSel+team.havenDefSel+team.iceboxAttSel+team.iceboxDefSel
                             +team.lotusAttSel+team.lotusDefSel));
                     teams.add(team);
                 }
                 else if (currentLine.startsWith("RESULT:")) {
                     String s = currentLine.replace("RESULT:", "");
                     String[] array = s.split("!");
                     findByName(teams, array[0]).win(findByName(teams, array[1]));;
                     findByName(teams, array[1]).loss(findByName(teams, array[0]));				
                 }
                 else if (currentLine.startsWith("INTL RESULT:")) {
                     String s = currentLine.replace("INTL RESULT:", "");
                     String[] array = s.split("!");
                     findByName(teams, array[0]).intlWin(findByName(teams, array[1]));;
                     findByName(teams, array[1]).intlLoss(findByName(teams, array[0]));
                     //System.out.println(findByName(teams, array[0]).name + " intl rating:" + findByName(teams, array[0]).intlRating + 
                     //		", " + findByName(teams, array[1]).name + " intl rating:" + findByName(teams, array[1]).intlRating);
                 }
                 else if (currentLine.startsWith("FINAL RESULT:")) {
                     String s = currentLine.replace("FINAL RESULT:", "");
                     String[] array = s.split("!");
                     findByName(teams, array[0]).finalWin();;
                     findByName(teams, array[1]).loss(findByName(teams, array[0]));				
                     //System.out.println(findByName(teams, array[0]).name + " rating:" + findByName(teams, array[0]).rating + 
                     //		", " + findByName(teams, array[1]).name + " rating:" + findByName(teams, array[1]).rating);
                 }
                 else if (currentLine.startsWith("FINAL INTL RESULT:")) {
                     String s = currentLine.replace("FINAL INTL RESULT:", "");
                     String[] array = s.split("!");
                     findByName(teams, array[0]).finalIntlWin();;
                     findByName(teams, array[1]).intlLoss(findByName(teams, array[0]));
                     //System.out.println(findByName(teams, array[0]).name + " intl rating:" + findByName(teams, array[0]).intlRating + 
                     //		", " + findByName(teams, array[1]).name + " intl rating:" + findByName(teams, array[1]).intlRating);
                 }
             }
         }catch (FileNotFoundException e) {
             System.out.println("Error.");
             e.printStackTrace();
         }
         return teams;
     }
     
     public static void dataDisplay(ArrayList<Team> teams) {
         ArrayList<String> dataTypes = new ArrayList<String>();
         String sortBy = "";
         Scanner dataScanner = new Scanner(System.in);
         System.out.println("Enter the data types you would like to be displayed separated by a space exactly as shown below:\n"
                 + "(name, region, gamesPlayed, rating, internationalRating, \n"
                 + "bindW, bindAtt, bindDef, bindGames\n"
                 + "havenW, havenAtt, havenDef, havenGames\n"
                 + "ascentW, ascentAtt, ascentDef, ascentGames\n"
                 + "iceboxW, iceboxAtt, iceboxDef, iceboxGames\n"
                 + "lotusW, lotusAtt, lotusDef, lotusGames\n"
                 + "sunsetW, sunsetAtt, sunsetDef, sunsetGames\n"
                 + "abyssW, abyssAtt, abyssDef, abyssGames)");
         if(dataScanner.hasNext())
             dataTypes.add(dataScanner.nextLine());
         System.out.println("Enter the data type you would like to sort by:");
         System.out.print(dataTypes.toString().replace("name ", "").replace("region ", ""));
         System.out.println();
         sortBy = dataScanner.next();
         switch (sortBy) {
             case "region":
                 break;
             case "gamesPlayed":
                 Collections.sort(teams, Team.gamesPlayedOrder);
                 break;
             case "rating":
                 Collections.sort(teams, Team.ratingOrder);
                 break;
             case "internationalRating":
                 Collections.sort(teams, Team.internationalRatingOrder);
                 break;
             case "bindW":
                 Collections.sort(teams, Team.bindWOrder);
                 break;
             case "bindAtt":
                 Collections.sort(teams, Team.bindAttOrder);
                 break;
             case "bindDef":
                 Collections.sort(teams, Team.bindDefOrder);
                 break;
             case "havenW":
                 Collections.sort(teams, Team.havenWOrder);
                 break;
             case "havenAtt":
                 Collections.sort(teams, Team.havenAttOrder);
                 break;
             case "havenDef":
                 Collections.sort(teams, Team.havenDefOrder);
                 break;
             case "ascentW":
                 Collections.sort(teams, Team.ascentWOrder);
                 break;
             case "ascentAtt":
                 Collections.sort(teams, Team.ascentAttOrder);
                 break;
             case "ascentDef":
                 Collections.sort(teams, Team.ascentDefOrder);
                 break;
             case "iceboxW":
                 Collections.sort(teams, Team.iceboxWOrder);
                 break;
             case "iceboxAtt":
                 Collections.sort(teams, Team.iceboxAttOrder);
                 break;
             case "iceboxDef":
                 Collections.sort(teams, Team.iceboxDefOrder);
                 break;
             case "lotusW":
                 Collections.sort(teams, Team.lotusWOrder);
                 break;
             case "lotusAtt":
                 Collections.sort(teams, Team.lotusAttOrder);
                 break;
             case "lotusDef":
                 Collections.sort(teams, Team.lotusDefOrder);
                 break;
             case "sunsetW":
                 Collections.sort(teams, Team.sunsetWOrder);
                 break;
             case "sunsetAtt":
                 Collections.sort(teams, Team.sunsetAttOrder);
                 break;
             case "sunsetDef":
                 Collections.sort(teams, Team.sunsetDefOrder);
                 break;
             case "abyssW":
                 Collections.sort(teams, Team.abyssWOrder);
                 break;
             case "abyssAtt":
                 Collections.sort(teams, Team.abyssAttOrder);
                 break;
             case "abyssDef":
                 Collections.sort(teams, Team.abyssDefOrder);
                 break;    
         }
         if(dataTypes.get(0).contains("name"))
             System.out.print("Name:        \t");
         if(dataTypes.get(0).contains("region"))
             System.out.print("Region:      \t");
         if(dataTypes.get(0).contains("gamesPlayed"))
             System.out.print("Games played:\t");
         if(dataTypes.get(0).contains("rating"))
             System.out.print("Rating:      \t");
         if(dataTypes.get(0).contains("internationalRating"))
             System.out.print("International rating:\t");
         if(dataTypes.get(0).contains("bindW"))
             System.out.print("Bind W%:     \t");
         if(dataTypes.get(0).contains("bindAtt"))
             System.out.print("Bind Att%:   \t");
         if(dataTypes.get(0).contains("bindDef"))
             System.out.print("Bind Def&:   \t");
         if(dataTypes.get(0).contains("bindGames"))
             System.out.print("Bind Games:  \t");
         if(dataTypes.get(0).contains("havenW"))					
             System.out.print("Haven W%:    \t");
         if(dataTypes.get(0).contains("havenAtt"))
             System.out.print("Haven Att%:  \t");
         if(dataTypes.get(0).contains("havenDef"))
             System.out.print("Haven Def%:  \t");
         if(dataTypes.get(0).contains("havenGames"))
             System.out.print("Haven Games: \t");
         if(dataTypes.get(0).contains("ascentW"))
             System.out.print("Ascent W%:   \t");
         if(dataTypes.get(0).contains("ascentAtt"))
             System.out.print("Ascent Att%: \t");
         if(dataTypes.get(0).contains("ascentDef"))
             System.out.print("Ascent Def%: \t");
         if(dataTypes.get(0).contains("ascentGames"))
             System.out.print("Ascent Games:\t");
         if(dataTypes.get(0).contains("iceboxW"))
             System.out.print("Icebox W%:   \t");
         if(dataTypes.get(0).contains("iceboxAtt"))
             System.out.print("Icebox Att%: \t");
         if(dataTypes.get(0).contains("iceboxDef"))
             System.out.print("Icebox Def%: \t");
         if(dataTypes.get(0).contains("iceboxGames"))
             System.out.print("Icebox Games:\t");
         if(dataTypes.get(0).contains("lotusW"))
             System.out.print("Lotus W%:    \t");
         if(dataTypes.get(0).contains("lotusAtt"))
             System.out.print("Lotus Att%:  \t");
         if(dataTypes.get(0).contains("lotusDef"))
             System.out.print("Lotus Def%:  \t");
         if(dataTypes.get(0).contains("lotusGames"))
             System.out.print("Lotus Games: \t");
         if(dataTypes.get(0).contains("sunsetW"))
             System.out.print("Sunset W%:   \t");
         if(dataTypes.get(0).contains("sunsetAtt"))
             System.out.print("Sunset Att%: \t");
         if(dataTypes.get(0).contains("sunsetDef"))
             System.out.print("Sunset Def%: \t");
         if(dataTypes.get(0).contains("sunsetGames"))
             System.out.print("Sunset Games:\t");
         if(dataTypes.get(0).contains("abyssW"))
             System.out.print("Abyss W%:    \t");
         if(dataTypes.get(0).contains("abyssAtt"))
             System.out.print("Abyss Att%:  \t");
         if(dataTypes.get(0).contains("abyssDef"))
             System.out.print("Abyss Def%:  \t");
         if(dataTypes.get(0).contains("abyssGames"))
             System.out.print("Abyss Games: \t");
         System.out.println();
         for(int i =0; i<teams.size(); i++) {
             if(dataTypes.get(0).contains("name"))
                 System.out.print(teams.get(i).name + "       \t");
             if(dataTypes.get(0).contains("region"))
                 System.out.print(teams.get(i).region + "       \t");
             if(dataTypes.get(0).contains("gamesPlayed"))
                 System.out.print(teams.get(i).gamesPlayed + "       \t");
             if(dataTypes.get(0).contains("rating"))
                 System.out.print(teams.get(i).rating + "       \t");
             if(dataTypes.get(0).contains("internationalRating"))
                 System.out.print(teams.get(i).intlRating + "       \t");
             if(dataTypes.get(0).contains("bindW"))
                 System.out.print(teams.get(i).bindW + "       \t");
             if(dataTypes.get(0).contains("bindAtt"))
                 System.out.print(teams.get(i).bindAtt + "       \t");
             if(dataTypes.get(0).contains("bindDef"))
                 System.out.print(teams.get(i).bindDef + "       \t");
             if(dataTypes.get(0).contains("bindGames"))
                 System.out.print(teams.get(i).bindGames + "       \t");
             if(dataTypes.get(0).contains("havenW"))					
                 System.out.print(teams.get(i).havenW + "       \t");
             if(dataTypes.get(0).contains("havenAtt"))
                 System.out.print(teams.get(i).havenAtt + "       \t");
             if(dataTypes.get(0).contains("havenDef"))
                 System.out.print(teams.get(i).havenDef + "       \t");
             if(dataTypes.get(0).contains("havenGames"))
                 System.out.print(teams.get(i).havenGames + "       \t");
             if(dataTypes.get(0).contains("ascentW"))
                 System.out.print(teams.get(i).ascentW + "       \t");
             if(dataTypes.get(0).contains("ascentAtt"))
                 System.out.print(teams.get(i).ascentAtt + "       \t");
             if(dataTypes.get(0).contains("ascentDef"))
                 System.out.print(teams.get(i).ascentDef + "       \t");
             if(dataTypes.get(0).contains("ascentGames"))
                 System.out.print(teams.get(i).ascentGames + "       \t");
             if(dataTypes.get(0).contains("iceboxW"))
                 System.out.print(teams.get(i).iceboxW + "       \t");
             if(dataTypes.get(0).contains("iceboxAtt"))
                 System.out.print(teams.get(i).iceboxAtt + "       \t");
             if(dataTypes.get(0).contains("iceboxDef"))
                 System.out.print(teams.get(i).iceboxDef + "       \t");
             if(dataTypes.get(0).contains("iceboxGames"))
                 System.out.print(teams.get(i).iceboxGames + "       \t");
             if(dataTypes.get(0).contains("lotusW"))
                 System.out.print(teams.get(i).lotusW + "       \t");
             if(dataTypes.get(0).contains("lotusAtt"))
                 System.out.print(teams.get(i).lotusAtt + "       \t");
             if(dataTypes.get(0).contains("lotusDef"))
                 System.out.print(teams.get(i).lotusDef + "       \t");
             if(dataTypes.get(0).contains("lotusGames"))
                 System.out.print(teams.get(i).lotusGames + "       \t");
             if(dataTypes.get(0).contains("sunsetW"))
                 System.out.print(teams.get(i).sunsetW + "       \t");
             if(dataTypes.get(0).contains("sunsetAtt"))
                 System.out.print(teams.get(i).sunsetAtt + "       \t");
             if(dataTypes.get(0).contains("sunsetDef"))
                 System.out.print(teams.get(i).sunsetDef + "       \t");
             if(dataTypes.get(0).contains("sunsetGames"))
                 System.out.print(teams.get(i).sunsetGames + "       \t");
             if(dataTypes.get(0).contains("abyssW"))
                 System.out.print(teams.get(i).abyssW + "       \t");
             if(dataTypes.get(0).contains("abyssAtt"))
                 System.out.print(teams.get(i).abyssAtt + "       \t");
             if(dataTypes.get(0).contains("abyssDef"))
                 System.out.print(teams.get(i).abyssDef + "       \t");
             if(dataTypes.get(0).contains("abyssGames"))
                 System.out.print(teams.get(i).abyssGames + "       \t");
             System.out.print("\n");
             }
     }
     
     public static void simulation(ArrayList<Team> teams) {
         System.out.println("Do you want to simulate a /Bo3/, /Bo3Aggregate/, /Bo5/, /Bo5Aggregate/, /Champions/ or /ChampionsAggregate?");
         String userInput = "";
         boolean validInput = false;
         while(validInput == false) {
             userInput = input.next();
             if(userInput.equalsIgnoreCase("Bo3")) {
                 Team teamA = new Team();
                 Team teamB = new Team();
                 System.out.println("What is the 'tricode' of the first team would you like to compete?");
                 String tricode = input.next();
                 for(Team t : teams) {
                     if(t.name.equalsIgnoreCase(tricode))
                         teamA = t;
                 }
                 System.out.println("What is the 'tricode' of the second team would you like to compete?");
                 tricode = input.next();
                 for(Team t : teams) {
                     if(t.name.equalsIgnoreCase(tricode))
                         teamB = t;
                 }
                 Bo3Game(teamA, teamB);
                 validInput = true;
             }
             else if(userInput.equalsIgnoreCase("Bo3Aggregate")) {
                 Team teamA = new Team();
                 Team teamB = new Team();
                 System.out.println("What is the 'tricode' of the first team would you like to compete?");
                 String tricode = input.next();
                 for(Team t : teams) {
                     if(t.name.equalsIgnoreCase(tricode))
                         teamA = t;
                 }
                 System.out.println("What is the 'tricode' of the second team would you like to compete?");
                 tricode = input.next();
                 for(Team t : teams) {
                     if(t.name.equalsIgnoreCase(tricode))
                         teamB = t;
                 }
                 Bo3GameAgg(teamA, teamB);
                 validInput = true;
             }
             else if(userInput.equalsIgnoreCase("Bo5")) {
                 Team teamA = new Team();
                 Team teamB = new Team();
                 System.out.println("What is the 'tricode' of the first team would you like to compete?");
                 String tricode = input.next();
                 for(Team t : teams) {
                     if(t.name.equalsIgnoreCase(tricode))
                         teamA = t;
                 }
                 System.out.println("What is the 'tricode' of the second team would you like to compete?");
                 tricode = input.next();
                 for(Team t : teams) {
                     if(t.name.equalsIgnoreCase(tricode))
                         teamB = t;
                 }
                 Bo5Game(teamA, teamB);
                 validInput = true;
             }
             else if(userInput.equalsIgnoreCase("Bo5Aggregate")) {
                 Team teamA = new Team();
                 Team teamB = new Team();
                 System.out.println("What is the 'tricode' of the first team would you like to compete?");
                 String tricode = input.next();
                 for(Team t : teams) {
                     if(t.name.equalsIgnoreCase(tricode))
                         teamA = t;
                 }
                 System.out.println("What is the 'tricode' of the second team would you like to compete?");
                 tricode = input.next();
                 for(Team t : teams) {
                     if(t.name.equalsIgnoreCase(tricode))
                         teamB = t;
                 }
                 Bo5GameAgg(teamA, teamB);
                 validInput = true;
             }
             else if(userInput.equalsIgnoreCase("Champions")) {
                 System.out.println("Please enter the tricodes of the 16 teams competing in this Champions event.\n"
                         + "Please be sure to enter them according to their seeding (1st seeds first, 4th seeds last), "
                         + "and in the order EMEA, AMERICAS, PACIFIC, CHINA.");
                 ArrayList<Team> qualifiedTeams = new ArrayList<Team>();
                 Team qualified = new Team();
                 for(int i = 0; i<16; i++) {
                     String tricode = input.next();
                     for(Team t : teams) {
                         if(t.name.equalsIgnoreCase(tricode)) {
                             qualified = t;
                             break;
                         }
                     }
                     qualifiedTeams.add(qualified);
                 }
                 champions(qualifiedTeams);
                 validInput = true;
             }
             else if(userInput.equalsIgnoreCase("championsAggregate")) {
                 System.out.println("Please enter the tricodes of the 16 teams competing in this Champions event.\n"
                         + "Please be sure to enter them according to their seeding (1st seeds first, 4th seeds last), "
                         + "and in the order EMEA, AMERICAS, PACIFIC, CHINA.");
                 ArrayList<Team> qualifiedTeams = new ArrayList<Team>();
                 Team qualified = new Team();
                 for(int i = 0; i<16; i++) {
                     String tricode = input.next();
                     for(Team t : teams) {
                         if(t.name.equalsIgnoreCase(tricode)) {
                             qualified = t;
                             break;
                         }
                     }
                     qualifiedTeams.add(qualified);
                 }
                 championsAgg(qualifiedTeams);
                 validInput = true;
             }
             else
                 System.out.print("Invalid input. Please enter input as instructed.\n");
         }
     }
     
     public static void main(String[] args) {
         ArrayList<Team> teams = parse();		
         boolean quit = false;
         while(quit == false) {
             System.out.print("Would you like to /displayData/ or do a /simulation/? Type 'quit' to quit.\n");
             String userInput = input.next();
             if(userInput.equalsIgnoreCase("quit"))
                 quit = true;
             else{
                     if(userInput.equalsIgnoreCase("displayData")) 
                         dataDisplay(teams);
                     else if(userInput.equalsIgnoreCase("simulation")) 
                         simulation(teams);
                     else
                         System.out.print("Invalid input. Please enter input as instructed.\n");
             }
         }
         input.close();
     }
 
 }
 