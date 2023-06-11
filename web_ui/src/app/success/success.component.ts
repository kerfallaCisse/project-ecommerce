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

  ngOnInit() {
    this.successService.getCart(this.email).subscribe(data => {
      console.log('Cart data:', data);
      this.cart_info = Array.isArray(data) ? data : [data];

      for (const item of this.cart_info) {


        const colorData: Colors = {
          pocket: item.pocketColor,
          bag: item.bagColor,
          quantity: item.quantity
        };
        this.successService.postColors(colorData)


        const stockData: StockInformations = {
          modelType:  item.modelType,
          color_pocket_name: item.pocketColor,
          color_bag_name: item.bagColor,
          quantity: - item.quantity,
        }
        this.successService.postStock(stockData)


        if(item.modelType== "smallModel"){
          this.amount += item.quantity*(130 + 30 * item.logo)
        }else if(item.modelType== "largeModel"){
          this.amount += item.quantity*(150 + 30 * item.logo)
        }
      }
      console.log(this.amount)
      this.successService.putAmount(this.amount)



    });
  }
}
