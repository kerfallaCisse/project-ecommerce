import { Component, Inject,OnInit} from '@angular/core';

// Import the AuthService type from the SDK
import { AuthService } from '@auth0/auth0-angular';
import { DOCUMENT } from '@angular/common';


@Component({
  selector: 'app-auth-button',
  template: `
    <ng-container *ngIf="auth.isAuthenticated$ | async; else loggedOut">
      <button (click)="auth.logout({ logoutParams: { returnTo: document.location.origin } })">
        Log out
      </button>
    </ng-container>

    <ng-template #loggedOut>
      <button (click)="auth.loginWithRedirect()">Log in</button>
    </ng-template>
  `,
  styles: [],
})
export class LoginComponent implements OnInit{

  ngOnInit(): void {
    console.log(this.auth.isAuthenticated$)
  }
  
  // Inject the authentication service into your component through the constructor
  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService) {}
}


// import { Component, OnInit } from '@angular/core';
// import { Router } from '@angular/router';
// @Component({
//   selector: 'app-login',
//   templateUrl: './login.component.html',
//   styleUrls: ['./login.component.css']
// })
// export class LoginComponent {
//   username!:string;
//   password!:string;
//   constructor(private router: Router){}

//   onButtonClick(): void {
//     if (this.username == "Ethan" && this.password == "arm") {
//       localStorage.setItem('inputValue', this.username);
//       this.router.navigate(['/precision']);
//     }
    
//   }
//   LoginUser(){
//     if (!this.username) {
//       console.log('L\'entrée est vide.');
//     }
//     if(this.username == "Ethan" && this.password == "arm")
//     {
//       console.log("welcome")
//     }
//   }
// }

