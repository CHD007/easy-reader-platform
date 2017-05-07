import {NgModule, ApplicationRef} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpModule} from '@angular/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {routing} from './app.routing';

import {removeNgStyles, createNewHosts} from '@angularclass/hmr';
import {HeaderComponent} from './home/header/header.component';
import {LeftMenu} from './home/left-menu/leftmenu.component';
import {DropdownComponent} from './home/header/dropdown/dropdown.component';
import {OverviewComponent} from './home/overview/overview.component';
import {HowToComponent} from "./home/howtostart/howto.component";

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    routing
  ],
  declarations: [
    AppComponent,
    OverviewComponent,
    DropdownComponent,
    HowToComponent,
    LeftMenu,
    HomeComponent,
    HeaderComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(public appRef: ApplicationRef) {
  }

  hmrOnInit(store) {
    console.log('HMR store', store);
  }

  hmrOnDestroy(store) {
    let cmpLocation = this.appRef.components.map(cmp => cmp.location.nativeElement);
    // recreate elements
    store.disposeOldHosts = createNewHosts(cmpLocation);
    // remove styles
    removeNgStyles();
  }

  hmrAfterDestroy(store) {
    // display new elements
    store.disposeOldHosts();
    delete store.disposeOldHosts;
  }
}
