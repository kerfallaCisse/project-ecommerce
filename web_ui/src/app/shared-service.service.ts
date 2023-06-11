import { Injectable } from '@angular/core';
import { CartComponent } from './cart/cart.component';
@Injectable({
  providedIn: 'root'
})
export class SharedServiceService {

  constructor( public cartComponent: CartComponent) {}
  
}
