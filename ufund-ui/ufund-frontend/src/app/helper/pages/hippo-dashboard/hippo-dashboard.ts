import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HippoService, Hippo } from '../../../core/services/hippo';

@Component({
  selector: 'app-hippo-dashboard',
  standalone: false,
  templateUrl: './hippo-dashboard.html',
  styleUrl: './hippo-dashboard.css',
})
export class HippoDashboard implements OnInit {
  hippos: Hippo[] = [];
  loading = false;
  // Track the hippo currently being checked out
  checkedOutHippo: Hippo | null = null;

  constructor(
    private hippoService: HippoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.fetchHippos();
  }

  /**
   * 
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
        this.loading = false;
      }
    });
  }

  /**
   * Updates the action from logging observation to checking out.
   * Sets the selected hippo to display the "buying" section.
   */
  checkoutForHippo(hippo: Hippo): void {
    this.checkedOutHippo = hippo;
    
    // PUSH the hippo to the shared service
    this.hippoService.setSelectedHippo(hippo);
    
    console.log(`Checking out for: ${hippo.name}`);
    alert(`Checkout initiated for ${hippo.name}.`);
  }
}