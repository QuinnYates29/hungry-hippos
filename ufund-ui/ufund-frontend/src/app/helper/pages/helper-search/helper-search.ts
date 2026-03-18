/// @file helper-search.ts
/// @author iz6341
/// helper search component for searching needs by name    
    
import { Component, OnInit, EventEmitter, Output} from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';
import { NeedsService, Need } from '../../../core/services/needs';

@Component({
  selector: 'app-helper-search',
  standalone: false,
  templateUrl: './helper-search.html',
  styleUrl: './helper-search.css',
})

export class HelperSearch implements OnInit {
  // Event emitter to send search results to the parent component.
  @Output() resultsFound = new EventEmitter<Need[]>();
  // Observable stream of needs matching the search term.
  needs$!: Observable<Need[]>;

  private searchTerms = new Subject<string>();
  constructor(private needService: NeedsService) {}

  /**
   * Trigger a search for needs matching the given term. 
   * @param term 
   */
  search(term: string): void {
    this.searchTerms.next(term);
  }

  /**
   * Set up the search stream to listen for search terms, debounce input, and fetch results from the backend. 
   * Emits the search results to the parent component via the resultsFound event emitter.
   */
  ngOnInit(): void {
    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => {
        if (term.trim()) {
          return this.needService.searchNeeds(term);
        } else {
          // if the search term is empty, return all needs
          return this.needService.getAllNeeds();
        }
      })
    ).subscribe(results => {
      //emitt the search results
      this.resultsFound.emit(results);
    });
  }
}
