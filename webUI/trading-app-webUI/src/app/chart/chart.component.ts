import { stringify } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { CurrencyService } from '../currency-service';
import { DataPointDto } from '../data-point-dto';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  multi: any[] = []
  view:[number,number] = [700, 600];

  // options
  autoScale: boolean = true;
  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Year';
  yAxisLabel: string = 'Population';
  timeline: boolean = true;

  colorScheme = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5']
  }; 

  constructor(private service:CurrencyService) {
    service.getData().subscribe(data => this.loadData(data))
  }

  private loadData(points:DataPointDto[]){
    var dataset = {
      name:"EURUSD",
      series:points
    }
    var processed = JSON.parse(JSON.stringify(dataset))
    this.multi = [processed]
  }

  onSelect(data: any): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data: any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data: any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

  ngOnInit(): void {
  }
}
