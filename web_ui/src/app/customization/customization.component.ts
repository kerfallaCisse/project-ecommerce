import * as THREE from 'three';
import {ElementRef, Injectable, NgZone, OnDestroy} from '@angular/core';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import {Component,OnInit, AfterViewInit} from '@angular/core';
import {CustomizationService} from '../services/customization/customization.service';

import { Color, MeshStandardMaterial } from 'three';

let number = 0;
let variable: boolean = false
let changer_sac = false

export interface Iteme {

  modelType: string;
  color_pocket_name: string;
  color_bag_name: string;
  quantity: number;
  file: File | null;
}

@Component({
  selector: 'app-customization',
  templateUrl: './customization.component.html',
  styleUrls: ['./customization.component.css'],
  providers: [CustomizationService]
})



export class CustomizationComponent implements AfterViewInit {
  
  selectedColor_pocket: string = "nothing";
  maValeur: number = 1;
  tab: string[] = [];
  fileName = '';
  tab_real_ai: {modelType: string,color_pocket_name: string,color_bag_name: string,quantity: number,fichier: File,}[] = [];

  

  

  private my3DScene: My3DScene | undefined;

  fileToUpload: File | null = null;


 handleFileInput(event: Event) {
    const fileInput = event.target as HTMLInputElement;
  
    if (fileInput && fileInput.files && fileInput.files.length > 0) {
      const file: File = fileInput.files[0];
      // Effectuer les opérations nécessaires avec le fichier sélectionné
      // console.log("on est la mon gar")
      // console.log(file);
      this.fileToUpload = file
      //console.log(this.fileToUpload)

    }
  }


  change_size(taille:number) { // model 40L
    
    if(taille===40){
    this.tab[0] = "40L"
    this.cleanScene()
    this.my3DScene?.loadGLTFModel('assets/assets_3d/petit_finallo2.glb')
     } if(taille===70) {
    this.tab[0] = "70L"
    this.cleanScene()
    this.my3DScene?.loadGLTFModel('assets/assets_3d/grand_finallo2.glb')
     }
  }

  constructor(private customizationService: CustomizationService) {}

  change_colors(endroit:boolean,color:string){
    
    if(endroit){
    
    this.tab[1] = color
    this.my3DScene?.change_color(true,color)
    }if(!endroit){
    this.tab[2] = color
     this.my3DScene?.change_color(false,color)
    }
  }

  finish_(){
    
    // console.log(this.tab)
    // console.log(this.fileToUpload)
    var label = document.getElementById("myLabel");
    label!.style.display = "block"; // Affiche le label lorsque le bouton est cliqué
    
    const data: Iteme = {
      modelType:  this.tab[0],
      color_pocket_name: this.tab[1],
      color_bag_name: this.tab[2],
      quantity: this.maValeur,
      file: this.fileToUpload || null
      
    };

    console.log(data)

  }

  button_plus(){
    this.maValeur += 1
    
    var element = document.getElementById("monElement");
    if (element) {
      element.textContent = this.maValeur.toString();
    }
  }

  button_moins(){
    if (this.maValeur != 1) {
    this.maValeur -= 1
    
    var element = document.getElementById("monElement");
    if (element) {
      element.textContent = this.maValeur.toString();
    }
  } 
  }

  



  private cleanScene(): void { // permet de nettoyer la scene
    this.my3DScene!.model.clear();
  }

  fileInput = document.getElementById("fileInput");

  ngAfterViewInit() {
    

    this.customizationService.getQuantityOfUrl().subscribe((dataa: any) => {
      const quantity = dataa[0].quantity;
      
      console.log(quantity)
      if(quantity > 10){
        console.log("salut")
      }
    
    })

    if(variable === false){
      this.my3DScene = new My3DScene();
      this.my3DScene.render();
      variable = true
     number += 1
    }
    var element = document.getElementById("monElement");
    if (element) {
      element.textContent = this.maValeur.toString();
    }
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
    this.scene.background = new THREE.Color(0xFFFFFF);
  }

 

  private init(){
    
    this.camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 2000);
    this.renderer = new THREE.WebGLRenderer({ antialias: true });
    this.gltfLoader = new GLTFLoader();

   

    this.pointlight = new THREE.PointLight(0xFFFFF,5,3); // moins futuriste mettre ,3 après 
   
    

   

    this.directionallight = new THREE.DirectionalLight(0xFFFFFF, 2.5);
    this.directionallight.position.set(0, 0, 5).normalize(); // au middle mettre 10 
    this.scene.add(this.directionallight);


    this.renderer.setSize(window.innerWidth, window.innerHeight);
    document.body.appendChild(this.renderer.domElement);

    
  
    this.camera.position.x = -5;
    this.camera.position.y = 10;
    this.camera.position.z = 30;
 

    
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
       
        this.scene.add(gltf.scene);
        this.model = gltf.scene;

        
        this.model.children[0].material = new MeshStandardMaterial({color:new Color(0x595959)});
        this.model.children[1].material = new MeshStandardMaterial({color:new Color(0x000060)});

        this.isModelLoaded = true;

        gltf.scene.position.set(0, 5, 5);

        this.model.center()

      },
      undefined,
      (error) => {
        console.error('Error', error, error.message);
      });
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
  
  }




  

