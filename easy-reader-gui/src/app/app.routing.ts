import { RouterModule, Routes } from '@angular/router';

import {OverviewComponent} from "./home/overview/overview.component";

const routes: Routes = [
  { path: '', component: OverviewComponent },
];

export const routing = RouterModule.forRoot(routes);
