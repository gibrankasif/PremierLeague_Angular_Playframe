package services;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entities.*;

/**
*The PremierLeagueManager class implements the LeagueManager interface 
 which inherits the following methods needed to add, remove, and view 
 statistics of a specified football club along with the league table of 
 all exisitng Football clubs.*/
public class PremierLeagueManager implements LeagueManager {
	private static List<SportsClub> sportsLeague = new ArrayList<>();  //The List used to store football clubs
	private static List<FootballMatch> matchList = new ArrayList<>();

	private static final int MAX_LEAGUE_COUNT = 20;				//The maximum limit of clubs the Premier League can hold
	private static final int MAX_NUM_MATCHES = 380;

	/**
	* addSportsClub method is used to add a club, 
	which is stored to the sportsLeague ArrayList*/
	@Override
	public void addSportsClub(SportsClub sportsClub) {
		if(sportsLeague.size() < MAX_LEAGUE_COUNT) {
			for(SportsClub individualSportsClub: sportsLeague) {
				if((sportsClub.getClubName()).equals(individualSportsClub.getClubName())) {
					//Prints the following message, if the same club is already entered more than once.
					System.out.println("The following football club is already in the Premier League");
					return;
				} 
			}
			if(sportsClub instanceof FootballClub) {
				sportsLeague.add(sportsClub);
				//Prints the following message, after club is added in to sportsLeague.
				System.out.println("Football club successfully added into the Premier League");
			}
		}
		else {
			/*Prints the following message, if the sportsLeague arraylist size has reached 
			the maximum limit of the premier league.*/
			System.out.println("The Premier League is at full capacity!");
		}		
	}
	/**
	* relegateSportsClub method is used to remove a club, 
	which is removed from the sportsLeague ArrayList*/
	@Override
	public void relegateSportsClub(String clubName) {
		boolean clubIdentified = false;
		for(SportsClub individualSportsClub: sportsLeague) {
			if(clubName.equals(individualSportsClub.getClubName())){
				sportsLeague.remove(individualSportsClub);
				clubIdentified = true;
				//Prints the following message, after club is removed from the sportsLeague.
				System.out.println(clubName + " has been removed from the Premier League");

				break;
			} 
		}
		
		if(!clubIdentified){
			//Prints the following message, is the club to be removed does not exist in the sportsLeague.
			System.out.println("The following club is not apart of the Premier League");	
		}
	}

	@Override
	public void showSportsClubStats(String clubName) {
		boolean clubIdentified = false;
		for(SportsClub individualSportsClub: sportsLeague) {
			if(clubName.equals(individualSportsClub.getClubName())) {
				clubIdentified = true;
				//Prints out the following fields and messages mentioned in the overrided toString method
				System.out.println(individualSportsClub);
				break;
			}
		}
		if(!clubIdentified){
			//Prints the following message, is the club to be displayed does not exist in the sportsLeague.
			System.out.println("The following club is not apart of the Premier League");
		}
	}

	@Override
	public void showSportsLeagueStats(){
		List<FootballClub> footballLeague = new ArrayList<>();
		for(SportsClub individualSportsClub: sportsLeague){
			//downcasting each sportsClub into its child entity (footballClub)
			footballLeague.add((FootballClub) individualSportsClub);
		}
		//Used to sort out the entire league of football clubs
		Collections.sort(footballLeague);
		//League Table String format
		System.out.println("__________________________________________________________________________________________________________________");
		System.out.printf("%-10s%-30s%6s%7s%7s%9s%10s%12s%14s%8s\n",
		"|POSITION", "CLUB NAME", "PLAYED", " WINS", " DRAWS", " DEFEATS",
		" G-SCORED", " G-RECEIVED", " G-DIFFERENCE", " POINTS |");
		System.out.println("|----------------------------------------------------------------------------------------------------------------|");

		int positionCount = 1;
		for(FootballClub individualFootballClub: footballLeague){
			System.out.printf("%-4s%-6s%-28s%6s%8s%6s%9s%9s%11s%13s%10s%4s\n", "|",
			positionCount,individualFootballClub.getClubName().toUpperCase(), individualFootballClub.getNumberOfMatchesPlayed(),
			individualFootballClub.getClubWins(), individualFootballClub.getClubDraws(), individualFootballClub.getClubDefeats(),
			individualFootballClub.getGoalsScored(), individualFootballClub.getGoalsReceived(), individualFootballClub.calculateGoalDifference(),
			individualFootballClub.calculateFootballClubPoints(), "|");
			System.out.println("|________________________________________________________________________________________________________________|");
			positionCount++;
		}
	}

	@Override
	public void addPlayedMatch(FootballMatch footballMatch, boolean matchIdentified) {
		matchIdentified = false;
		for(FootballMatch individualFootballMatch: matchList) {
			if(footballMatch.equals(individualFootballMatch)) {
				matchIdentified = true;
				//Checks to see if the same match has happend before.
				System.out.println("The following match has already taken place!");
				break;
			}
			/*The following condition has been placed to prevent from adding a match with
			 an opponent who has been already placed on another which is on the same date*/
			else if(footballMatch.getMatchDate().equals(individualFootballMatch.getMatchDate())) {
				if ((footballMatch.footballMatchDateCheck(individualFootballMatch) || individualFootballMatch.footballMatchDateCheck(footballMatch))) {
					matchIdentified = true;
					System.out.println("Either one or both opponents have been allocated, to a different match during this date");
					break;
				}
				break;
			}
		}

		if(!matchIdentified) {
			//Validation to prevent the same club going against itself
			if(footballMatch.getOpponent01().equals(footballMatch.getOpponent02())) {
				System.out.println("A football club cannot compete amongst its self");
			}

			else {
				/*The match would be added and opponents stats would become updated 
				**only until it passes through the above validations*/
				footballMatch.updateFootballClubStats();
				matchList.add(footballMatch);
				//System.out.println(footballMatch);
			}
		}
	}

	@Override
	public void saveSportsClubInfo(String fileName) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		//first line would contain the length of the sports league arraylist
		objectOutputStream.writeObject(sportsLeague.size());
		//next line would contain the length of the match arraylist
		objectOutputStream.writeObject(matchList.size());

		//From here on, all Sportsclub objects would be written in the file 
		for(SportsClub sportsClub : sportsLeague) {
			objectOutputStream.writeObject(sportsClub);
		}
		//And then finally all Football matches would be written last in the file 
		for(FootballMatch footballMatch : matchList) {
			objectOutputStream.writeObject(footballMatch);
		}
	}
	
	@Override
	public void retrieveSportsClubInfo(String fileName) throws IOException, ClassNotFoundException {
		int numOfFootballClubs;
		int numOfFootballMatches;

		sportsLeague.clear();
		matchList.clear();

		try{
			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			for(;;) {
				try {
					/**From here the both arraylist sizes would be retrieved
					**And based on the length, each line would read each object
					which would be placed in the appropriate arraylist based on 
					the size of each arraylist*/ 
					numOfFootballClubs = (int)objectInputStream.readObject();
					numOfFootballMatches = (int)objectInputStream.readObject();

					for (int lineNo = 0; lineNo < numOfFootballClubs; lineNo++) {
					sportsLeague.add((SportsClub)objectInputStream.readObject());
					}

					for (int nxtLine = 0; nxtLine < numOfFootballMatches; nxtLine++) {
					matchList.add((FootballMatch) objectInputStream.readObject());
					}
				}	
				catch(EOFException e) {
					break;
				}
			} 
		} //Used to create a file if it doesn't exist
		catch(FileNotFoundException f){
			//System.out.println(fileName +" file not available!");
			saveSportsClubInfo(fileName);
		}
	}

	public String randomPlayedFootballMatch() {
		String responseMsg = null;

		int countMatch = 0;
		//Retrieving footballLeague size
		int leagueSize = getSportsLeagueLength();
		//380 matches can only occurs by the maximum (20 * (20 - 19))
		int maxNumberOfMatches = MAX_NUM_MATCHES;

		//If less than two clubs are added a alert will pop up.
		if(leagueSize > 1) {
			boolean randomMatchLoop = true;
			while(randomMatchLoop) {
				//Picking random football clubs by picking out a random index value.
				FootballClub opponent01 = returnClubs().get(new Random().nextInt(leagueSize));
				FootballClub opponent02 = returnClubs().get(new Random().nextInt(leagueSize));

				if (opponent01.equals(opponent02)) {
					continue;
				}
				int maxMatchScore = 10;
				int minMatchScore = 0;
				int score01 = new Random().nextInt((maxMatchScore - minMatchScore) + 1) + minMatchScore;
				int score02 = new Random().nextInt((maxMatchScore - minMatchScore) + 1) + minMatchScore;

				LocalDate startDate = LocalDate.of(2021, Month.JANUARY, 1);
				LocalDate endDate = LocalDate.of(2021, Month.DECEMBER, 31);
				//Used to find the difference in the starting & ending time period
				long days = ChronoUnit.DAYS.between(startDate, endDate);
				LocalDate randomMatchDate = startDate.plusDays(new Random().nextInt((int) days + 1));
				FootballMatch randomFootballMatch = new FootballMatch(opponent01, opponent02, score01, score02, randomMatchDate);

				/** Used to avoid matches from been repeated, if so the loop will continue running through
				 all 380 match fixtures */
				boolean matchPlaced = false;
				for (FootballMatch individualFootballMatch : returnMatches()) {
					if (randomFootballMatch.equals(individualFootballMatch)) {
						matchPlaced = true;
						break;
					}
				}
				countMatch += 1;
				//Condition below runs the following loop at a maximum of 380 loops
				if (maxNumberOfMatches == (leagueSize * (leagueSize - 1)) || maxNumberOfMatches == countMatch) {
					//System.out.println("All possible matches have been played!");
					responseMsg = "All possible matches have been played!";
					System.out.println(responseMsg);
					return responseMsg;

				} else if (matchPlaced) {
					continue;
				} else {
					boolean matchOccupied = false;
					addPlayedMatch(randomFootballMatch, matchOccupied);
					if (matchOccupied){
						continue;
					}
					else {
						String opponent01Name = randomFootballMatch.getOpponent01().getClubName();
						String opponent02Name = randomFootballMatch.getOpponent02().getClubName();

						responseMsg = String.valueOf("--------------------------------------------------" +
								String.format("%-16s", "<br> Match took place on ")  + randomMatchDate +
								String.format("%-16s", "<br>"+ opponent01Name) + " scored " + score01 + " goals" +
								String.format("%-16s", "<br>"+ opponent02Name) + " scored " + score02 + " goals "+
								"<br>--------------------------------------------------" +
								String.format("<br><br>%s", score01 > score02 ?
										opponent01Name + " has won the match against " + opponent02Name
										: score01  < score02 ?
										opponent02Name + " has won the match against " + opponent01Name
										: "The following match is a tie!"));
						System.out.println(randomFootballMatch);
						return responseMsg;
					}
				}
			}
		}
		else {
			responseMsg = "Please add at least two clubs to run a match!";
			System.out.println(responseMsg);
			return responseMsg;

		}
		return responseMsg;
	}

	//Additional method created for placing football clubs into a football match
	public static FootballClub retrieveFootballClub(String clubName) {
		for (SportsClub individualSportsClub : sportsLeague) {
			if (clubName.equals(individualSportsClub.getClubName())) {
				return (FootballClub)individualSportsClub;	
			}
		}
		return null;
	}

	/*Additional method created to return arraylist as a validation constraint
	for adding a football match  in the console.*/
	public static int getSportsLeagueLength(){
		return sportsLeague.size();
	}

	public static List<FootballClub> returnClubs() {
		List<FootballClub> footballLeague = new ArrayList<>();
		for(SportsClub individualSportsClub: sportsLeague){
			//down-casting each sportsClub into its child entity (footballClub)
			footballLeague.add((FootballClub) individualSportsClub);
		}
		Collections.sort(footballLeague);
		return footballLeague;
	}

	public static List<FootballMatch> returnMatches(){
		Collections.sort(matchList);
		return matchList;
	}
}