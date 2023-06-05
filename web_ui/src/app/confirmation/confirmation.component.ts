import { Component, OnInit } from '@angular/core';
import { ConfirmationService } from '../services/confirmation/confirmation.service';
import { Cart, Product, ProductJson } from 'src/app/shared/models/confirmation';




@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})



export class ConfirmationComponent {

  constructor(private confirmationService: ConfirmationService) { }

  cart_info: Cart[] = [];
  productJson!: ProductJson;

  email: String = "john@gmail.com";

  formData$ = this.confirmationService.formData$;

  small_models: number = 0;
  large_models: number = 0;
  logos: number = 0;


  ngOnInit() {
    const formDataFromLocalStorage = this.confirmationService.getFormDataFromLocalStorage();
    if (formDataFromLocalStorage) {
      this.confirmationService.setFormData(formDataFromLocalStorage);
    }

    this.confirmationService.getCart(this.email).subscribe(data => {
      console.log('Cart data:', data);
      this.cart_info = Array.isArray(data) ? data : [data];

      for (const item of this.cart_info) {
        if (item.modelType === 'smallModel') {
          this.small_models += item.quantity;
        } else if (item.modelType === 'largeModel') {
          this.large_models += item.quantity;
        }

        if (item.logo === 1) {
          this.logos += item.quantity;
        }
      }

      this.productJson = {
        products: [
          {
            prodId: 'prod_O1FT1XFDOKRsUC',
            quantity: this.logos
          },
          {
            prodId: 'prod_O1FSw1MDp2WNIT',
            quantity: this.large_models
          },
          {
            prodId: 'prod_O1FSPgFMsu8V1o',
            quantity: this.small_models
          }
        ]
      };

      console.log('Product JSON:', this.productJson);
    });

  }

  onPaymentClick() {
    const formDataFromLocalStorage = this.confirmationService.getFormDataFromLocalStorage();
    if (formDataFromLocalStorage) {
      this.confirmationService.postForm(formDataFromLocalStorage);
    }
    console.log('salut', this.productJson)
    this.confirmationService.postCart(this.productJson)
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
