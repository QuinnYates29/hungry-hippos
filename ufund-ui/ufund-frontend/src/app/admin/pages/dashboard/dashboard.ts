/// @file dashboard.ts
/// @author qry3977
/// @author ars4041
/// Dashboard shows current UI changes and sends and receives signals to service for updating the cupboard backend and updating the current page
/// Used for admin dashboard

import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NeedsService, Need } from '../../../core/services/needs';
import { HippoService, Hippo } from '../../../core/services/hippo';
import { Router } from '@angular/router';


/**
 * Dashboard page for U-fund managers to view all cupboard needs.
 */
@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
})
export class Dashboard implements OnInit{
  //List of needs retrieved from backend
  needs: Need[] = [];
  hippos: Hippo[] = [];
  tempNeeds: Need[] = [];
  tempHippos: Hippo[] = [];
  loading = false

  //add need box
  displayAddNeedBox = false;

  //edit need box
  displayEditNeedBox = false;
  
  newNeed: Need = {id: 0, name: '', type: '', cost: 0, quantity: 0};
  editCurNeed: Need = {id: 0, name: '', type: '', cost: 0, quantity: 0};

  //add hippo box
  displayAddHippoBox = false;

  newHippo: Hippo = {id: 0, name: '', species: '', gender: '', birthDate: [2026,1,1], weight: 0.0, latitude: 0.0, longitude: 0.0};
  newHippoDateStr: string = '2026-01-01';


  constructor(
    private needsService: NeedsService,
    private hippoService: HippoService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.fetchNeeds();
    this.fetchHippos
  }

  /**
   * Fetches needs from backend and stores them in a local list. This effectively
   * "refreshes" the list and should be called after every remove/add/page change
   */
  fetchNeeds(): void {
    this.loading = true;
    this.needsService.getAllNeeds().subscribe({
      next: (data) => {
        this.needs = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching needs', err);
        this.needs = [];
        this.loading = false;
      }
    });
  }

    /**
   * Fetches hippos from backend and stores them in a local list. This effectively
   * "refreshes" the list and should be called after every remove/add/page change
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

  /**
   * Calls need service class deleteNeed for given id
   * If error, do nothing and report it
   * @param id if of need to be deleted
   */
  removeNeed(id: number): void {
    if (!confirm('Are you sure you want to delete this need?')) {
      return;
    }
    this.needsService.deleteNeed(id).subscribe({
      next: () => {
        // Call fetch needs to update local array
        this.fetchNeeds();
      },
      error: (err) => {
        console.error('Error deleting need', err);
      }
    });
  }

  /**
   * Sends a signal for the UI to open the add need box
   */
  openAddNeedBox(): void {
    this.displayAddNeedBox = true;
  }
  /**
   * Sends a signal for the UI to close the add need box and resets the new need for next time
   */
  closeAddNeedBox(): void {
    this.newNeed = {id: 0, name: '', type: '', cost: 0, quantity: 0};
    this.displayAddNeedBox = false;
  }

  /**
   * Calls need service class to create and add a new need to the cupboard
   * If error, do nothing and report it
   */
  addNewNeed(): void {
    if (!confirm('Are you sure you want to add this need?')) {
      return;
    }
    this.needsService.createNeed(this.newNeed).subscribe({
      next: (createdNeed) => {
        this.needs.push(createdNeed);
        this.fetchNeeds();
      },
      error: (err) => {
        console.error('Failed to add new need', err);
      }
    })
    this.displayAddNeedBox = false;
    this.newNeed = {id: 0, name: '', type: '', cost: 0, quantity: 0};
  }

  /**
   * Sends a signal for the UI to open the edit need box with the current needs data displayed
   */
  openEditNeedBox(need: Need): void {
    this.editCurNeed.id = need.id;
    this.editCurNeed.name = need.name;
    this.editCurNeed.type = need.type;
    this.editCurNeed.cost = need.cost;
    this.editCurNeed.quantity = need.quantity;
    this.displayEditNeedBox = true;
  }

  /**
   * Sends a signal for the UI to close the edit need box and resets the current needs data
   */
  closeEditNeedBox(): void {
    this.editCurNeed = {id: 0, name: '', type: '', cost: 0, quantity: 0};
    this.displayEditNeedBox = false;
  }

  /**
   * Calls need service class edit and update an existing need in the cupboard
   * If error, do nothing and report it
   */
  editNeed(): void {
    if (!confirm('Are you sure you want to edit this need?')) {
      return;
    }
    this.needsService.editNeedServ(this.editCurNeed).subscribe({
      next: () => {
        this.fetchNeeds();
        this.displayEditNeedBox = false;
        this.editCurNeed = {id: 0, name: '', type: '', cost: 0, quantity: 0};
      },
      error: (err) => {
        console.error('Failed to edit need', err);
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


  /**
   * Logs out the current user by clearing session data and redirecting to the login page.
   */
  logout(): void {
      localStorage.removeItem('currentUser');
      this.router.navigate(['/login']);
    }
}