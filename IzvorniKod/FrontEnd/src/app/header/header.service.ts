import {Injectable, Input} from "@angular/core";
import {Subject} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
    providedIn: 'root'
})

export class HeaderService {
    active = new Subject<string>();
    currentURL = window.location.href;
    page = this.currentURL.substring(this.currentURL.lastIndexOf('/') + 1);
    logged: Subject<boolean> = new Subject<boolean>();
    user: boolean = localStorage.getItem("token") !== null;
    role: string;
    roleS: Subject<string> = new Subject<string>();
    alert: boolean = false;
    isNavOpen = new Subject<boolean>();
    isOpen: boolean = false;
    status: string;
    statusS: Subject<string> = new Subject<string>();

  constructor(private router: Router) {
      if(localStorage.getItem("user") !== null) {
        // @ts-ignore
        this.role = localStorage.getItem("user");
      } else {
        this.role = "";
      }

    if(localStorage.getItem("status") !== null) {
      // @ts-ignore
      this.status = localStorage.getItem("status");
    } else {
      this.status = "";
    }
    }

    changeActivePage(activePage: string) {
        console.log(activePage, "uspjeh");
        this.page = activePage;
        this.active.next(activePage);
    }

    userLoggedIn(data: boolean) {
        this.user = data;
        console.log(this.user, "log")
        this.logged.next(this.user);
    }

    roleChanged(role: string) {
      this.role = role;
      this.roleS.next(this.role);
    }

    statusChanged(status: string) {
      this.status = status;
      this.statusS.next(this.status);
    }

    logout() {
        localStorage.clear();
        this.isOpen = false;
        this.isNavOpen.next(this.isOpen);
        this.router.navigate(['/login'])
    }

  toggleSidebar() {
    this.isOpen = !this.isOpen;
    this.isNavOpen.next(this.isOpen);
  }
}
