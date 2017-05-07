import { RouterModule, Routes } from '@angular/router';

import {OverviewComponent} from "./home/overview/overview.component";
import {HowToComponent} from "./home/howtostart/howto.component";

const routes: Routes = [
  { path: 'home', component: OverviewComponent },
  { path: 'start', component: HowToComponent },
  { path: '' , redirectTo: '/home', pathMatch: 'full'}
];

export const routing = RouterModule.forRoot(routes);
