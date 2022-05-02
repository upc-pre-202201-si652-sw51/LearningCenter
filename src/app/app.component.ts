import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "./security/services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'learning-center';

  constructor(private router: Router,
              private authService: AuthService) {}

  getCurrentUserEmail() {
    let currentUserString = this.authService.getCurrentUser();
    if (currentUserString) {
      console.log(`current user: ${ currentUserString}`);
      let currentUser = JSON.parse(currentUserString);
      console.log(currentUser);
      return currentUser.email;
    } else {
      return null;
    }
  }

  signOut() {
    this.authService.signOut();
    this.router.navigate(['home']).then();
    console.log("Signed Out");
  }
}
