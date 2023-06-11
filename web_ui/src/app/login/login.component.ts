import { Component,OnInit,Inject} from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { DOCUMENT } from '@angular/common';
import { BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import {Auth} from 'src/app/auth.service'
import {createAuth0Client,  Auth0Client } from '@auth0/auth0-spa-js';
import { environment } from '../environnement';
import { AuthModule } from '@auth0/auth0-angular';

@Component({
     selector: 'app-login',
     templateUrl: './login.component.html',
     styleUrls: ['./login.component.css']
   })

export class LoginComponent {
  private auth0Client: Auth0Client | undefined;
  isAuthenticated = false;

  constructor() {
    this.initializeAuth0();
  }

  private async initializeAuth0() {
    this.auth0Client = await createAuth0Client({
      domain:'dev-xuzmuq3g0kbtxrc4.us.auth0.com',
      clientId: 'ulU0Nnga1ilqIpGjcjXLkHSvNkplcYW6',
    });
    this.isAuthenticated = await this.auth0Client.isAuthenticated();
  }

  async login() {
    await this.auth0Client?.loginWithRedirect();
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

