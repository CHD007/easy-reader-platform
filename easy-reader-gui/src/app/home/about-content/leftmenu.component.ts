import {Component} from '@angular/core';

@Component({
  selector: 'left-menu',
  templateUrl: 'leftmenu.component.html',
  styleUrls: ['leftmenu.component.scss']
})
export class LeftMenu {

  private isAboutActive: boolean = false;


  public changeAbout(): void {
    this.isAboutActive = !this.isAboutActive;
    console.log(this.isAboutActive);
  }

}
