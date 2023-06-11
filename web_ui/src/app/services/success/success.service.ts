import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Colors, StockInformations } from 'src/app/shared/models/success';



@Injectable({
  providedIn: 'root'
})
export class SuccessService {

  constructor(private http: HttpClient) { }

  getCart(mail: String) {
    const cartEndpointURL = `/api/statistics/cart?email=${mail}`;
    return this.http.get(cartEndpointURL)
  }

  postColors(color: Colors) {
    const popularColorsEndpointURL = '/api/statistics/colors';
    this.http.post<PaymentResponse>(popularColorsEndpointURL, color).subscribe(response => {
      console.log(response,"bien post")
    })
  }

  putAmount(amount: number) {
    const updateAmountEndpointURL = 'api/payment/update_amount';
    const body = { amount: amount };
    console.log(body)
    this.http.put(updateAmountEndpointURL, body).subscribe(response => {
      console.log(response,"bien put")
    })
  }

  postStock(stock: StockInformations){
    const updateStockEndpointURL = '/api/stock';
    this.http.post<PaymentResponse>(updateStockEndpointURL, stock).subscribe(response => {
      console.log(response,"bien post2")
    })
  }
}
