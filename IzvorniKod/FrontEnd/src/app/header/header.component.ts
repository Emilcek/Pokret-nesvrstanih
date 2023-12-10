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
  @Input() user!: string;
  @Input() isLoggedIn!: boolean;
  pageChange: Subscription = new Subscription();
  loggingChange: Subscription = new Subscription();
  roleChange: Subscription = new Subscription();
  isNavOpen: boolean = false;

  constructor(public headerService: HeaderService) {
  }

  ngOnInit() {
    let url = window.location.href;
    let page = url.substring(url.lastIndexOf('/'));

    this.headerService.changeActivePage(page);

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
    this.roleChange = this.headerService.roleS
      .subscribe(
        (role: string) => {
          this.user = role;
        }
      )
  }

    changeActivePage(activePage: any) {
        if(this.isNavOpen) {
          this.toggleNav()
        }
        this.headerService.changeActivePage(activePage);
        this.activePage = this.headerService.page;
    }

    logout() {
        this.activePage = "/login";
        this.headerService.logout();
        this.isNavOpen = false;
        this.headerService.userLoggedIn(false);
    }

  toggleNav() {
    this.headerService.toggleSidebar();
    this.isNavOpen = this.headerService.isOpen;
  }
}
