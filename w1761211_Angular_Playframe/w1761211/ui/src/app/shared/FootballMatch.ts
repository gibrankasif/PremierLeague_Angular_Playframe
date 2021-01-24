import {FootballClub} from './FootballClub';
//The following class is used when loading the list the objects to the match table component
export class FootballMatch {
  opponent01: FootballClub;
  opponent02: FootballClub;
  matchScoreOpponent01: number;
  matchScoreOpponent02: number;
  matchDate: string;
}
