import {AfterViewChecked, Component} from '@angular/core';

declare var showMap: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewChecked {

  constructor() {

  }

  ngAfterViewChecked(): void {
    new showMap();
  }
}
