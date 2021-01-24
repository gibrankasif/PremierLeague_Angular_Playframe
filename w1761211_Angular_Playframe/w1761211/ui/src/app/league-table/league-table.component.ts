import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {HttpErrorResponse} from '@angular/common/http';
import {MatSort, Sort} from '@angular/material/sort';
import {appService} from '../app.service';
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-league-table',
  templateUrl: './league-table.component.html',
  styleUrls: ['./league-table.component.scss']
})
export class LeagueTableComponent implements OnInit{
  @ViewChild(MatSort) leagueSort: MatSort;
  @ViewChild(MatPaginator) leaguePaginator: MatPaginator;


  leagueTableColumns: string[] = ['rank', 'clubName', 'numberOfMatchesPlayed', 'clubWins', 'clubDraws', 'clubDefeats', 'goalsScored', 'goalsReceived', 'goalDifferenceClub', 'points'];
  leagueDataSource = new MatTableDataSource([]);

  constructor(private leagueService: appService) {}
  //The following datasource will receive the league list from the backend
  ngOnInit(): void {
    this.leagueService.getPremierClubs().subscribe(
      (data: any) => {
        this.leagueDataSource = new MatTableDataSource(data);
        this.leagueDataSource.paginator = this.leaguePaginator;
        setTimeout(() => this.leagueDataSource.sort = this.leagueSort);
        const sortState: Sort = {active: 'name', direction: 'desc'};
        this.leagueSort.active = sortState.active;
        this.leagueSort.direction = sortState.direction;
        this.leagueSort.sortChange.emit(sortState);

      },
      (err: HttpErrorResponse) => {
        console.log(err.message);
      });
  }
}
