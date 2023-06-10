import { Component,OnInit,Inject} from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Auth0Client } from '@auth0/auth0-spa-js';
import { DOCUMENT } from '@angular/common';
import { BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';

@Component({
     selector: 'app-login',
     templateUrl: './login.component.html',
     styleUrls: ['./login.component.css']
   })

export class LoginComponent implements OnInit {
  
  isAuthenticated: boolean | undefined = undefined;
  
  constructor(
    public auth: AuthService,
    @Inject(DOCUMENT) private doc: Document
  ) {}  

    loginWithRedirect(): void {
      this.auth.loginWithRedirect();
      console.log("onestla")
    }

    
  ngOnInit(): void {
    
    function logout(this: any): void {
      this.auth.logout({ returnTo: this.doc.location.origin });
    }

  

    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      this.isAuthenticated = isAuthenticated;
      //console.log('Authentifié :', isAuthenticated);
  })
  console.log(this.auth.isAuthenticated$)

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

