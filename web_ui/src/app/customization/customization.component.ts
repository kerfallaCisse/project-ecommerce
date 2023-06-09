import * as THREE from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import {Component,OnInit, OnDestroy, HostListener , AfterViewInit} from '@angular/core';
import {CustomizationService} from '../services/customization/customization.service';
import { Color, MeshStandardMaterial } from 'three';

let number = 0;
let variable: boolean = false
let changer_sac = false


@Component({
  selector: 'app-customization',
  templateUrl: './customization.component.html',
  styleUrls: ['./customization.component.css'],
  providers: [CustomizationService]
})


export class CustomizationComponent implements OnInit {

  constructor(private customizationService: CustomizationService) {}

  selectedColor_pocket: string = "nothing";
  maValeur: number = 1;

  tab: string[] = [];
  fileName = '';
  tab_real_ai: {modelType: string,color_pocket_name: string,color_bag_name: string,quantity: number,fichier: File,}[] = [];
  quantity: number = 5
  pricee = 130

  private my3DScene: My3DScene | undefined;

  fileToUpload: File | null = null;

  selectedSize: number = 40;
  selectedBagColor: string = '#0F0F0F';
  selectedPocketColor: string = '#000060';


  handleFileInput(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput && fileInput.files && fileInput.files.length > 0) {
      const file: File = fileInput.files[0];
      this.fileToUpload = file
    }
  }

  change_size(taille:number) { // change la taille du sac quand l'utilisatueur clique sinon c'est 40L par défault
    this.selectedSize = taille;

    if(taille===40){
      this.maValeur = 1
      this.tab[0] = "smallModel"
      this.cleanScene()
      this.pricee = 130
      this.my3DScene?.loadGLTFModel('assets/assets_3d/petit_finallo2.glb')
      this.selectedBagColor = '#0F0F0F';
      this.selectedPocketColor = '#000060';
    }
    if(taille===70) {
      this.maValeur = 1
      this.pricee = 150
      this.tab[0] = "largeModel"
      this.cleanScene()
      this.my3DScene?.loadGLTFModel('assets/assets_3d/grand_finallo2.glb')
      this.selectedBagColor = '#0F0F0F';
      this.selectedPocketColor = '#000060'
    }
  }


  change_colors(endroit:boolean,color:string){ // change la couleur du sac (pocket et bag)
    if (endroit) {
      this.selectedPocketColor = color;
    } else {
      this.selectedBagColor = color;
    }

    if(endroit){ // pocket
    if (color == "#0F0F0F"){
      this.tab[1] = "black"
    } else if (color == "#500000"){
      this.tab[1] = "red"
    } else if (color == "#000060"){
      this.tab[1] = "blue"
    }
    this.my3DScene?.change_color(true,color)
    }if(!endroit){
      if (color == "#0F0F0F"){
        this.tab[2] = "black"
      } else if (color == "#500000"){
        this.tab[2] = "red"
      } else if (color == "#000060"){
        this.tab[2] = "blue"
      }
     this.my3DScene?.change_color(false,color)
    }
  }

  finish_(){
    if (this.tab[0] == undefined){
      this.tab[0] = "smallModel"
    }
    this.customizationService.getQuantityOfUrl(this.tab[0],this.tab[1],this.tab[2]).subscribe((dataa: any) => {
      this.quantity = dataa[0].quantity;
      let blabla = dataa[0].quantity;

      console.log("Quantity : " + blabla)
      if(blabla >= this.maValeur){
        var label = document.getElementById("label_adding");
        label!.style.display = "block"; // Affiche le label lorsque le bouton est cliqué

        // appel de la fonction qui va faire le post
        this.function_for_make_post()
      }else{
        if(this.quantity == 0){
          alert("Nous sommes désolés, ce modèle n'est plus disponible")
        }else{
          alert("Nous somme désolés, il ne reste que " + this.quantity + " sac de ce modèle")
        }
      }
    })
  }

  button_plus(){  // ajoute plus de quantité
    this.maValeur += 1
    console.log(this.pricee,this.maValeur)
    if(this.tab[0] == undefined || this.tab[0] == "smallModel"){
      this.pricee += 130
    }else {
      this.pricee += 150
    }
  }

  button_moins(){ // diminue les quantités
    if (this.maValeur != 1) {
      this.maValeur -= 1
      if(this.tab[0] == undefined || this.tab[0] == "smallModel"){
        this.pricee -= 130
      } else {
        this.pricee -= 150
      }
    }
  }

  function_for_make_post(){
    const image = "http://res.cloudinary.com/dqvvvce88/image/upload/wz1dbmyo22ohwuug3nbi"
    const logo = 0
    const email = "john@gmail.com"
    this.customizationService.make_post_for_cart(email,this.tab[0],this.tab[1],this.tab[2],image,logo)
  }


  private cleanScene(): void { // permet de nettoyer la scene
    this.my3DScene!.model.clear();
  }

  fileInput = document.getElementById("fileInput");

  ngOnDestroy() {
    window.removeEventListener('resize', this.onWindowResize);
  }


  ngOnInit(){


    if(variable === false){
      this.my3DScene = new My3DScene();
      this.my3DScene.render();
      variable = true
      number += 1
    }else{
      window.location.reload()
      console.log("salut")
      this.cleanScene()
      this.my3DScene?.loadGLTFModel('assets/assets_3d/petit_finallo2.glb')
    }

    //Stacy

    this.changeBackgroundColor('#d2d7d2');
    const width = window.innerWidth * 2.5;
    const height = window.innerHeight * 4;
    this.resizeScene(width, height);

    window.addEventListener('resize', () => this.onWindowResize());
  }

  //STACY


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
    // this.renderer = new THREE.WebGLRenderer({ antialias: true }); //stacy
    this.renderer = new THREE.WebGLRenderer({ canvas: document.getElementById('myCanvas') as HTMLCanvasElement }); //stacy pr le html
    this.gltfLoader = new GLTFLoader();
    this.pointlight = new THREE.PointLight(0xFFFFF,5,3);

    this.directionallight = new THREE.DirectionalLight(0xFFFFFF,3);
    this.directionallight.position.set(2, 5, 5).normalize(); // au middle mettre 10
    this.scene.add(this.directionallight);


    this.renderer.setSize(width, height);

    const container = document.getElementById('my3DContainer'); //szacy pr le html
    container?.appendChild(this.renderer.domElement);

    this.camera.position.x = -5;
    this.camera.position.y = 10;
    this.camera.position.z = 31;

    this.loadGLTFModel('assets/assets_3d/petit_finallo2.glb')

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

  public loadGLTFModel(path: string) {

    this.gltfLoader.load(path,
      (gltf) => {

        this.centerModel(gltf.scene); // Centre le modèle //Stacy
        gltf.scene.position.set(-5, 3, 10);

        this.scene.add(gltf.scene);
        this.model = gltf.scene;

        this.model.children[0].material = new MeshStandardMaterial({color:new Color(0x0F0F0F)});
        this.model.children[1].material = new MeshStandardMaterial({color:new Color(0x000060)});
        this.model.children[2].material = new MeshStandardMaterial({color:new Color(0x444444)});

        this.isModelLoaded = true;

      },
      undefined,
      (error) => {
        console.error('Error', error, error.message);
      }
    );
  }

  public change_color(endroit:boolean,primary?:String) {

    if (endroit){
      this.model?.children[1].material.color.set(primary);
    } if(!endroit) {
      this.model?.children[0].material.color.set(primary);
    }
  }

  public render(): void {

    this.frameId = requestAnimationFrame(() => {
      this.render();
    });

    if (this.isModelLoaded) {
      if (this.isModelLoaded) {
      this.model.children[0].rotation.y += 0.01
      this.model.children[1].rotation.y += 0.01
      this.model.rotation.x = 3.05
      this.renderer.render(this.scene, this.camera);
      }
    }
  }

  //Stacy
  public centerModel(model: THREE.Object3D): void {
    const box = new THREE.Box3().setFromObject(model);
    const center = box.getCenter(new THREE.Vector3());
    model.position.sub(center); // Centre le modèle sur l'origine
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






