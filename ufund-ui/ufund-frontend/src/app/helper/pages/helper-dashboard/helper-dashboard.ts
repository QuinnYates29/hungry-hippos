import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NeedsService, Need } from '../../../core/services/needs';
import { Subject } from 'rxjs/internal/Subject';
import { Basket } from '../../../core/basket';
import { Router } from '@angular/router';
import { UsersService } from '../../../core/services/users';
import { Hippo, HippoService } from '../../../core/services/hippo';

@Component({
  selector: 'app-helper-dashboard',
  standalone: false,
  templateUrl: './helper-dashboard.html',
  styleUrl: './helper-dashboard.css',
})

export class HelperDashboard implements OnInit {
  needs: Need[] = [];
  private searchTerms = new Subject<string>();
  loadingNeeds = false;
  loadingBasket = false;
  showBasket = false;
  basketNeeds: Need[] = [];
  showSuccess: boolean = false;

  get currentUserId(): number {
    const user = this.usersService.getCurrentUser();
    return user?.id ?? 0;
  }

  activeHippo: Hippo | null = null;

  constructor(
    private needsService: NeedsService, 
    private basketService: Basket,
    private hippoService: HippoService,
    private cdr: ChangeDetectorRef, 
    private router: Router, 
    private usersService: UsersService
  ) { }

  /**
   * Handle search results emitted from the HelperSearch component
   * @param foundNeeds - Array of needs that match the search criteria
   */
  handleSearchResults(foundNeeds: Need[]): void {
    this.needs = foundNeeds;
    this.cdr.detectChanges(); 
  }

  /**
   * Fetch needs when the component initializes
   */
  ngOnInit(): void {
    this.fetchNeeds();
    this.fetchBasket();

    this.hippoService.selectedHippo$.subscribe(hippo => {
      this.activeHippo = hippo;
      this.cdr.detectChanges();
    });
  }

  /**
   * Retrieves all needs in the current user's basket from the backend.
   * Updates the local basket state and triggers manual change detection on success.
   */
  fetchBasket(): void {
    if (this.currentUserId === 0) return;
    this.loadingBasket = true;
    this.basketService.getAllNeeds(this.currentUserId).subscribe({
      next: (data) => {
        console.log('Basket needs received from backend:', data);
        this.basketNeeds = data;
        this.loadingBasket = false;
        this.cdr.detectChanges();
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
        console.log('Needs received from backend:', data);
        this.needs = data;
        this.loadingNeeds = false;
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
      this.basketService.removeFromBasket(this.currentUserId, needId).subscribe({
      next: () => {
        this.fetchBasket();
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
          this.fetchBasket();
          this.showBasket = true;

        },
        error: (err) => console.error(`Error adding ${need.name} to basket`, err)
      });
  }

  /**
   * Logs out the current user by clearing session data and redirecting to the login page.
   */
  logout(): void {
      this.usersService.logout();
      this.router.navigate(['/login']);
    }
    
  /**
   * Toggles the visibility of the basket component.
   * Switches the `showBasket` boolean between true and false.
   */
  toggleBasket(): void {
    this.showBasket = !this.showBasket;
  }

  /**
   * Closs the checkout window.
   */
  closeWindow(): void{
    this.showSuccess=false;
  }
  
  /**
   * Checkout method
   * @param currentUserId
   */
  checkout(): void {
      this.basketService.checkout(this.currentUserId).subscribe({
      next: () => {
        this.showSuccess = true;
        this.fetchNeeds();
        this.fetchBasket();  
      },
      error: (err) => console.error('Checkout failed', err)
      });
  }
}