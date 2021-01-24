package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
*The following FootballMatch object was created, inorder to place a match between two football clubs
where the premier league manager choses the desired scores along with the date of the match
*/
//Default constructor for a FootballMatch
public class FootballMatch implements Serializable, Comparable<FootballMatch> {
	private FootballClub Opponent01;
	private FootballClub Opponent02;
	private int matchScoreOpponent01;
	private int matchScoreOpponent02;
	private LocalDate matchDate;

	public FootballMatch(FootballClub Opponent01, FootballClub Opponent02, int matchScoreOpponent01, int matchScoreOpponent02,
	 LocalDate matchDate) {
		this.Opponent01 = Opponent01;
		this.Opponent02 = Opponent02;
		this.matchScoreOpponent01 = matchScoreOpponent01;
		this.matchScoreOpponent02 = matchScoreOpponent02;
		this.matchDate = matchDate;
	}

	public int getMatchScoreOpponent01() {
		return matchScoreOpponent01;
	}

	public int getMatchScoreOpponent02() {
		return matchScoreOpponent02;
	}

	/**The following method is used to update each opponent's stats,
	 **which is only called once a FootballMatch has created
	 if it has gone through the necessary validation*/
	public void updateFootballClubStats(){
		Opponent01.addGoalsScoreAndReceived(matchScoreOpponent01, matchScoreOpponent02);
		Opponent02.addGoalsScoreAndReceived(matchScoreOpponent02,matchScoreOpponent01);

		Opponent01.setGoalDifferenceClub(Opponent01.getGoalsScored()-Opponent01.getGoalsReceived());
		Opponent02.setGoalDifferenceClub(Opponent02.getGoalsScored()-Opponent02.getGoalsReceived());


		if(matchScoreOpponent01 > matchScoreOpponent02) {
			Opponent01.setClubWins(Opponent01.getClubWins() + 1);
			Opponent02.setClubDefeats(Opponent02.getClubDefeats() + 1);
		}
		else if(matchScoreOpponent01 < matchScoreOpponent02) {
			Opponent02.setClubWins(Opponent02.getClubWins() + 1);
			Opponent01.setClubDefeats(Opponent01.getClubDefeats() + 1);
		}
		else {
			Opponent01.setClubDraws(Opponent01.getClubDraws() + 1);
			Opponent02.setClubDraws(Opponent02.getClubDraws() + 1);
		}
		Opponent01.setPoints(Opponent01.calculateFootballClubPoints());
		Opponent02.setPoints(Opponent02.calculateFootballClubPoints());

	}
	//Used to return the first football club opponent
	public FootballClub getOpponent01() {
		return Opponent01;
	}
	//Used to return the second football club opponent
	public FootballClub getOpponent02() {
		return Opponent02;
	}
	//Used to return the date of which the match was played at.
	public LocalDate getMatchDate() {
		return matchDate;
	}

	/**The following method footballMatchDateCheck is used
	to identify the number of occurrences of two teams competing 
	amongst themselves. So if there is at least one instance of 
	a match happening with one opponent, it would then return true.*/
	
	public boolean footballMatchDateCheck(FootballMatch footballMatch) {
		return footballMatch.Opponent01.equals(Opponent01) ||
		footballMatch.Opponent02.equals(Opponent02) ||
		footballMatch.Opponent01.equals(Opponent02) ||
		footballMatch.Opponent02.equals(Opponent01);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object instanceof FootballMatch){
			FootballMatch footballMatch = (FootballMatch)object;
			return Opponent01.equals(footballMatch.Opponent01) &&
			Opponent02.equals(footballMatch.Opponent02);
		}
		else 
			return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Opponent01, Opponent02, matchScoreOpponent01, matchScoreOpponent02, matchDate);
	}

	@Override
	public int compareTo(FootballMatch footballMatch) {
		return this.getMatchDate().compareTo(footballMatch.getMatchDate());
	}

	@Override
	public String toString() {
		return "\n----SCORE RESULTS----" + String.format("%-12s", "\nMatch Date") + " --> " + this.matchDate +
		String.format("%-12s", "\n"+Opponent01.getClubName()) + " --> " + matchScoreOpponent01 +
		String.format("%-12s", "\n"+Opponent02.getClubName()) + " --> " + matchScoreOpponent02 +
		String.format("\n%s", matchScoreOpponent01 > matchScoreOpponent02 ? 
			Opponent01.getClubName() + " has won the match against " + Opponent02.getClubName()
		 : matchScoreOpponent01 < matchScoreOpponent02 ? 
			Opponent02.getClubName() + " has won the match against " + Opponent01.getClubName(): 
			"The following match is a tie!");
	}

}