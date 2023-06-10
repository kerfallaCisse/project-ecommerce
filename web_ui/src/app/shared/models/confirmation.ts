export interface Cart {
  modelType: string
  bagColor: string
  pocketColor: string
  image: string
  logo: number
  quantity:number
}

export interface Product {
  prodId: string;
  quantity: number;
}

export interface ProductJson {
  products: Product[];
}

export interface PaymentResponse {
  amount: number;
  url: string;
}
