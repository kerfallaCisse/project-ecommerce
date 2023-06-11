import { Component } from '@angular/core';

@Component({
  selector: 'app-fail',
  templateUrl: './fail.component.html',
  styleUrls: ['./fail.component.css']
})
export class FailComponent {

  paymentAccepted:boolean = false;

  ngOnInit() {
    localStorage.setItem('paymentAccepted', 'false');
  }

}
