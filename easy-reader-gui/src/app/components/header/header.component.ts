import {Component} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.scss']
})
export class HeaderComponent {

  private isActive: boolean = false;

  public changeDropdownState(): void {
    this.isActive = !this.isActive;
  }

}
