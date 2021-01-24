import { Component, OnInit } from '@angular/core';
import {appService} from '../app.service';

@Component({
  selector: 'app-dialog-message',
  templateUrl: './dialog-message.component.html',
  styleUrls: ['./dialog-message.component.css']
})
export class DialogMessageComponent implements OnInit {
  /*The following component is used to display a dialog box whenever a random match is called for
  from the random match button*/
  matchMsg: string;
  constructor(private msgService: appService) {}
//The appService would send the backend result to matchMsg
  ngOnInit(): void {
    this.msgService.getRandomMatchMsg().subscribe((data: any) => {
        this.matchMsg = data;
        console.log(this.matchMsg);
      }
    );
  }
}
