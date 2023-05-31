import { Injectable } from '@angular/core';
import { My3DScene } from 'src/app/customization/customization.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';


export interface Cart {
  email: string,
  modelType: string
  bagColor: string
  pocketColor: string
  image: string
  logo: number 
}

@Injectable({
  providedIn: 'root'
})



export class CustomizationService {

  constructor(private http: HttpClient) { }
  private modelInstance: My3DScene | null = null;
  public isVisible: boolean = false;


  getQuantityOfUrl(model:String,bagcolor:string,pocketColor:string){
    return this.http.get<number>('/api/stock/quantity?modelType='+model+'&bagColor='+ bagcolor + '&pocketColor='+pocketColor)
  }

    // POST PRÊT POUR KERFALLA 
  make_post_for_cart(email: string,modelType: string,pocketColor: string,bagColor: string,image: string,logo: number ){
    const data_cart: Cart = {
      email: email,
      modelType: modelType,
      bagColor: bagColor,
      pocketColor: pocketColor,
      image: image,
      logo: logo,
    }
  
  }


  createModel(): void {
    if (!this.modelInstance) {
      this.modelInstance = new My3DScene();
      this.modelInstance.render();
    }
  }

  removeModel(): void {
    if (this.modelInstance) {
      
      this.modelInstance = null;
    }
  }

  getModelInstance(): My3DScene | null {
    return this.modelInstance;
  }
}

