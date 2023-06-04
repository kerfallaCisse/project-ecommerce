import { Component, OnInit } from '@angular/core';
import { ConfirmationService } from '../services/confirmation/confirmation.service';
import { Cart } from 'src/app/shared/models/confirmation' 



@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})



export class ConfirmationComponent {

  constructor(private confirmationService: ConfirmationService) { }


  cart_info: Cart[] = [];

  email: String = "john@gmail.com"

  formData$ = this.confirmationService.formData$;


  ngOnInit() {
    const formDataFromLocalStorage = this.confirmationService.getFormDataFromLocalStorage();
    if (formDataFromLocalStorage) {
      this.confirmationService.setFormData(formDataFromLocalStorage);
    }

    this.confirmationService.getCart(this.email).subscribe(data => {
      console.log('Cart data:', data);
      this.cart_info = Array.isArray(data) ? data : [data];
    });
  }


  onPaymentClick() {
    const formDataFromLocalStorage = this.confirmationService.getFormDataFromLocalStorage();
    if (formDataFromLocalStorage) {
      this.confirmationService.postForm(formDataFromLocalStorage);
    }
  }

  getModelTypeDisplayName(modelType: string): string {
    if (modelType === 'smallModel') {
      return '40L';
    } else if (modelType === 'largeModel') {
      return '70L';
    }
    return modelType;
  }

  getLogoDisplayName(logo: number): string {
    if (logo === 0) {
      return 'without';
    } else if (logo === 1) {
      return 'with';
    }
    return logo.toString()
  }


}
