import { RouterModule, Routes } from '@angular/router';

import {OverviewComponent} from "./components/overview/overview.component";
import {HowToComponent} from "./components/howtostart/howto.component";
import {BookLibraryComponent} from "./components/book-upload/booklibrary.component";

const routes: Routes = [
  { path: 'home', component: OverviewComponent },
  { path: 'start', component: HowToComponent },
  { path: 'library', component: BookLibraryComponent },
  { path: '' , redirectTo: '/home', pathMatch: 'full'}
];

export const routing = RouterModule.forRoot(routes);
