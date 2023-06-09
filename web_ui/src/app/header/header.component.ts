import { AfterViewInit, Component,OnInit} from '@angular/core';
import { CartComponent } from '../cart/cart.component';
import { HeaderService } from '../services/header/header.service';
import {Item2} from '../shared/models/header';
import { Item } from '../services/stock/stock.service';


let variable: boolean = false

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})


export class HeaderComponent implements AfterViewInit{

  full_cart_client: Item2[] = [];
  theQuantity = 0

  computeQuantity(panier:Item2[]){
    var quantite = 0
    for (let i = 0;i<panier.length;i++){
      quantite = quantite + panier[i].quantity;
    }
    return quantite
  }


  constructor(private headerService: HeaderService) {}

  ngAfterViewInit(): void {
    this.headerService.get_panier_john().subscribe(data2 => { 

      this.full_cart_client = data2
      
      
      this.theQuantity = this.computeQuantity(this.full_cart_client)

      var subtotalElement = document.getElementById("numbre_in_cart");
      if(this.theQuantity > 0){
      subtotalElement!.textContent = String(this.computeQuantity(this.full_cart_client));
      document.getElementById("border_red")!.style.display = "flex";
      }
      
    })
  
  }
  
  

  loadHeader() {
    console.log("je suis la");
    window.location.reload();
  }

  

 
  //   await this.delay(1000)

   
  //  console.log(this.cartQuantity)

   
  // 
    

   


}
