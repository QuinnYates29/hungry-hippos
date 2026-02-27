import { Component, OnInit } from '@angular/core';
import { NeedsService, Need } from '../../../core/services/needs';

/**
 * Dashboard page for U-fund managers to view all cupboard needs.
 */
@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit{
  //List of needs retrieved from backend
  needs: Need[] = [];
  loading = false

  constructor(private needsService: NeedsService) { }

  ngOnInit(): void {
    this.fetchNeeds();
  }

  //Gets needs from backend and stores them in local needs list
  fetchNeeds(): void {
    this.loading = true;
    this.needsService.getAllNeeds().subscribe({
      next: (data) => {
        this.needs = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching needs', err);
        this.loading = false;
      }
    });
  }
}
