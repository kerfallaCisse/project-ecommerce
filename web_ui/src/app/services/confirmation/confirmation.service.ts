import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { FormInformations } from 'src/app/shared/models/form';
import { HttpClient } from '@angular/common/http';
import { Cart } from 'src/app/shared/models/confirmation' //temporaire



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
    const confirmationEndpointURL = '/api/statistics/cart/confirmation'
    console.log("la",data)
    this.http.post<FormInformations>(confirmationEndpointURL, data).subscribe(response => {
      console.log(response,"sal")
    })
  }

}

