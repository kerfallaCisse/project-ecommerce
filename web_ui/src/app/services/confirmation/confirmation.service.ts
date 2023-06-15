import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { FormInformations } from 'src/app/shared/models/form';
import { HttpClient } from '@angular/common/http';
import { Cart, Product, ProductJson, PaymentResponse } from 'src/app/shared/models/confirmation'



@Injectable({
  providedIn: 'root',
})


export class ConfirmationService {

  constructor(private http: HttpClient) { }

  private formDataSubject = new BehaviorSubject<FormInformations | null>(this.getFormDataFromLocalStorage());
  formData$ = this.formDataSubject.asObservable();


  setFormData(data: FormInformations) {
    localStorage.setItem('formData', JSON.stringify(data));
    this.formDataSubject.next(data);
  }

  getFormDataFromLocalStorage(): FormInformations | null {
    const formDataString = localStorage.getItem('formData');
    if (formDataString) {
      return JSON.parse(formDataString);
    }
    return null;
  }

  getCart(mail: String) {
    const cartEndpointURL = `/api/statistics/cart?email=${mail}`;
    return this.http.get(cartEndpointURL)
  }

  postForm(data:FormInformations) {
    const confirmationFormEndpointURL = '/api/statistics/cart/confirmation'
    this.http.post<FormInformations>(confirmationFormEndpointURL, data).subscribe(response => {
      console.log(response,"postForm")
    })
  }

  postCart(cart: ProductJson) {
    const confirmationCartFormEndpointURL = '/api/payment';
    return this.http.post<PaymentResponse>(confirmationCartFormEndpointURL, cart);
  }
}

