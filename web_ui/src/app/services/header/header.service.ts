import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { cart_array } from '../cart/cart.service';





@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(private http: HttpClient) { }
  get_panier_john(){
    return this.http.get<cart_array>('/api/statistics/cart?email=john@gmail.com')
  }
}
