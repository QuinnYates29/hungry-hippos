import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NeedsService, Need } from '../../../core/services/needs';
import { Subject } from 'rxjs/internal/Subject';


@Component({
  selector: 'app-helper-dashboard',
  standalone: false,
  templateUrl: './helper-dashboard.html',
  styleUrl: './helper-dashboard.css',
})
export class HelperDashboard implements OnInit{
  needs: Need[] =[]
  private searchTerms = new Subject<string>();
  loading = false


  constructor(private needsService: NeedsService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.fetchNeeds();
  }

  fetchNeeds(): void {
    this.loading = true;
    this.needsService.getAllNeeds().subscribe({
      next: (data) => {
        console.log('Needs received from backend:', data); // debug
        this.needs = data;
        this.loading = false;
        // Force Angular to detect changes immediately
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching needs', err);
        this.loading = false;
      }
    });
  }
   addBasket(need: Need){
      console.log('Adding to basket:', need.name);

   }
}
