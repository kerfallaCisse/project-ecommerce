import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export interface Cart {
  modelType: string
  bagColor: string
  pocketColor: string
  image: string
  logo: string 
  quantity:number
}

export type cart_array = [];

@Injectable({
  providedIn: 'root'
})


export class CartService {


  constructor(private http: HttpClient) { }
  get_panier_john(){
    return this.http.get<cart_array>('/api/statistics/cart?email=john@gmail.com')
  }
}
