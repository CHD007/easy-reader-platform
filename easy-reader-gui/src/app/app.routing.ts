import { RouterModule, Routes } from '@angular/router';

import {OverviewComponent} from "./home/overview/overview.component";

const routes: Routes = [
  { path: 'home', component: OverviewComponent },
  { path: '' , redirectTo: '/home', pathMatch: 'full'}
];

export const routing = RouterModule.forRoot(routes);
