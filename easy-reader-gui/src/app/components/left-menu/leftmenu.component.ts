import {Component} from '@angular/core';

@Component({
  selector: 'left-menu',
  templateUrl: 'leftmenu.component.html',
  styleUrls: ['leftmenu.component.scss']
})
export class LeftMenu {

  private isAboutActive: boolean = true;
  private toogledLink: number = 1;


  public changeAbout(): void {
    this.isAboutActive = !this.isAboutActive;
  }

  public toogleLink(value:number){
    this.toogledLink = value;
  }


}


