import { Component, AfterViewInit, OnDestroy } from '@angular/core';
import * as L from 'leaflet';

interface Hippo {
  id: number;
  name: string;
  species: string;
  gender: string;
  birthDate: number[]; 
  weight: number;
  latitude: number;
  longitude: number;
}

@Component({
  selector: 'app-hippo-map',
  standalone: false,
  templateUrl: './hippo-map.html',
  styleUrl: './hippo-map.css',
})
export class HippoMap implements AfterViewInit, OnDestroy {
  private map!: L.Map;

  // Mock data for testing without the backend
  private mockHippos: Hippo[] = [
    {
      id: 1,
      name: "Moto Moto",
      species: "Common Hippo",
      gender: "Male",
      birthDate: [2010, 5, 24],
      weight: 2800.5,
      latitude: -13.1234,
      longitude: 31.5678
    },
    {
      id: 2,
      name: "Kyle",
      species: "Pygmy Hippo",
      gender: "Male",
      birthDate: [2018, 11, 12],
      weight: 260.0,
      latitude: -13.1240,
      longitude: 31.5685
    }
  ];

  constructor() {}

  ngAfterViewInit(): void {
    this.initMap();
    this.plotMockHippos();
  
    setTimeout(() => {
      this.map.invalidateSize();
    }, 200);
  }

  private initMap(): void {
    // Center the map on the coordinates of our mock hippos
    this.map = L.map('map').setView([-13.1234, 31.5678], 15);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap'
    }).addTo(this.map);

    // This fix is necessary because Leaflet's default icon paths 
    // often break when bundled by Angular's build system.
    const DefaultIcon = L.icon({
      iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
      shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41]
    });
    L.Marker.prototype.options.icon = DefaultIcon;
  }

  private plotMockHippos(): void {
    this.mockHippos.forEach(hippo => {
      L.marker([hippo.latitude, hippo.longitude])
        .addTo(this.map)
        .bindPopup(`<b>${hippo.name}</b><br>Weight: ${hippo.weight}kg`);
    });
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.remove();
    }
  }
}