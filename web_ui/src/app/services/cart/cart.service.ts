import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { AbandBag, DeleteBag } from 'src/app/shared/models/cart';


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

  deleteBagFromCart(url:string){
    const data: DeleteBag = {image: url};
    this.http.put('/api/statistics/cart/basket_quantity',data).subscribe(data => {
      console.log(data);
    });
  }

  postAbandonnedBags(modelType:string){
    const data: AbandBag = {modelType: modelType};
    this.http.post('/api/statistics/abandoned_basket',data).subscribe(data => {
      console.log(data);
    });
  }

  getBasket(){
    return this.http.get<cart_array>('/api/statistics/cart?email=john@gmail.com')
  }
}
