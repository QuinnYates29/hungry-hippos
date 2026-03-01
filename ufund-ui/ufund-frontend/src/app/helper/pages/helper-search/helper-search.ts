      
      
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
  @Output() resultsFound = new EventEmitter<Need[]>();
  needs$!: Observable<Need[]>;
  private searchTerms = new Subject<string>();
  constructor(private needService: NeedsService) {}

  search(term: string): void {
    this.searchTerms.next(term);
  }


  ngOnInit(): void {
    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => term.trim() 
        ? this.needService.searchNeeds(term) 
        : this.needService.getAllNeeds())
    ).subscribe(results => {
      // 2. Instead of just holding the results, EMIT them
      this.resultsFound.emit(results);
    });
  }
}
