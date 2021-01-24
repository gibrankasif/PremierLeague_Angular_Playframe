import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MatchComponent} from './match/match.component';
import {LeagueTableComponent} from './league-table/league-table.component';

const routes: Routes = [
  {path: '',component: LeagueTableComponent},
  {path: 'Match', component: MatchComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [LeagueTableComponent, MatchComponent];
