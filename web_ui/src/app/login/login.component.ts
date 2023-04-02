import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username!:string;
  password!:string;
  constructor(private router: Router){}

  onButtonClick(): void {
    if (this.username == "Ethan" && this.password == "arm") {
      localStorage.setItem('inputValue', this.username);
      this.router.navigate(['/precision']);
    }
    
  }
  LoginUser(){
    if (!this.username) {
      console.log('L\'entrée est vide.');
    }
    if(this.username == "Ethan" && this.password == "arm")
    {
      console.log("welcome")
    }
  }
}

