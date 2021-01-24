import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {HttpErrorResponse} from '@angular/common/http';
import {MatSort, Sort} from '@angular/material/sort';
import {appService} from '../app.service';
import {MatDialog} from '@angular/material/dialog';
import {DialogMessageComponent} from '../dialog-message/dialog-message.component';
import {MatPaginator} from "@angular/material/paginator";
class NestedMatTableDataSource<T> extends MatTableDataSource<T> {

  constructor(initialData: T[] = []) {
    super(initialData);
  }
  // @ts-ignore
  sortingDataAccessor =
    (data: object, sortHeaderId: string): string | number => {
      const propPath = sortHeaderId.split('.');
      const value: any = propPath
        .reduce((curObj, property) => curObj[property], data);
      return !isNaN(value) ? Number(value) : value;
    };
}

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.scss']
})
export class MatchComponent implements OnInit{
  @ViewChild(MatSort) matchSort: MatSort;
  @ViewChild(MatPaginator) matchPaginator: MatPaginator;

  matchTableColumns: string[] = ['matchDate', 'opponent01.clubName', 'opponent02.clubName', 'matchScoreOpponent01', 'matchScoreOpponent02'];
  matchDataSource = new NestedMatTableDataSource([]);

  constructor(private leagueService: appService, public dialog: MatDialog ) {}

  ngOnInit(): void {
    this.leagueService.getPremierMatches().subscribe(
      (data: any) => {
        this.matchDataSource = new NestedMatTableDataSource(data);
        this.matchDataSource.paginator = this.matchPaginator;

        setTimeout(() => this.matchDataSource.sort = this.matchSort);
        const sortState: Sort = {active: 'name', direction: 'desc'};
        this.matchSort.active = sortState.active;
        this.matchSort.direction = sortState.direction;
        this.matchSort.sortChange.emit(sortState);

      },
      (err: HttpErrorResponse) => {
        console.log(err.message);
      });
  }


  public openDialog(): void {
    this.dialog.open(DialogMessageComponent)
      .afterClosed()
      .subscribe(() => this.refreshParent());
  }
  public doFilter = (value: string) => {
    this.matchDataSource.filter = value.trim().toLocaleUpperCase();
  }

  private refreshParent(): void {
    location.reload();
  }
}



