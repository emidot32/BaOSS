import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit {
  graph1 = {
    data: [
      { x: ['Mobile Product', 'Internet Product', 'DTV Product'], y: [20, 57, 39], type: 'bar' },
    ],
    layout: {width: 720, height: 440, title: 'Products'}
  };
  constructor() { }

  ngOnInit() {
  }

}
