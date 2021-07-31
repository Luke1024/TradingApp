import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { ChartComponent } from './chart/chart.component';
import { HowToComponent } from './how-to/how-to.component';
import { TradingViewComponent } from './trading-view/trading-view.component';

const routes: Routes = [
  { path: 'main', component: ChartComponent },
  { path: 'trading', component: TradingViewComponent },
  { path: 'howTo', component: HowToComponent },
  { path: 'about', component: AboutComponent },
  { path: '**', component: ChartComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
