import { Component, AfterViewInit, OnDestroy } from '@angular/core';
import * as L from 'leaflet';
import { HippoService } from '../../core/services/hippo';

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
  hippos: Hippo[] = [];

  constructor(private hippoService: HippoService) {}

  ngAfterViewInit(): void {
    this.initMap();
    this.plotHippos();
  
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

  private plotHippos(): void {
    this.hippoService.getHippos().subscribe({
      next: (data) => {
        console.log('Hippos received from backend', data);
        this.hippos = data;

        // THE FIX: The loop must be INSIDE the subscribe block!
        this.hippos.forEach(hippo => {
          L.marker([hippo.latitude, hippo.longitude])
            .addTo(this.map)
            .bindPopup(`<b>${hippo.name}</b><br>Weight: ${hippo.weight}kg`);
        });
        
        // Optional: If you want the map to automatically zoom out to see all hippos
        if (this.hippos.length > 0) {
            const group = L.featureGroup(this.hippos.map(h => L.marker([h.latitude, h.longitude])));
            this.map.fitBounds(group.getBounds().pad(0.1));
        }
      },
      error: (err) => {
        console.error('Error fetching hippos', err);
      }
    });
    
    // Anything placed here runs BEFORE the hippos arrive!
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.remove();
    }
  }
}