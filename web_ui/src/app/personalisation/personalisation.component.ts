import * as THREE from 'three';
import {ElementRef, Injectable, NgZone, OnDestroy} from '@angular/core';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import {Component,OnInit, AfterViewInit} from '@angular/core';

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
  
  

  change_model() { // model 40L
    this.cleanScene()
    this.my3DScene?.loadGLTFModel('assets/assets_3d/sac_finalPETIT.glb')
  }

  change_model_2() { // model 70L
    this.cleanScene()
    this.my3DScene?.loadGLTFModel('assets/assets_3d/sac_final_grande2.glb')
    //this.my3DScene?.change_color({secondary:"red"});
   

  }

  private cleanScene(): void { // permet de nettoyer la scene
    this.my3DScene!.model.clear();
  }


  ngAfterViewInit() {
    
    if(variable === false){
      this.my3DScene = new My3DScene();
      this.my3DScene.render();
      variable = true
     number += 1
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

    
  
    this.camera.position.x = -30;
    this.camera.position.y = 0;
    this.camera.position.z = 50;
 

    
    this.loadGLTFModel('assets/assets_3d/testrotate.glb')


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

        
        this.model.children[0].material = new MeshStandardMaterial({color:new Color(0x000000)});
        this.model.children[1].material = new MeshStandardMaterial({color:new Color(0x00008B)});

        this.isModelLoaded = true;

        gltf.scene.position.set(0, 0, 0);

        this.model.center()

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
  }

  


  public render(): void {
   
    this.frameId = requestAnimationFrame(() => {
      this.render();
    });

    if (this.isModelLoaded) {
  if (this.isModelLoaded) {

  this.model.rotation.y += 0.01
  this.model.rotation.x = 3.05
  
  this.renderer.render(this.scene, this.camera);
}





}
  }
  
  }





