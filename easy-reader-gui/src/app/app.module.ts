import {NgModule, ApplicationRef} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpModule} from '@angular/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {HomeComponent} from './components/home.component';
import {routing} from './app.routing';

import {removeNgStyles, createNewHosts} from '@angularclass/hmr';
import {HeaderComponent} from './components/header/header.component';
import {LeftMenu} from './components/left-menu/leftmenu.component';
import {DropdownComponent} from './components/header/dropdown/dropdown.component';
import {OverviewComponent} from './components/overview/overview.component';
import {HowToComponent} from "./components/howtostart/howto.component";
import {BookLibraryComponent} from "./components/book-upload/booklibrary.component";
import {DataService} from "./services/data.service";

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
    BookLibraryComponent,
    LeftMenu,
    HomeComponent,
    HeaderComponent
  ],
  providers: [DataService],
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
    // display newWords elements
    store.disposeOldHosts();
    delete store.disposeOldHosts;
  }
}
