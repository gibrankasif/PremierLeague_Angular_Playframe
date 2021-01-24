package entities;

import java.util.Objects;

/**
*The SchoolFootballClub class extends from the FootballClub object 
 which inherits its methods.*/
public class SchoolFootballClub extends FootballClub {
	private String schoolName;
	private static final int MAX_AGE_LIMIT = 18; //Constant set based on the mentioned age limit

	//Default Constructor for UniversityFootballClub
	 public SchoolFootballClub(String clubName, String clubLocation, int numberOfPlayers, int yearEstablished, String schoolName) {
		super(clubName, clubLocation, numberOfPlayers, yearEstablished);
		this.schoolName = schoolName;
	}

	//Overloaded method Constructor for SchoolFootballClub
	public SchoolFootballClub(String clubName, String clubLocation, int numberOfPlayers, int yearEstablished, 
		int clubWins, int clubDraws, int clubDefeats, int goalsScored, int goalsReceived, String schoolName) {
		super(clubName, clubLocation, numberOfPlayers, yearEstablished, clubWins, clubDraws, clubDefeats, goalsScored, goalsReceived);
		this.schoolName = schoolName;
	} 
	
	//returns school name
	public String getSchoolName() {
		return schoolName;
	}
	//changes school name
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@Override
	public boolean equals(Object object) {
		if(this == object)
			return true;

		if(object instanceof SchoolFootballClub) {
			SchoolFootballClub schoolFootballClub = (SchoolFootballClub) object;
			return schoolFootballClub.schoolName == schoolName && super.equals(object);	
		}

		if(object instanceof FootballClub) {

			if(object instanceof UniversityFootballClub)
				return false;
			else
				return super.equals(object);
		}

		if(object instanceof SportsClub)
			return super.equals(object);

		else
			return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), schoolName);
	}

	//This is the default format returned when printing a SchoolFootballClub object. 
	@Override
	public String toString() {
		return super.toString() + String.format("%-17s", "\nSCHOOL NAME") + " --> " + this.schoolName;
	}
}