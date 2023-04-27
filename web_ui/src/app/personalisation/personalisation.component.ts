import * as THREE from 'three';
import {ElementRef, Injectable, NgZone, OnDestroy} from '@angular/core';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import {Component,OnInit, AfterViewInit} from '@angular/core';

@Component({
  selector: 'app-personalisation',
  templateUrl: './personalisation.component.html',
  styleUrls: ['./personalisation.component.css']
})

export class PersonalisationComponent implements OnInit,AfterViewInit {



  // Create a new instance of the scene and start rendering
  public constructor() {}

  ngAfterViewInit() {
    console.log("Engine initiated")
    const my3DScene = new My3DScene();
    function animate() {

      my3DScene.render();

    }
   animate();
  }

  public ngOnInit(): void {
  }

}

class My3DScene {

  private frameId!: number;
  private scene: THREE.Scene;
  private camera: THREE.PerspectiveCamera;
  private renderer: THREE.WebGLRenderer;
  private gltfLoader: GLTFLoader;
  private light: THREE.AmbientLight;
  private model : any;
  private directionallight : THREE.DirectionalLight;


  private isMouseDown: boolean = false;



  constructor() {


    console.log("Created scene")
    this.scene = new THREE.Scene();


    this.scene.background = new THREE.Color(0xF5F5DC)

    //this.model.material.color.setHex( 0xff0000 )

    this.camera = new THREE.PerspectiveCamera(
      75, window.innerWidth / window.innerHeight, 0.1, 1000
    );


    this.renderer = new THREE.WebGLRenderer({ antialias: true });

    this.renderer.setSize(window.innerWidth, window.innerHeight);
    document.body.appendChild(this.renderer.domElement);

    this.light = new THREE.AmbientLight(0xFFFFFF);
    this.light.position.z = 10;
    this.scene.add(this.light);

    // on peut rêgler la position de la caméra
    this.camera.position.x = 3;
    this.camera.position.y = 10;
    this.camera.position.z = 15;


    // permet d'ajouter de la "lumière" sur le sac

    this.directionallight = new THREE.DirectionalLight(0xFFFFFF, 1);
    this.directionallight.position.set(0, 1, 1).normalize();
    this.scene.add(this.directionallight);


    this.gltfLoader = new GLTFLoader();


    this.loadGLTFModel('assets/assets_3d/backpack.gltf');


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

      this.model.rotation.z += event.movementX / 100;

    }
  }

  private loadGLTFModel(path: string) {

    this.gltfLoader.load(path,
      (gltf) => {
        this.model = gltf.scene.children[0];
        //this.model.material.color.setHex(0xFF0000);  // ROUGGGE COMME LE SANG
        //this.model.material.color.setHex(0x0000FF); // BlEU COMME LES JEUDI SOIR
        this.scene.add(gltf.scene);
        this.model = gltf.scene;

        gltf.scene.position.set(0, 10, -10);

      },
      undefined,
      (error) => {
        console.error('Error', error, error.message);
      });
  }




  public render(): void {
    this.frameId = requestAnimationFrame(() => {
      this.render();
    });

    this.model.rotation.z += 0.01;
    this.model.rotation.x = 4.80
    this.renderer.render(this.scene, this.camera);
  }
}
