package entities;

import java.io.Serializable;
import java.util.Objects;

/**
*The SportsClub class is the main parent object, the following fields 
are based on the information of the sports club which is then passed on to its child object the 
FootBallClub*/

public abstract class SportsClub implements Serializable{
	private String clubName;
	private String clubLocation;
	private int numberOfPlayers;
	private int yearEstablished;

//Deafult Constructor for FootballClub
	public SportsClub(String clubName, String clubLocation, int numberOfPlayers, int yearEstablished) {
			this.clubName = clubName;
			this.clubLocation = clubLocation;
			this.numberOfPlayers = numberOfPlayers;
			this.yearEstablished = yearEstablished;
	}
	//returns the club name
	public String getClubName() {
		return clubName;
	}
	//returns the club location
	public String getClubLocation() {
		return clubLocation;
	}
	//returns the player base
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	//returns the established year of the club
	public int getYearEstablished() {
		return yearEstablished;
	}
	//updates the club name
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	//updates the club location
	public void setClubLocation(String clubLocation) {
		this.clubLocation = clubLocation;
	}
	//updates the number of players in the club
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers =  numberOfPlayers;
	}

	public void setYearEstablished(int yearEstablished) {
		this.yearEstablished =  yearEstablished;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if(object instanceof SportsClub) {
			SportsClub sportsClub = (SportsClub)object;
			return sportsClub.clubName.equals(clubName);		
		}

		else
			return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clubName, clubLocation, numberOfPlayers, yearEstablished);
	}
	//This is the default format returned when printing a SportsClub object. 
	@Override
	public String toString() {
		return "CLUB INFORMATION" + 
		String.format("%-17s", "\nCLUB NAME") + " --> " + this.clubName + 
		String.format("%-17s", "\nLOCATION") + " --> " + this.clubLocation + 
		String.format("%-17s", "\nPLAYER BASE") + " --> " + this.numberOfPlayers + 
		String.format("%-17s", "\nYEAR ESTABLISHED") + " --> " + this.yearEstablished;
	}
}



