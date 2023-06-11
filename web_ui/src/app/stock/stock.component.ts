import { Component, OnInit } from '@angular/core';
import { StockResponse, StockService } from '../services/stock/stock.service';
import { StockItem } from '../shared/models/stock';

@Component({
  selector: 'app-config',
  templateUrl: './stock.component.html',
  providers: [ StockService ],
  styleUrls: ['./stock.component.css'] // <-- ajoute cette ligne
})



export class StockComponent implements OnInit {


  quantities: number[] = []


  tab_real: {modelType: string,id: number,quantity: number,color_pocket_name: string,color_bag_name: string}[] = [];

  color: string[] = ["black","blue","red"]
  modeltype: string[] = ["small","large"]
  first = this.color[0]

  selectedColor_pocket: string = "";
  selectedColor_bag: string = "";
  model_bag: string = ""

  selectedColor_pocket_2: string = "";
  selectedColor_bag_2: string = "";
  model_bag_2: string = ""

  numberr: number = 0

  button_clicked: string = '';


  ngOnInit(): void {

    this.stockService.getConfig_real().subscribe(data2 => { // la vrai de vrai
      this.tab_real = data2
      this.sortTable();
      console.log(this.tab_real)
      this.resumeStock()
    })

    this.stockService.getQuantityOfUrl().subscribe(dataa => {
      console.log(dataa)
    })

    this.stockService.getQuantityOfUrl_custom().subscribe(dataa => {
      console.log("return custom")
      console.log(dataa)
    })

  }

  resumeStock(){

    let compteur_low = 0
    let compteur_out = 0

    for (var i = 0; i < this.tab_real.length;i++){
      if (this.tab_real[i].quantity == 0){
        compteur_out += 1
      }else if (this.tab_real[i].quantity < 5){
        compteur_low += 1
      }
    }

    console.log(compteur_low,compteur_out)
    const low_message  = document.getElementById("lowmessage");
    const out_message = document.getElementById("outmessage");

    if (out_message != null && low_message != null && (compteur_low != 0 || compteur_out != 0)){
      low_message.innerHTML = ">> " + compteur_low + " bags with low stock";
      out_message.innerHTML = ">> " +compteur_out + " bags out of stock";
    }
  }

  getStockStatus(item: {quantity: number}): string {
    if (item.quantity == 0) {
      return 'out-of-stock';
    } else if (item.quantity < 5) {
      return 'low-stock';
    }
    return '';
  }




  modifyQuantity(color_bag: string,color_pocket: string,quantiti: number,model: string ,add: boolean) {

    for (var i = 0; i < this.tab_real.length; i++) {

      if (this.tab_real[i].color_bag_name ===  color_bag && this.tab_real[i].color_pocket_name === color_pocket && this.tab_real[i].modelType === model) {
        if(add == true){
          this.tab_real[i].quantity = this.tab_real[i].quantity + +quantiti;
        } else {
          this.tab_real[i].quantity = this.tab_real[i].quantity - +quantiti;
        }
      break
      }

    }
  }

  onInputChange(event: any) {
    this.numberr = event.target.value;
  }

  onSubmitAdd() {
    if (this.model_bag !== "" && this.selectedColor_bag !== "" && this.selectedColor_pocket !== "" && this.numberr > 0) {
      this.button_clicked = 'add_button';
      this.stockService.modifiy_donne(this.model_bag, this.selectedColor_bag, this.selectedColor_pocket, this.numberr,)
      this.stockService.getConfig_real().subscribe(data2 => {
        this.tab_real = data2
        this.sortTable();
        console.log(this.tab_real)
        this.resumeStock()
      })

      window.location.reload();
      console.log(typeof this.model_bag, this.selectedColor_bag, this.selectedColor_pocket, typeof this.numberr)
    }
  }

  onSubmitRemove() {
    if (this.model_bag_2 !== "" && this.selectedColor_bag_2 !== "" && this.selectedColor_pocket_2 !== "" && this.numberr > 0) {
      this.button_clicked = 'remove_button';
      this.stockService.modifiy_donne(this.model_bag_2, this.selectedColor_bag_2, this.selectedColor_pocket_2, -this.numberr,)
      this.stockService.getConfig_real().subscribe(data2 => {
        this.tab_real = data2
        this.sortTable();
        console.log(this.tab_real)
        this.resumeStock()
      })

      window.location.reload();
      console.log(typeof this.model_bag, this.selectedColor_bag, this.selectedColor_pocket, typeof this.numberr)
    }
  }


  sortTable() {
    this.tab_real.sort((a, b) => {
      if (a.modelType > b.modelType) {
        return -1;
      } else if (a.modelType < b.modelType) {
        return 1;
      } else {
        if (a.color_bag_name < b.color_bag_name) {
          return -1;
        } else if (a.color_bag_name > b.color_bag_name) {
          return 1;
        } else {
          if (a.color_pocket_name < b.color_pocket_name) {
            return -1;
          } else if (a.color_pocket_name > b.color_pocket_name) {
            return 1;
          } else {
            return 0;
          }
        }
      }
    });
  }

  getModelTypeDisplayName(modelType: string): string {
    if (modelType === 'SmallModel') {
      return 'small';
    } else if (modelType === 'LargeModel') {
      return 'large';
    }
    return modelType;
  }


  constructor(private stockService: StockService) {}

}














