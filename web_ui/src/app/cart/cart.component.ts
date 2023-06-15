import { Component , OnInit} from '@angular/core';
import {CartService} from '../services/cart/cart.service';
import { Item } from '../services/stock/stock.service';
import { Item2, Item3} from '../shared/models/cart';
//import {HeaderComponent} from 'src/app/header/header.component'
import { BehaviorSubject } from 'rxjs';
import { toHalfFloat } from 'three/src/extras/DataUtils';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit{

  full_cart_client: Item2[] = [];
  full_cart_client_modifier: Item3[] = [];
  price_final: number = 0
  quantity: number[] = []
  nbr_total_bag_in_cart = 0
  cartQuantity: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  shipping: number = 0;


  constructor(private cartService: CartService) {}

  getLogoDisplayName(logo: number): string {
    if (logo === 0) {
      return 'without';
    } else if (logo === 1) {
      return 'with';
    }
    return logo.toString()
  }

  removeBag(url:string, modelType:string){
    this.cartService.deleteBagFromCart(url)
    this.cartService.postAbandonnedBags(modelType)
    window.location.reload();
  }

  getCartQuantity() {
    return this.cartQuantity.asObservable();
  }


  computePriceArticle() {
    let sum: number = 0;

    const tableauItem3: Item3[] = this.full_cart_client.map((item2) => {
      const quantity = item2.quantity;
      let modelType = item2.modelType;
      const logo = item2.logo;
      const image = item2.image;
      const pocketColor = item2.pocketColor;
      const bagColor = item2.bagColor;
      this.quantity.push(quantity);
      let price: number;

      if (item2.modelType == "smallModel") {
        modelType = "40L";
        price = quantity * 130;
      } else {
        modelType = "70L";
        price = quantity * 150;
      }

      if (logo === 1) {
        price += 30 * quantity;
      }

      sum += price;
      return { modelType, bagColor, pocketColor, logo, image, quantity, price };
    });

    this.price_final = sum;
    return tableauItem3;
  }

  afficher(){
    alert("removed !")
  }

  updateTotalItemsInCart(): void {
    this.nbr_total_bag_in_cart = 0;
    for (let i = 0; i < this.quantity.length; i++) {
      this.nbr_total_bag_in_cart += this.quantity[i];
    }
  }

  ngOnInit(): void {
    this.cartService.getBasket().subscribe(data => {
      this.full_cart_client = data;

      this.full_cart_client_modifier = this.computePriceArticle();

      this.updateTotalItemsInCart();
    });
  }

}
