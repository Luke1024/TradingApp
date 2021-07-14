import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor() { }

  model = "howTo";

  ngOnInit(): void {}

  onChange(): void {
    console.log(this.model)
  }
}
