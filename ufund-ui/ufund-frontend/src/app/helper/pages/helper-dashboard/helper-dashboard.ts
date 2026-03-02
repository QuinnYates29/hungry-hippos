/// @file helper-dashboard.ts
/// @author iz6341
///helper dashboard component for displaying all need and searching needs by name


import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NeedsService, Need } from '../../../core/services/needs';
import { Subject } from 'rxjs/internal/Subject';
import { Basket } from '../../../core/basket';


@Component({
  selector: 'app-helper-dashboard',
  standalone: false,
  templateUrl: './helper-dashboard.html',
  styleUrl: './helper-dashboard.css',
})


export class HelperDashboard implements OnInit{
  // List of needs to display
  needs: Need[] =[]
  private searchTerms = new Subject<string>();
  loadingNeeds = false
  loadingBasket = false
  showBasket = false;
  basketNeeds: Need[] = [];


  constructor(private needsService: NeedsService, private basketService: Basket,
    private cdr: ChangeDetectorRef
  ) { }

  /**
   * Handle search results emitted from the HelperSearch component
   * @param foundNeeds - Array of needs that match the search criteria
   */
  handleSearchResults(foundNeeds: Need[]): void {
    this.needs = foundNeeds;
    // Ensure the cards update immediately
    this.cdr.detectChanges(); 
  }

  /**
   * Fetch needs when the component initializes
   */
  ngOnInit(): void {
    this.fetchNeeds();
    this.fetchBasket();
  }

  fetchBasket(): void {
    this.loadingBasket = true;
    this.basketService.getAllNeeds().subscribe({
      next: (data) => {
        console.log('Basket needs received from backend:', data); // debug
        this.basketNeeds = data;
        this.loadingBasket = false;
      },
      error: (err) => {
        console.error('Error fetching basket needs', err);
        this.loadingBasket = false;
      }
    });
  }

  /**
   * Fetches all needs from the backend. 
   * Sets loading state while fetching and updates the needs list once data is received. 
   */

  fetchNeeds(): void {
    this.loadingNeeds = true;
    this.needsService.getAllNeeds().subscribe({
      next: (data) => {
        console.log('Needs received from backend:', data); // debug
        this.needs = data;
        this.loadingNeeds = false;
        // Force Angular to detect changes immediately
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching needs', err);
        this.loadingNeeds = false;
      }
    });
  }
  /**
   * Placeholder function for adding a need to the helper's basket.
   * @param need 
   */
  addBasket(need: Need){
      console.log('Adding to basket:', need.name);
      this.basketService.addToBasket(need).subscribe({
        next: () => {
          // console.log(`Successfully added ${need.name} to basket`);
          this.fetchBasket(); // Refresh basket after adding
        },
        error: (err) => console.error(`Error adding ${need.name} to basket`, err)
      });
      // this.basketNeeds.push(need);
      // this.cdr.detectChanges();
    }

    toggleBasket(): void {
      this.showBasket = !this.showBasket;
    }
}
