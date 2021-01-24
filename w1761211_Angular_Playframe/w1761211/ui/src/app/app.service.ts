import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
/*The following service page is used to extract all JSON data sent from
the backend each url is based on the backend controller method from the
 PremierLeagueConsole**/
@Injectable()
export class appService {
  //FootballClub list
  private leagueUrl = '/api/league';
  //FootballMatch list
  private matchUrl = '/api/matches';
  //Random match method from java src
  private randomMatch = '/api/randomMatch';

  constructor(private http: HttpClient) {
  }
  public getPremierClubs() {
    return this.http.get(this.leagueUrl).pipe(
      map(response => response)
    );
  }
  public getPremierMatches() {
    return this.http.get(this.matchUrl).pipe(
      map(response => response)
    );
  }
  public getRandomMatchMsg(){
    return this.http.get(this.randomMatch).pipe(
      map(response => response)
    );
  }
}
