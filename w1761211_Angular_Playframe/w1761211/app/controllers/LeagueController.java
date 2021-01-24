package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import consoleApplication.PremierLeagueConsole;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;

public class LeagueController extends Controller {
    private static PremierLeagueConsole premierLeagueController = new PremierLeagueConsole();

    /**The following GET method will return the FootballClub arraylist into JSON format
      *to backend localhost:9000 which will be accessed and displayed into the Angular host,
       the following list will be loading the following clubs saved in the serialized file*/
    public Result listPremierLeague() throws IOException, ClassNotFoundException {
        premierLeagueController.retrieveSportsClubInfo();
        ObjectMapper leagueMapper = new ObjectMapper();
        JsonNode leagueData = leagueMapper.convertValue(premierLeagueController.retrieveLeagueList(), JsonNode.class);

        System.out.println((premierLeagueController.retrieveLeagueList()));
        return ok(leagueData).as("application/json");
    }

    /**The following GET method will return the FootballMatch arraylist into JSON format
     *to backend localhost:9000 which will be accessed and displayed into the Angular host,
     the following list will be loading the following matches saved in the serialized file*/
    public Result listPremierMatches() throws IOException, ClassNotFoundException {

        premierLeagueController.retrieveSportsClubInfo();
        ObjectMapper matchMapper = new ObjectMapper();
        matchMapper.registerModule(new JavaTimeModule());
        //The following mapper will reformat Date attribute into a readable format in JSON
        //Tutorial referenced from https://stackoverflow.com/questions/28802544/java-8-localdate-jackson-format
        matchMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JsonNode matchData = matchMapper.convertValue(premierLeagueController.retrieveMatchList(), JsonNode.class);
        System.out.println(premierLeagueController.retrieveMatchList());

        return ok(matchData).as("application/json");
    }

    /**The following GET method will call out the premierleagueconsole random match method
     * which would add a match each time the method is called */
    public Result generateRandomMatch() throws IOException, ClassNotFoundException {
        premierLeagueController.retrieveSportsClubInfo();
        String matchMsg = premierLeagueController.getPremierLeagueManager().randomPlayedFootballMatch();
        ObjectMapper randomMatchMapper = new ObjectMapper();
        //Reformatting
        String matchData = randomMatchMapper.writerWithDefaultPrettyPrinter().writeValueAsString(matchMsg);

        premierLeagueController.saveSportsClubInfo();
        return ok(matchData).as("application/json");
    }
    /**The following method is a summary of the premier league*/
    public Result premierLeagueSummary() throws IOException, ClassNotFoundException {
        premierLeagueController.retrieveSportsClubInfo();
        String summaryMsg = ("There are " + premierLeagueController.retrieveLeagueList().size() + " football clubs, placed into the league & "+
               + premierLeagueController.retrieveMatchList().size()+ " matches have already taken place.");
        return ok(Json.toJson(summaryMsg)).as("application/json");
    }

}
