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
  needs: Need[] =[]
  private searchTerms = new Subject<string>();
  loadingNeeds = false
  loadingBasket = false
  showBasket = false;
  basketNeeds: Need[] = [];


  constructor(private needsService: NeedsService, private basketService: Basket,
    private cdr: ChangeDetectorRef
  ) { }

  handleSearchResults(foundNeeds: Need[]): void {
    this.needs = foundNeeds;
    this.cdr.detectChanges(); // Ensure the cards update immediately
  }

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
  
   removeFromBasket(needId: number): void {
      this.basketService.removeFromBasket(needId).subscribe({
      next: () => {
        // console.log(`Successfully removed item with ID ${needId} from basket`);
        this.fetchBasket(); // Refresh basket after removal
      },
      error: (err) => console.error(`Error removing item with ID ${needId} from basket`, err)
      });
      // this.basketNeeds = this.basketNeeds.filter(item => item.id !== needId);
      // this.cdr.detectChanges();
  }

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
