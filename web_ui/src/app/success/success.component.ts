import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SuccessService } from '../services/success/success.service';
import { Cart } from 'src/app/shared/models/confirmation';
import { Colors, StockInformations } from 'src/app/shared/models/success';


@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent {

  constructor(private successService: SuccessService) { }

  email: String = "john@gmail.com";
  cart_info: Cart[] = [];
  amount: number = 0;

  model!:string;

  ngOnInit() {

    const paymentAccepted = localStorage.getItem('paymentAccepted') === 'true';


    if (paymentAccepted) {

      localStorage.setItem('paymentAccepted', 'false');

      const formDataFromLocalStorage = this.successService.getFormDataFromLocalStorage();
      if (formDataFromLocalStorage) {
        this.successService.postForm(formDataFromLocalStorage);
      }


      this.successService.getCart(this.email).subscribe(data => {
        this.cart_info = Array.isArray(data) ? data : [data];

        for (const item of this.cart_info) {

          //for colors and orders update
          const colorData: Colors = {
            pocket: item.pocketColor,
            bag: item.bagColor,
            quantity: item.quantity
          };
          this.successService.postColors(colorData)

          //for total profit update
          if(item.modelType== "smallModel"){
            this.amount += item.quantity*(130 + 30 * item.logo)
            this.model = "small";
          }else if(item.modelType== "largeModel"){
            this.amount += item.quantity*(150 + 30 * item.logo)
            this.model = "large";
          }

          //for stock update
          const stockData: StockInformations = {
            modelType:  this.model,
            color_pocket_name: item.pocketColor,
            color_bag_name: item.bagColor,
            quantity: -item.quantity,
          };
          this.successService.postStock(stockData)

        }

        this.successService.postAmount(this.amount)

        //for basket update
        this.successService.deleteCart(this.email)

      });
    } else {
      // console.log("not payed")
    }
  }

}
