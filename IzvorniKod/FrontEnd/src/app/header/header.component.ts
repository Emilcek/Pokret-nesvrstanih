import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  @Input() activePage!: string;

  changeActivePage(activePage: string) {
    this.activePage = activePage;
  }
}
