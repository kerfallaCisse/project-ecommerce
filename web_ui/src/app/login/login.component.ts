import { Component,OnInit} from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Auth0Client } from '@auth0/auth0-spa-js';


@Component({
     selector: 'app-login',
     templateUrl: './login.component.html',
     styleUrls: ['./login.component.css']
   })

export class LoginComponent implements OnInit{

  isAuthenticated: boolean | undefined = undefined;
  
    constructor( public auth: AuthService) {}
    
  ngOnInit(): void {
    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      this.isAuthenticated = isAuthenticated;
      console.log('Authentifié :', isAuthenticated);
  })

  }
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

