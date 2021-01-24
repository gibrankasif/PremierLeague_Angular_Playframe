package services;

import entities.FootballMatch;
import entities.SportsClub;

import java.io.IOException;

public interface LeagueManager {
	/* addSportsClub method is used to add a club, */
	void addSportsClub(SportsClub sportsClub);
	/* relegateSportsClub method is used to remove a club, based on the club name */
	void relegateSportsClub(String clubName);
	/* showSportsClubStats method is used to view a club stats and info, based on the club name */
	void showSportsClubStats(String clubName);
	/* showSportsLeagueStats method is used to view the stats of each sports club in a league*/
	void showSportsLeagueStats();
	/* addPlayedMatch method is used to generate a simulation of match between two clubs*/
	void addPlayedMatch(FootballMatch footballMatch, boolean matchIdentified);
	/* saveSportsClubInfo method is used to generate a text file containing the objects*/
	void saveSportsClubInfo(String fileName) throws IOException;
	/* retrieveSportsClubInfo method is used to load previously saved data*/
	void retrieveSportsClubInfo(String fileName) throws IOException, ClassNotFoundException;

}