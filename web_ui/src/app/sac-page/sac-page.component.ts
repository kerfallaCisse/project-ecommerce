import { ActivatedRoute } from '@angular/router';
import { FoodService } from '../services/food/food.service';
import {Food} from '../shared/models/Food';
import * as THREE from 'three';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';


@Component({
  selector: 'app-sac-page',
  templateUrl: './sac-page.component.html',
  styleUrls: ['./sac-page.component.css']
})
export class SacPageComponent {
  @ViewChild('canvasContainer', {static: true}) canvasContainer!: ElementRef<HTMLDivElement>;
  private renderer!: THREE.WebGLRenderer;
  private scene!: THREE.Scene;
  private camera!: THREE.PerspectiveCamera;

  ngOnInit() {
    this.initScene();

    this.loadModel();
  }

  private initScene() {
    const container = this.canvasContainer.nativeElement;
    this.renderer = new THREE.WebGLRenderer();
    this.renderer.setSize(container.offsetWidth, container.offsetHeight);
    container.appendChild(this.renderer.domElement);

    this.scene = new THREE.Scene();

    this.camera = new THREE.PerspectiveCamera(45, container.offsetWidth / container.offsetHeight, 0.1, 1000);
    this.camera.position.set(0, 0, 5);
  }

  private loadModel() {
      const loader = new GLTFLoader();
      loader.load('/assets/a_backpack_for_an_adventure/scene.gltf', gltf => {
        const mesh = gltf.scene.children[0];
        this.scene.add(mesh);
      });




  }
}


