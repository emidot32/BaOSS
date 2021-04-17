import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material';
import {Device} from '../_models/interface';

@Component({
  selector: 'app-device-info',
  templateUrl: './device-info.component.html',
  styleUrls: ['./device-info.component.css']
})
export class DeviceInfoComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: {device: Device}) { }

  ngOnInit() {
  }

}
