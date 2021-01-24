package consoleApplication;

import entities.*;
import services.PremierLeagueManager;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class PremierLeagueConsole {
	private static PremierLeagueManager premierLeagueManager = new PremierLeagueManager();
	private static boolean initiateGUI = false;
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//Calling retrieve method to load back previously saved info to the Premier League
		retrieveSportsClubInfo();
		System.out.println("Premier League information loaded successfully");
		/*A label statement was placed to the switch case, which is used to break out of the switch case*/
		consoleMenuLoop:
		while(true) {
			System.out.println("--------------------------------------------------------------");
			System.out.println("Press \"A\" to add a Football club");
			System.out.println("Press \"D\" to relegate a Football club");
			System.out.println("Press \"T\" to print the Premier League table chart");
			System.out.println("Press \"F\" to find club statistics and information of a specific club");
			System.out.println("Press \"M\" to add a played match");
			System.out.println("Press \"R\" to generate a random played match");
			System.out.println("Press \"S\" to save the current progress");
			System.out.println("Press \"I\" to initiate Play-frame/Angular project");
			System.out.println("Press \"Q\" to quit and save all progress");
			System.out.println("--------------------------------------------------------------");

			Scanner consoleSC = new Scanner(System.in);
			String userChoice = consoleSC.nextLine().toUpperCase();
			retrieveSportsClubInfo();

			switch (userChoice) {
				case "A":
					addFootballClub();
					saveSportsClubInfo();
					break;
				case "D":
					relegateFootballClub();
					saveSportsClubInfo();
					break;
				case "T":
					//Displays an entire table chart of each and every club statistics
					premierLeagueManager.showSportsLeagueStats();
					break;
				case "F":
					displayFootballClubStats();
					break;
				case "M":
					runFootballMatch();
					saveSportsClubInfo();
					break;
				case "R":
					premierLeagueManager.randomPlayedFootballMatch();
					saveSportsClubInfo();
					break;
				case "S":
					saveSportsClubInfo();
					System.out.println("Premier League Football Clubs & Matches have been saved successfully");
					break;
				case "I":
					initiatePlayAngular();
					break;
				case "Q":
					/*Once the user enters Q to end the program, all progress would be saved 
					on the premierLeagueFile.txt, which would be loaded and update the data structures 
					with the file's last saved progress*/
					saveSportsClubInfo();
					System.out.println("Premier League Football Clubs & Matches have been saved successfully");
					break consoleMenuLoop;
				default:
					System.out.println("Invalid option, please insert another option!");
			}
		}
	}
	/**The following addFootballClub method contains the premierLeague add method, inorder to call
	this method, inputs were placed, for the club's name, location, player base and year of establishment
	which would be placed in an initialised FootballClub object which is the only parameter required, for the
	premier league manager's addSportsClub() method
	Alongside this, input validations have also been placed*/
	public static void addFootballClub() {
		Scanner footballClubInput = new Scanner(System.in);
		SportsClub sportsClub;
		String clubName = "";
		String clubLocation = "";
		int numOfPlayers;
		boolean verifyInput = true;

		while (verifyInput) {
			System.out.println("Enter the name of the Football CLub: ");
			clubName = footballClubInput.nextLine().toUpperCase().trim();
			if (clubName.equals("")) {
				System.out.println("Please enter a valid name");
			}else {
				verifyInput=false;
			}
		}
		if(premierLeagueManager.retrieveFootballClub(clubName) != null){
			System.out.println("The following club is already in the premier league!");
		}
		else {
			verifyInput = true;
			while(verifyInput) {
				System.out.println("Enter the location of the Football Club: ");
				clubLocation = footballClubInput.nextLine().toUpperCase().trim();
				if (clubLocation.equals("")) {
					System.out.println("Please enter a valid location");
				}else {
					verifyInput=false;
				}
			}
			System.out.println("Enter the number of players in the Football Club: ");
			try {
				verifyInput = true;
				do {
					numOfPlayers = footballClubInput.nextInt();
					if (numOfPlayers <= 0) {
						System.out.println("Enter a valid number of players: ");
					}else{
						verifyInput = false;
					}
				}while (verifyInput);

				verifyInput = true;
				int clubYear = 0;

				while (verifyInput) {
					System.out.println("Enter year of the Football Club's establishment: ");
					clubYear = footballClubInput.nextInt();

					if (!(clubYear >= 1800 && clubYear < 2021)) {
						System.out.println("Please enter a valid year!");
					} else {
						verifyInput = false;
					}
				}
				sportsClub = new FootballClub(clubName, clubLocation, numOfPlayers, clubYear);
				premierLeagueManager.addSportsClub(sportsClub);
			}
			catch (InputMismatchException inputMismatchException){
				System.out.println("Please enter a valid number!");
			}
		}
	}
	/**The following method calls the premier league manager's relegateSportsClub() method,
	along with just a string input to identify the club's name to be deleted.*/
	public static void relegateFootballClub()  {
		Scanner footballClubInput = new Scanner(System.in);
		System.out.println("Enter the name of the Football CLub you wish to relegate: ");
		String clubName = footballClubInput.nextLine().toUpperCase().trim();
		premierLeagueManager.relegateSportsClub(clubName);
	}

	/**The following method calls the premier league manager's showSportsClubStats() method,
	along with just a string input to identify a specific club's information & statistics*/
	public static void displayFootballClubStats() {
		Scanner footballClubInput = new Scanner(System.in);
		System.out.println("Enter the name of the Football CLub you wish to view its statistics: ");
		String clubName = footballClubInput.nextLine().toUpperCase().trim();
		premierLeagueManager.showSportsClubStats(clubName);
	}
	/**The final method runFootballMatch() gets down the premier league manager's addPlayedMatch()
	**method requires two FootballClubs objects as its arguments, so before doing that, additional input validations
	and necessary validations to confirm the existence of either one or both of the competing clubs*/
	public static void runFootballMatch() {
		if(premierLeagueManager.getSportsLeagueLength() > 1) {
			Scanner footballClubInput = new Scanner(System.in);

			System.out.println("Enter the name of the first opponent club: ");
			String opp01 = footballClubInput.nextLine().toUpperCase().trim();
			System.out.println("Enter the name of the second opponent club: ");
			String opp02 = footballClubInput.nextLine().toUpperCase().trim();

			//Retrieving two footballClub from the premier league based on the entered name of each opponent
			FootballClub opponent01 = premierLeagueManager.retrieveFootballClub(opp01);
			FootballClub opponent02 = premierLeagueManager.retrieveFootballClub(opp02);

			//Input validation in case, the club name entered does not actually exist in the league
			if(opponent01 == null || opponent02 == null) {
				if(opponent01 == null && opponent02 == null) {
					System.out.println("Both clubs do not exist in the Premier League!");
				}
				else if(opponent01 == null) {
					System.out.println(opp01 + " does not exist in the Premier League!");
				}
				else{
					System.out.println(opp02 + " does not exist in the Premier League!");
				}
			}

			//Input validation in case, both of the same clubName has been entered twice.
			else if(opp01.equals(opp02)) {
				System.out.println("The same opponent cannot compete each other!");
			}

			else {
				try {
					System.out.println("Enter the score of the first opponent: ");
					int score01 = footballClubInput.nextInt();

					System.out.println("Enter the score of the second opponent: ");
					int score02 = footballClubInput.nextInt();

					//The following condition prevents negative scores from been entered

					if (score01 < 0 || score02 < 0) {
						System.out.println("Negative scores cannot occur during a match!");
					} else {
						// try catch has been used to catch incorrect date inputs
						System.out.println("Enter the day of the match: ");
						int day = footballClubInput.nextInt();

						System.out.println("Enter the month of the match: ");
						int month = footballClubInput.nextInt();

						int year = Calendar.getInstance().get(Calendar.YEAR);

						FootballMatch footballMatch = new FootballMatch(opponent01, opponent02, score01, score02, LocalDate.of(year, month, day));
						premierLeagueManager.addPlayedMatch(footballMatch, false);
					}
				}
				catch(InputMismatchException inputMismatchException){
					System.out.println("Please enter a valid int value!");
				}
				catch (DateTimeException dateTimeException) {
					System.out.println("An invalid date has been entered!");
				}
			}
		}
		else {
			//Only this msg will be printed if the Premier League contains 0 or 1 clubs
			System.out.println("Please add at least two Football clubs in the league, to initiate a match!");
		}
	}
	//The following methods below were implemented for the use of the JavaFX GUI
	public static void retrieveSportsClubInfo() throws IOException, ClassNotFoundException {
		premierLeagueManager.retrieveSportsClubInfo("premierLeagueFile.txt");
	}
	public static void saveSportsClubInfo() throws IOException {
		premierLeagueManager.saveSportsClubInfo("premierLeagueFile.txt");
	}
	//returns the football league arraylist for use in the leagueTableView
	public static List<FootballClub> retrieveLeagueList(){
		return premierLeagueManager.returnClubs();
	}
	//returns the football match arraylist for use in the matchTableView
	public static List<FootballMatch> retrieveMatchList(){
		return premierLeagueManager.returnMatches();
	}

	//Returns a reference to the premier league manager for use in the backend controller methods
	public static PremierLeagueManager getPremierLeagueManager() {
		return premierLeagueManager;
	}

	/**
	 * The following method is used to initiate the PlayFramework & Angular host
	 * this method will be only called once whenever the console application is run*/
	public static void initiatePlayAngular() throws IOException {
		if (!initiateGUI) {
			/**The following process is used to launch cmd.exe and execute a specified cmd prompt
			 *from the existing project path. This will automatically launch the play-framework backend
			 * which then automatically runs the Angular local host, this was given as an option in the console
			 * as required from the rubric marking sheet*/
			Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"sbt run\"");
			initiateGUI = true;
		}
		else{
			System.out.println("Both Play-frame and Angular host are already running");
		}
	}
}