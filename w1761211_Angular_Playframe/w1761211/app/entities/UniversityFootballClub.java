package entities;

import java.util.Objects;

/*The UniversityFootballClub class extends from the FootballClub object 
 which inherits its methods.*/
public class UniversityFootballClub extends FootballClub {
	private String universityName;
	private static final int MAX_AGE_LIMIT = 23; //Constant set based on the mentioned age limit

	//Default Constructor for UniversityFootballClub
	 public UniversityFootballClub(String clubName, String clubLocation, int numberOfPlayers, int yearEstablished, String universityName) {
		super(clubName, clubLocation, numberOfPlayers, yearEstablished);
		this.universityName = universityName;
	}

	//Overloaded method Constructor for UniversityFootballClub
	public UniversityFootballClub(String clubName, String clubLocation, int numberOfPlayers, int yearEstablished, 
		int clubWins, int clubDraws, int clubDefeats, int goalsScored, int goalsReceived, String universityName) {
		super(clubName, clubLocation, numberOfPlayers, yearEstablished, clubWins, clubDraws, clubDefeats, goalsScored, goalsReceived);
		this.universityName = universityName;
	}

	//returns university name
	public String getUniversityFootballClubName() {
		return universityName;
	}
	//update university name
	public void setUniversityFootballClubName(String universityName) {
		this.universityName = universityName;
	}

	@Override
	public boolean equals(Object object) {
		if(this == object)
			return true;

		if (object instanceof UniversityFootballClub){
			UniversityFootballClub universityFootballClub = (UniversityFootballClub) object;
			return universityFootballClub.universityName == universityName;
		}
		
		if(object instanceof FootballClub) {
			
			if(object instanceof SchoolFootballClub)
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
		return Objects.hash(super.hashCode(), universityName);
	}
	
	//This is the default format returned when printing a UniversityFootballClub object. 
	@Override
	public String toString() {
		return super.toString() + String.format("%-17s", "\nUNIVERSITY NAME") + " --> " + this.universityName;
	}


} 