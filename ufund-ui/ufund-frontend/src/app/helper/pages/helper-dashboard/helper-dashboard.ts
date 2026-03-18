/// @file helper-dashboard.ts
/// @author iz6341, 
///helper dashboard component for displaying all need and searching needs by name


import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NeedsService, Need } from '../../../core/services/needs';
import { Subject } from 'rxjs/internal/Subject';
import { Basket } from '../../../core/basket';
import { Router } from '@angular/router';


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
  currentUserId: number = 0;
  showSuccess: boolean = false;


  constructor(private needsService: NeedsService, private basketService: Basket,
    private cdr: ChangeDetectorRef, private router: Router
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
    const currentSession = localStorage.getItem('currentUser');
    if(currentSession){
      const user= JSON.parse(currentSession);
      this.currentUserId= user.id
    }
    this.fetchNeeds();
    this.fetchBasket();
  }

  /**
   * Retrieves all needs in the current user's basket from the backend.
   * Updates the local basket state and triggers manual change detection on success.
   */
  fetchBasket(): void {
    this.loadingBasket = true;
    this.basketService.getAllNeeds(this.currentUserId).subscribe({
      next: (data) => {
        console.log('Basket needs received from backend:', data); // debug
        this.basketNeeds = data;
        this.cdr.detectChanges()
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
   * Remove item from the basket method
   * @param needId
   */
  removeFromBasket(needId: number): void {
      this.basketService.removeFromBasket(this.currentUserId,needId).subscribe({
      next: () => {
        this.fetchBasket(); //refresh/fetch basket after removal
      },
      error: (err) => console.error(`Error removing item with ID ${needId} from basket`, err)
      });
  }

  
  /**
   * Placeholder function for adding a need to the helper's basket.
   * @param need 
   */
  addBasket(need: Need){
      console.log('Adding to basket:', need.name);
      this.basketService.addToBasket(this.currentUserId, need).subscribe({
        next: () => {
          this.fetchBasket(); // refresh/fetch basket after adding
        },
        error: (err) => console.error(`Error adding ${need.name} to basket`, err)
      });
  }

    logout(): void {
      // Clear user session data
      localStorage.removeItem('currentUser');
      // Redirect to login page
      this.router.navigate(['/login']);
    }
    // removeFromBasket(arg0: number) {
    //   throw new Error('Method not implemented.');
    //   }
}
