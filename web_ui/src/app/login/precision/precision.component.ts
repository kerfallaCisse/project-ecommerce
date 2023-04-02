import { Component } from '@angular/core';

@Component({
  selector: 'app-precision',
  templateUrl: './precision.component.html',
  styleUrls: ['./precision.component.css']
})
export class PrecisionComponent {
  savedValue = localStorage.getItem('inputValue');

}
