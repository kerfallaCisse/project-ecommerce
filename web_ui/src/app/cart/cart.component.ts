import { Component , OnInit} from '@angular/core';
import {CartService} from '../services/cart/cart.service';
import { Item } from '../services/stock/stock.service';
import { Item2, Item3} from '../shared/models/cart';




@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit{

  tout_panier: Item2[] = [];
  tout_modif: Item2[] = [];
  tableau_price: number[] = []
  nouv: Item3[] = [];
  price_final: number = 0

  
  

  constructor(private cartService: CartService) {}

  know_price(){

  
    let sum:number = 0
  // for (let i = 0; i < this.tout_panier.length; i++) {
  //   const item = this.tout_panier[i];
  //   if(item.modelType == "smallModel"){

  //     quantitey.push(item.quantity*130);

  //   }else if (item.modelType =="largeModel"){

  //     quantitey.push(item.quantity*150)
  //   }
  const tableauItem3: Item3[] = this.tout_panier.map((item2) => {
    const quantity = item2.quantity;
    let modelType = item2.modelType // RAJOUTER TOUS LE RESTE 
    const logo = item2.logo
    const image = item2.image
    const pocketColor = item2.pocketColor
    const bagColor = item2.bagColor

    let price: number;

  if (item2.modelType == "smallModel") {
    modelType = "40L"
    price = quantity * 130;
    sum += price
    
  } else {
    modelType = "60L"
    price = quantity * 150;
    sum += price

  }

  return {modelType,bagColor,pocketColor,logo,image, quantity, price };
});
  
  //console.log(tableauItem3)
  this.price_final = sum
  console.log(this.price_final)
  return tableauItem3
  }


  func_for_modif(){
    let updatedItems = this.tout_panier.map(item => {
      
      if (item.modelType === "smallModel") {
        return { ...item, modelType: "40L",bagColor: item.bagColor.toUpperCase(),pocketColor: item.pocketColor.toUpperCase() };
      } else if (item.modelType === "largeModel") {
        return { ...item, modelType: "50L",  bagColor: item.bagColor.toUpperCase(),pocketColor: item.pocketColor.toUpperCase()};
      } 
      const logoStatus = item.logo === 0 ? "No" : "Yes";

      return { ...item, bagColor: item.bagColor.toUpperCase(),logo:logoStatus };

    });
    
  }

  ngOnInit(): void {
    



    this.cartService.get_panier_john().subscribe(data2 => { 
      
      
      this.tout_panier = data2
      //console.log(this.tout_panier)
      this.func_for_modif()
      //console.log(this.tout_panier)
      this.nouv = this.know_price()
      console.log(this.nouv)
      // Compute Total Price
      var subtotalElement = document.getElementById("subtotal");
      subtotalElement!.textContent = "Nouvelle valeur";
      subtotalElement!.textContent = String(this.price_final);
    })

      
 
  

  
 

  }
}
