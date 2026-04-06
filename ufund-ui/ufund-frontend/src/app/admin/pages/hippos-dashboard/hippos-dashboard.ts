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
  //List of hippos retrieved from backend
  hippos: Hippo[] = [];
  loading = false;
  tempHippos: Hippo[] = [];
  //add hippo box status
  displayAddHippoBox = false;
  //new hippo being added
  newHippo: Hippo = {id: 0, name: '', species: '', gender: '', birthDate: [2026,1,1], weight: 0.0, latitude: 0.0, longitude: 0.0};
  newHippoDateStr: string = '2026-01-01';
  
  constructor(
    private hippoService: HippoService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  /**
   * Fetches hippos from backend and stores them in a local list. This effectively
   * "refreshes" the list and should be called after every remove/add/page change.
   */
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

  /**
   * Deletes a hippo by its ID after user confirmation.
   * @param id The ID of the hippo to delete.
   * @returns void
   */
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
  /**
   * Sends a signal for the UI to open the add hippo box
   */
  openAddHippoBox(): void {
    this.displayAddHippoBox = true;
  }

    /**
   * Sends a signal for the UI to close the add hippo box and resets the new hippo for next time
   */
  closeAddHippoBox(): void {
    this.newHippo = {id: 0, name: '', species: '', gender: '', birthDate: [2026,1,1], weight: 0.0, latitude: 0.0, longitude: 0.0};
    this.newHippoDateStr = '2026-01-01';
    this.displayAddHippoBox = false;
  }

    /**
   * Calls hippo service class to create and add a new hippo to the hippos backend
   * If error, do nothing and report it
   */
  addNewHippo(): void {
    if (!confirm('Are you sure you want to add this hippo?')) {
      return;
    }
    const [year, month, day] = this.newHippoDateStr.split('-').map(s => +s);
    const hippoCorrected: Hippo = {
    ...this.newHippo,
    birthDate: [year, month, day]
  };
    this.hippoService.createHippo(hippoCorrected).subscribe({
      next: (createdHippo) => {
        this.hippos.push(createdHippo);
        this.fetchHippos();
      },
      error: (err) => {
        console.error('Failed to add new hippo', err);
      }
    })
    this.displayAddHippoBox = false;
    this.newHippo = {id: 0, name: '', species: '', gender: '', birthDate: [2026,1,1], weight: 0.0, latitude: 0.0, longitude: 0.0};
    this.newHippoDateStr = '2026-01-01';
  }

 
  

 
  logout(): void {
      localStorage.removeItem('currentUser');
      this.router.navigate(['/login']);
  }

}
