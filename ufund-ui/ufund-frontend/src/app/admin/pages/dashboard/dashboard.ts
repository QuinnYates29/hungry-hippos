import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NeedsService, Need } from '../../../core/services/needs';


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
  tempNeeds: Need[] = [];
  loading = false

  //add need box
  displayAddNeedBox = false;
  
  newNeed: Need = {id: 0, name: '', type: '', cost: 0, quantity: 0};


  constructor(
    private needsService: NeedsService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.fetchNeeds();
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

  openAddNeedBox(): void {
    this.displayAddNeedBox = true;
  }

  closeAddNeedBox(): void {
    this.displayAddNeedBox = false;
  }

  addNewNeed(): void {
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

}