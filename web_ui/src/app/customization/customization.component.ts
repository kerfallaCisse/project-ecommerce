import * as THREE from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import {Component,OnInit, OnDestroy, HostListener , AfterViewInit} from '@angular/core';
import {CustomizationService} from '../services/customization/customization.service';
import { Color, MeshStandardMaterial } from 'three';
import { HttpClient } from '@angular/common/http';




let number = 0;
let variable: boolean = false
let changer_sac = false


@Component({
  selector: 'app-customization',
  templateUrl: './customization.component.html',
  styleUrls: ['./customization.component.css'],
  providers: [CustomizationService]
})


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------

export class CustomizationComponent implements OnInit {

  constructor(private customizationService: CustomizationService, private http: HttpClient) {}

  userChoice: string[] = [];

  selectedSize: number = 40;
  selectedBagColor: string = '#0F0F0F';
  selectedPocketColor: string = '#000060';
  loadedFile:number = 0;

  bagQuantity: number = 1;
  stockQuantity!: number;

  price:number = 130;

  private my3DScene: My3DScene | undefined;

  fileToUpload: File | null = null;
  fileInput = document.getElementById("fileInput");

  email: string = "john@gmail.com";




  ngOnInit(){

    this.userChoice = ["smallModel", "black", "blue"]

    if(variable === false){
      this.my3DScene = new My3DScene();
      this.my3DScene.render();
      variable = true
      number += 1
    }else{
      window.location.reload()
      this.cleanScene()
      this.my3DScene?.loadGLTFModel('assets/assets_3d/smallModel.glb', this.selectedBagColor, this.selectedPocketColor);
    }

    this.changeBackgroundColor('#d2d7d2');
    const width = window.innerWidth * 2.5;
    const height = window.innerHeight * 4;
    this.resizeScene(width, height);

    window.addEventListener('resize', () => this.onWindowResize());
  }


  ngOnDestroy() {
    window.removeEventListener('resize', this.onWindowResize);
  }



  //pour gérer les fichiers pour le logo
  handleFileInput(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput && fileInput.files && fileInput.files.length > 0) {
      const file: File = fileInput.files[0];
      this.fileToUpload = file;
      this.price += 30*this.bagQuantity;
      this.loadedFile = 1;
    }
  }


  // change la taille du sac quand l'utilisateur clique sinon c'est 40L par défault
  changeBagSize(taille:number) {

    this.selectedSize = taille;

    if(taille===40){

      this.userChoice[0] = "smallModel";
      this.price = (130 + 30*this.loadedFile) * this.bagQuantity;

      this.cleanScene();
      this.my3DScene?.loadGLTFModel('assets/assets_3d/smallModel.glb', this.selectedBagColor, this.selectedPocketColor);


    } else if(taille===70) {

      this.userChoice[0] = "largeModel";
      this.price = (150 + 30*this.loadedFile) * this.bagQuantity;

      this.cleanScene();
      this.my3DScene?.loadGLTFModel('assets/assets_3d/largeModel.glb',this.selectedBagColor, this.selectedPocketColor);

    }
  }


  // change la couleur du sac (pocket color== True et bag main color == False)
  changeBagColors(endroit:boolean, color:string){

    if (endroit) {
      this.selectedPocketColor = color;
    } else {
      this.selectedBagColor = color;
    }

    if(endroit){
      if (color == "#0F0F0F"){
        this.userChoice[2] = "black"
      } else if (color == "#500000"){
        this.userChoice[2] = "red"
      } else if (color == "#000060"){
        this.userChoice[2] = "blue"
      }
    this.my3DScene?.changeBagColorsOn3D(endroit, color)

    } else if(!endroit) {
      if (color == "#0F0F0F"){
        this.userChoice[1] = "black"
      } else if (color == "#500000"){
        this.userChoice[1] = "red"
      } else if (color == "#000060"){
        this.userChoice[1] = "blue"
      }
    this.my3DScene?.changeBagColorsOn3D(endroit, color)
    }
  }

  async addingToCartOperation(){
    this.customizationService.getQuantityOfUrl(this.userChoice[0],this.userChoice[1],this.userChoice[2]).subscribe(async (stock: any) => {

      this.stockQuantity = stock[0].quantity

      if(this.stockQuantity >= this.bagQuantity){
        try {
          await this.postBagCustomization();
          window.location.reload();
        } catch (error) {
          console.error("La requête POST a échoué", error);
        }
      }else{
        if(this.stockQuantity == 0 || this.stockQuantity==undefined){
          alert("We're sorry, this model is no longer available.")
        }else{
          alert("We're sorry, there are only " + this.stockQuantity + " bags left of this model")
        }
      }
    })
  }


  // ajoute des quantités
  plusButton(){
    this.bagQuantity += 1;
    if(this.userChoice[0] == undefined || this.userChoice[0] == "smallModel"){
      this.price = (130 + 30*this.loadedFile)*this.bagQuantity;
    }else {
      this.price = (150 + 30*this.loadedFile)*this.bagQuantity;
    }
  }

  // diminue les quantités
  minusButton(){
    if (this.bagQuantity != 1) {
      this.bagQuantity -= 1
      if(this.userChoice[0] == undefined || this.userChoice[0] == "smallModel"){
        this.price = (130 + 30*this.loadedFile)*this.bagQuantity;
      } else {
        this.price = (150 + 30*this.loadedFile)*this.bagQuantity;
      }
    }
  }

  postBagCustomization(){
    return new Promise<void>((resolve, reject) => {

      const xhr = new XMLHttpRequest();
      const url = 'api/customization';

      const formData = new FormData();

      if (this.fileToUpload !== null) {
        formData.append('file', this.fileToUpload);
      } else {
        formData.append('file', "null");
      }

      formData.append('modelType', this.userChoice[0]);
      formData.append('bagColor', this.userChoice[1]);
      formData.append('pocketColor', this.userChoice[2]);
      formData.append('email', this.email);
      formData.append('quantity', String(this.bagQuantity));
      xhr.open('POST', url);

      let urll = ""

      xhr.onload = () => {
        if (xhr.status === 200) {
          const response = JSON.parse(xhr.responseText);
          console.log("response", response);
          urll = response.cloudinary_url;
          handle(urll);
          resolve();
        } else {
          reject();
        }
      };

      const handle = (urll: string) => {
        this.customizationService.postForCart(
        this.email,
        this.userChoice[0],
        this.userChoice[1],
        this.userChoice[2],
        urll,
        this.loadedFile,
        this.bagQuantity
        );
      }

      xhr.onerror = function () {
        console.error('Erreur lors de la requête:', xhr.status);
      };

      xhr.send(formData);
    });

  }

  // permet de nettoyer la scene
  cleanScene(): void {
    this.my3DScene!.model.clear();
  }


  changeBackgroundColor(color: string): void {
    this.my3DScene?.changeBackgroundColor(color);
  }

  resizeScene(width: number, height: number): void {
    this.my3DScene?.updateCameraAspect(width, height);
  }

  onWindowResize(): void {
    const newWidth = window.innerWidth * 2.5;
    const newHeight = window.innerHeight * 4;
    this.resizeScene(newWidth, newHeight);
  }

}


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------



export class My3DScene {

  private frameId!: number;
  public scene!: THREE.Scene;
  private camera!: THREE.PerspectiveCamera;
  private renderer!: THREE.WebGLRenderer;
  private gltfLoader!: GLTFLoader;
  public model: any;
  private light!: THREE.AmbientLight;
  private spotlight!: THREE.SpotLight;
  private directionallight!: THREE.DirectionalLight;
  private pointlight!: THREE.PointLight;
  private isMouseDown: boolean = false;
  private isModelLoaded: boolean = false;
  public currentColor: string = 'white';


  constructor() {
    this.createScene();
    this.init();
  }


  private createScene(): void {
    this.scene = new THREE.Scene();
    this.scene.background = new THREE.Color(0xF3F3F3);
  }


  public init(width: number = window.innerWidth, height: number = window.innerHeight) {

    this.camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 2000);
    this.renderer = new THREE.WebGLRenderer({ canvas: document.getElementById('myCanvas') as HTMLCanvasElement }); //for html declaration
    this.gltfLoader = new GLTFLoader();
    this.pointlight = new THREE.PointLight(0xFFFFF,5,3);

    this.directionallight = new THREE.DirectionalLight(0xFFFFFF,3);
    this.directionallight.position.set(2, 5, 5).normalize();
    this.scene.add(this.directionallight);


    this.renderer.setSize(width, height);

    const container = document.getElementById('my3DContainer'); //for html declaration
    container?.appendChild(this.renderer.domElement);

    this.camera.position.x = -5;
    this.camera.position.y = 10;
    this.camera.position.z = 31;

    this.loadGLTFModel('assets/assets_3d/smallModel.glb', '#0F0F0F','#000060');

    // enft c'est un "écouteur" qui observe quand l'utilisateur clique sur la souris
    this.renderer.domElement.addEventListener('mouseup', () => {
      this.isMouseDown = false;
    });
    this.renderer.domElement.addEventListener('mousedown', () => {
      this.isMouseDown = true;
    });

    // et si la variable isMouseDown est true c'est que l'utilisateur est en train d'appuyer
    this.renderer.domElement.addEventListener('mousemove', (event) => {
      if (this.isMouseDown == true){
      this.handleMouseMove(event);
      }
    });

    window.addEventListener('resize', () => {
      this.camera.aspect = window.innerWidth / window.innerHeight;
      this.camera.updateProjectionMatrix();
      this.renderer.setSize(window.innerWidth, window.innerHeight);
    });

  }

  private handleMouseMove(event: MouseEvent) {
    if (this.model) {

      const x = (event.clientX / window.innerWidth) * 2 - 1;
      const y = -(event.clientY / window.innerHeight) * 2 + 1;

      // Mettre à jour la rotation du modèle en fonction de la position de la souris
      this.model.children[0].rotation.y += event.movementX / 100
      this.model.children[1].rotation.y += event.movementX / 100
    }
  }


  public loadGLTFModel(path: string, bagColor: string, pocketColor: string) {

    this.gltfLoader.load(path,
      (gltf) => {

        this.centerModel(gltf.scene);
        gltf.scene.position.set(-5, 3, 10);

        this.scene.add(gltf.scene);
        this.model = gltf.scene;

        this.model.children[0].material = new MeshStandardMaterial({color:new Color(bagColor)});
        this.model.children[1].material = new MeshStandardMaterial({color:new Color(pocketColor)});
        this.model.children[2].material = new MeshStandardMaterial({color:new Color(0x444444)});

        this.isModelLoaded = true;

      },
      undefined,
      (error) => {
        console.error('Error', error, error.message);
      }
    );
  }


  //change la couleur du sac ou de la poche directement sur le modèle 3D (pocket == True, bag == False)
  public changeBagColorsOn3D(endroit:boolean,color?:String) {
    if (endroit){
      this.model?.children[1].material.color.set(color);
    } if(!endroit) {
      this.model?.children[0].material.color.set(color);
    }
  }


  public render(): void {
    this.frameId = requestAnimationFrame(() => {
      this.render();
    });

    if (this.isModelLoaded) {
      if (this.isModelLoaded) {
      this.model.children[0].rotation.y += 0.008
      this.model.children[1].rotation.y += 0.008
      this.model.rotation.x = 3.05
      this.renderer.render(this.scene, this.camera);
      }
    }
  }

  public centerModel(model: THREE.Object3D): void {
    const box = new THREE.Box3().setFromObject(model);
    const center = box.getCenter(new THREE.Vector3());
    model.position.sub(center);
  }

  public changeBackgroundColor(color: string): void {
    this.scene.background = new THREE.Color(color);
  }

  public updateCameraAspect(width: number, height: number): void {
    this.camera.aspect = width / height;
    this.camera.updateProjectionMatrix();
    this.renderer.setSize(width, height);
  }
}






