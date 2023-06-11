import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Colors, StockInformations } from 'src/app/shared/models/success';
import { FormInformations } from 'src/app/shared/models/form';



@Injectable({
  providedIn: 'root'
})
export class SuccessService {

  constructor(private http: HttpClient) { }


  getCart(mail: String) {
    const cartEndpointURL = `/api/statistics/cart?email=${mail}`;
    return this.http.get(cartEndpointURL)
  }

  getFormDataFromLocalStorage(): FormInformations | null {
    const formDataString = localStorage.getItem('formData');
    if (formDataString) {
      return JSON.parse(formDataString);
    }
    return null;
  }

  postForm(data:FormInformations) {
    const confirmationFormEndpointURL = '/api/statistics/cart/confirmation'
    this.http.post<FormInformations>(confirmationFormEndpointURL, data).subscribe(response => {
      console.log(response,"postForm")
    })
  }

  postColors(color: Colors) {
    const popularColorsEndpointURL = '/api/statistics/colors';
    this.http.post<Colors>(popularColorsEndpointURL, color).subscribe(response => {
      console.log(response,"bien post")
    })
  }

  postAmount(amount: number) {
    const updateAmountEndpointURL = 'api/payment/update_amount';
    const body = { amount: amount };
    this.http.post(updateAmountEndpointURL, body).subscribe(response => {
      console.log(response,"bien put")
    })
  }

  postStock(stock: StockInformations){
    const updateStockEndpointURL = '/api/stock';
    this.http.post<StockInformations>(updateStockEndpointURL, stock).subscribe(response => {
      console.log(response,"bien post2")
    })
  }

  deleteCart(email:String){
    console.log(email)
    const updateStockEndpointURL = '/api/statistics/cart/payment?email='+email;
    this.http.delete(updateStockEndpointURL).subscribe(data => {
      console.log(data);
    });
  }
}
