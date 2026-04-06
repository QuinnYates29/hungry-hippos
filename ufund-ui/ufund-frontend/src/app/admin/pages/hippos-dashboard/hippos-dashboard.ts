import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HippoService, Hippo } from '../../../core/services/hippo';
import { Router } from '@angular/router';


@Component({
  selector: 'app-hippos-dashboard',
  standalone: false,
  templateUrl: './hippos-dashboard.html',
  styleUrl: './hippos-dashboard.css',
})
export class HipposDashboard implements OnInit {
  hippos: Hippo[] = [];
  loading = false;
  tempHippos: Hippo[] = [];
  displayAddHippoBox = false;
  displayEditHippoBox = false;
  newHippo: Hippo = {id: 0, name: '', species: '', gender: '', birthDate: [], weight: 0, latitude: 0, longitude: 0};
  editCurHippo: Hippo = {id: 0, name: '', species: '', gender: '', birthDate: [], weight: 0, latitude: 0, longitude: 0};
  
  constructor(
    private hippoService: HippoService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}
  fetchHippos(): void {
    this.loading = true;
    this.hippoService.getHippos().subscribe({
      next: (data) => {
        this.hippos = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching hippos', err);
        this.hippos = [];
        this.loading = false;
      }
    });
  }

  ngOnInit(): void {
    this.fetchHippos();
  }


  deleteHippo(id: number): void {
    if (!confirm('Are you sure you want to delete this hippo?')) {
      return;
    }
    this.hippoService.deleteHippo(id).subscribe({
      next: () => {
        this.fetchHippos();
      },
      error: (err) => {
        console.error('Error deleting hippo', err);
      }
    });
  }
  openAddHippoBox(): void {
    this.displayAddHippoBox = true;
  }

  closeAddHippoBox(): void {
    this.newHippo = {id: 0, name: '', species: '', gender: '', birthDate: [], weight: 0, latitude: 0, longitude: 0};
    this.displayAddHippoBox = false;
  }

 
  openEditHippoBox(hippo: Hippo): void {
    this.editCurHippo.id = hippo.id;
    this.editCurHippo.name = hippo.name;
    this.editCurHippo.species = hippo.species;
    this.editCurHippo.gender = hippo.gender;
    this.editCurHippo.birthDate = hippo.birthDate;
    this.editCurHippo.weight = hippo.weight;
    this.editCurHippo.latitude = hippo.latitude;
    this.editCurHippo.longitude = hippo.longitude;
    this.displayEditHippoBox = true;
  }
  closeEditHippoBox(): void {
    this.editCurHippo = {id: 0, name: '', species: '', gender: '', birthDate: [], weight: 0, latitude: 0, longitude: 0};
    this.displayEditHippoBox = false;
  }
  editHippo(): void {
    if (!confirm('Are you sure you want to edit this hippo?')) {
      return;
    }
    this.hippoService.updateHippo(this.editCurHippo).subscribe({
      next: (updatedHippo) => {
        this.fetchHippos();
        this.displayEditHippoBox = false;
        this.editCurHippo = {id: 0, name: '', species: '', gender: '', birthDate: [], weight: 0, latitude: 0, longitude: 0};
      },
      error: (err) => {
        console.error('Error updating hippo', err);
      }
    })
    this.displayEditHippoBox = false;
    this.editCurHippo = {id: 0, name: '', species: '', gender: '', birthDate: [], weight: 0, latitude: 0, longitude: 0};
  }
 
  logout(): void {
      localStorage.removeItem('currentUser');
      this.router.navigate(['/login']);
  }

}
