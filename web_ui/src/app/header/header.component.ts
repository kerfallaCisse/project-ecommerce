import { Component} from '@angular/core';


let variable: boolean = false

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})


export class HeaderComponent{

  func_reload_header() {
    window.location.href = "/";
    window.location.reload();
  }

  func_reload_about() {
    window.location.href = "/about";
    window.location.reload();
  }
}
