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

  constructor(private router: Router) {
      if(localStorage.getItem("user") !== null) {
        // @ts-ignore
        this.role = localStorage.getItem("user");
      } else {
        this.role = "";
      }
    }

    changeActivePage(activePage: string) {
        console.log(activePage, "uspjeh");
        this.page = activePage;
        this.active.next(activePage);
    }

    userLoggedIn() {
        this.user = !this.user;
        this.logged.next(this.user);
    }

    roleChanged(role: string) {
      this.role = role;
      this.roleS.next(this.role);
    }

    logout() {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        this.router.navigate(['/login'])
    }
}