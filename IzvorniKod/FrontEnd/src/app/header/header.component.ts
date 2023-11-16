import {Component, Input, OnInit} from '@angular/core';
import {HeaderService} from "./header.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  @Input() activePage!: string;
  @Input() isLoggedIn!: boolean;
  pageChange: Subscription = new Subscription();
  loggingChange: Subscription = new Subscription();

  constructor(public headerService: HeaderService) {
  }

  ngOnInit() {
    this.pageChange = this.headerService.active
        .subscribe(
            (active: string) => {
              this.activePage = active;
            }
        )
    this.loggingChange = this.headerService.logged
        .subscribe(
            (logged: boolean) => {
              this.isLoggedIn = logged;
              console.log(this.isLoggedIn, "log")
            }
        )
  }

    changeActivePage(activePage: any) {
        this.headerService.changeActivePage(activePage);
        this.activePage = this.headerService.page;
    }

    logout() {
        this.activePage = "/login";
        this.headerService.logout();
        this.headerService.userLoggedIn();
    }
}
