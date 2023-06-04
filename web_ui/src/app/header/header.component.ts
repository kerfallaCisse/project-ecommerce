import { Component} from '@angular/core';


let variable: boolean = false

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})


export class HeaderComponent{


  loadHeader() {
    console.log("je suis la");
    window.location.reload();
  }

}
