import * as THREE from 'three';
import {ElementRef, Injectable, NgZone, OnDestroy} from '@angular/core';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import {Component,OnInit, AfterViewInit} from '@angular/core';
import { PersonalisationService } from 'src/app/personalisation/personalisation.service';
import { animate } from '@angular/animations';
import { Color, MeshStandardMaterial } from 'three';

let number = 0;
let variable: boolean = false
let changer_sac = false
@Component({
  selector: 'app-personalisation',
  templateUrl: './personalisation.component.html',
  styleUrls: ['./personalisation.component.css']
})



export class PersonalisationComponent implements AfterViewInit {
  
  private my3DScene: My3DScene | undefined;
  
  

  change_model() {
   
    changer_sac = true;

    console.log("Le bouton a été cliqué !",changer_sac);
    
    //this.my3DScene?.change_color({secondary:"white"});
    this.cleanScene()
    this.my3DScene?.loadGLTFModel('assets/assets_3d/sac_finalPETIT.glb')
    
    

  }

  change_model_2() {
    changer_sac = true;
    console.log("Le bouton a été cliqué !",changer_sac);
    this.cleanScene()
    this.my3DScene?.loadGLTFModel('assets/assets_3d/sac_final_grande2.glb')
    //this.my3DScene?.change_color({secondary:"red"});
   

  }

  private cleanScene(): void {
    
    this.my3DScene?.model.clear();
    console.log(this.my3DScene?.model.children)

  }


  
  
  // Create a new instance of the scene and start rendering
  
  
  
  
  
  ngAfterViewInit() {
    
    console.log(number)
    if(variable === false){
     this.funcion()
     number += 1
    
    }
  }

  funcion(){
    console.log("Engine initiated")
    
    const animate = () => {

      this.my3DScene = new My3DScene();
      this.my3DScene.render();


      
    }
   animate();
   variable = true

   
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
    console.log("Created scene")
    this.camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    this.renderer = new THREE.WebGLRenderer({ antialias: true });
    this.gltfLoader = new GLTFLoader();

    // this.spotlight = new THREE.SpotLight(0xFFFFF);
    // this.spotlight.position.set( 0, 0, 10 );
     this.scene.add(this.spotlight);
   

    this.pointlight = new THREE.PointLight(0xFFFFF,5); // moins futuriste mettre ,3 après 
   
    this.scene.add(this.pointlight);

   

    this.directionallight = new THREE.DirectionalLight(0xFFFFFF, 2.5);
    this.directionallight.position.set(0, 0, 5).normalize(); // au middle mettre 10 
    this.scene.add(this.directionallight);


    this.renderer.setSize(window.innerWidth, window.innerHeight);
    document.body.appendChild(this.renderer.domElement);

    
    

    // on peut rêgler la position de la caméra
    this.camera.position.x = -30;
    this.camera.position.y = 0;
    this.camera.position.z = 50;


    // permet d'ajouter de la "lumière" sur le sac

    


    this.gltfLoader = new GLTFLoader();
    
    this.loadGLTFModel('assets/assets_3d/sac_final_grande2.glb')


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

      this.model.rotation.y += event.movementX / 100;

    }
  }

  public loadGLTFModel(path: string) {

    this.gltfLoader.load(path,
      (gltf) => {
       
        this.scene.add(gltf.scene);
        this.model = gltf.scene;

        console.log(this.model.children)

        this.model.children[0].material = new MeshStandardMaterial({color:new Color(0x000000)});
        this.model.children[1].material = new MeshStandardMaterial({color:new Color(0x00008B)});

        this.isModelLoaded = true;

        gltf.scene.position.set(0, -10, 0);

      },
      undefined,
      (error) => {
        console.error('Error', error, error.message);
      });
  }

  public change_color(colors:{primary?:String,secondary?:String}) {
    if (colors.primary)
      this.model?.children[0].material.color.set(colors.primary);
    if (colors.secondary)
      this.model?.children[1].material.color.set(colors.secondary)
    // and need to call your render function to apply changes to scene
  }

  
  

  


  public render(): void {
   
    this.frameId = requestAnimationFrame(() => {
      this.render();
    });

    if (this.isModelLoaded) {
  // Créer un nouveau groupe
  if (this.isModelLoaded) {
  // Créer un nouveau groupe
  var pivot = new THREE.Group();
  this.scene.add(pivot);
  pivot.add(this.model);
  
  // Positionner le pivot au centre de l'objet
  var boundingBox = new THREE.Box3().setFromObject(this.model);
  var center = new THREE.Vector3();
  boundingBox.getCenter(center);
  this.model.position.sub(center);
  
  // Appliquer la rotation sur le pivot
  pivot.rotation.x = 0;
  pivot.rotation.y = 0;
  pivot.rotation.z = 0;
  this.model.rotation.y += 0.01;
  
  this.model.rotation.z = 0;
  this.model.rotation.x = 3.05



  
  this.renderer.render(this.scene, this.camera);
}




}
  }
  public cleanup(): void {
    this.scene.remove(this.model);
    this.scene.remove(this.camera);
  
  }
}




