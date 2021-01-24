package entities;

import java.util.Objects;

/**
*The FootballClub class extends from the SportClub object
 which inherits its methods.*/
public class FootballClub extends SportsClub implements Comparable<FootballClub> {
	private int clubWins;    /*Number of matches won*/
	private int clubDraws;	 /*Number of matches tied*/
	private int clubDefeats; /*Number of matches lost*/
	private int goalsScored; /*Totals goals scored from matches*/
	private int goalsReceived; /*Goals won against opponent*/
	private int numberOfMatchesPlayed; /*The total numbers of matches based on wins, draws and losses*/
	private int points; /*The points based on the wins, draws and losses*/
	private int goalDifferenceClub;  /*Difference between the goals scored and the goals received*/


	//Default Constructor for FootballClub
	public FootballClub(String clubName, String clubLocation, int numberOfPlayers, int yearEstablished){
		super(clubName, clubLocation, numberOfPlayers, yearEstablished);
	}
	//Overloaded Football Method Constructor
	public FootballClub(String clubName, String clubLocation, int numberOfPlayers, int yearEstablished,
		int clubWins, int clubDraws, int clubDefeats, int goalsScored, int goalsReceived) {
		//calling out the sportsclub constructor
		super(clubName, clubLocation, numberOfPlayers, yearEstablished);
		this.clubWins = clubWins;
		this.clubDraws = clubDraws;
		this.clubDefeats = clubDefeats;
		this.goalsScored = goalsScored;
		this.goalsReceived = goalsReceived;
		this.numberOfMatchesPlayed = clubWins + clubDraws + clubDefeats;
	}
	//returns number of matches won
	public int getClubWins() {
		return clubWins;
	}
	//returns number of matches drawn
	public int getClubDraws() {
		return clubDraws;
	}
	//returns number of matches lost
	public int getClubDefeats() {
		return clubDefeats;
	}
	//returns goals recieved
	public int getGoalsReceived() {
		return goalsReceived;
	}

	//returns goals scored
	public int getGoalsScored() {
		return goalsScored;
	}

	//returns number of matches played
	public int getNumberOfMatchesPlayed() {
		return numberOfMatchesPlayed;
	}

	//returns the goal-difference
	public int getGoalDifferenceClub() {return goalDifferenceClub; }

	//returns the points of the club
	public int getPoints() {
		return points;
	}


	//used to update club wins
	public void setClubWins(int clubWins) {
		this.clubWins = clubWins;
	}

	//used to update club draws
	public void setClubDraws(int clubDraws) {
		this.clubDraws = clubDraws;
	}

	//used to update club defeats
	public void setClubDefeats(int clubDefeats) {
		this.clubDefeats = clubDefeats;
	}

	//used to update goals received
	public void setGoalsReceived(int goalsReceived) {
		this.goalsReceived = goalsReceived;
	}

	//used to update goals scored
	public void setGoalsScored(int goalsScored) {
		this.goalsScored = goalsScored;
	}

	//used to update number of played matches
	public void setNumberOfMatchesPlayed(int numberOfMatchesPlayed) {
		this.numberOfMatchesPlayed = numberOfMatchesPlayed;
	}

	//used to update goal difference based on match scores
	public void setGoalDifferenceClub(int goalDifference) {
		this.goalDifferenceClub = goalDifference;
	}

	//used to update points earned based on the outcome of the match
	public void setPoints(int points) {
		this.points = points;
	}

	//A separate method used to calculate the football club points which is used for stats & sorting
	public int calculateFootballClubPoints() {
		return this.clubWins * 3 + this.clubDraws * 1 + this.clubDefeats * 0;
	}

	//A separate method used to calculate the football goal difference which is necessary for sorting
	public int calculateGoalDifference() {
		return this.goalsScored - this.goalsReceived;
	}

	//Method used to update Football stats once a match has been called out
	public void addGoalsScoreAndReceived(int matchScore, int opponentMatchScore){
		this.goalsScored += matchScore;
		this.goalsReceived += opponentMatchScore;
		this.numberOfMatchesPlayed += 1;
	}

	//The following equals methods check to see if two football objects are the same by checking both club names	
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if(object instanceof FootballClub){
			return super.equals(object);
		}
			
		if(object instanceof SportsClub){
			return super.equals(object);
		}

		else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), clubWins, clubDraws, clubDefeats, goalsScored, goalsReceived, numberOfMatchesPlayed);
	}

	/*compareTo method is the default order in which the football clubs are sorted based on club points and goal difference.
	inorder to override the method, the FootballClub has implmented the Comparable interface*/
	@Override
	public int compareTo(FootballClub footballClub) {
		int comparePoints = footballClub.calculateFootballClubPoints() - this.calculateFootballClubPoints();
		if (comparePoints == 0) {
			if (this.calculateGoalDifference() > footballClub.calculateGoalDifference())
				return -1;
			else {
				if (this.calculateGoalDifference() < footballClub.calculateGoalDifference())
					return 1;
				else
					return 0;
            		}	
        	}
        	return comparePoints;
	}
	//This is the default format returned when printing a FootballClub object. 
	@Override
	public String toString() {
		return "\n" + super.toString() +"\n" + "\nCLUB STATISTICS" + 
		String.format("%-17s", "\nMATCHES PLAYED") + " --> " + this.numberOfMatchesPlayed + 
		String.format("%-17s", "\nWINS") + " --> " + this.clubWins + 
		String.format("%-17s", "\nDRAWS") + " --> " + this.clubDraws + 
		String.format("%-17s", "\nDEFEATS") + " --> " + this.clubDefeats + 
		String.format("%-17s", "\nGOALS SCORED") + " --> " + this.goalsScored + 
		String.format("%-17s", "\nGOALS CONCEDED") + " --> " + this.goalsReceived +
		String.format("%-17s", "\nGOAL DIFFERENCE") + " --> " + this.calculateGoalDifference() +
		String.format("%-17s", "\nPOINTS") + " --> " + this.calculateFootballClubPoints();
	}
}