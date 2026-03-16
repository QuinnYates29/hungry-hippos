/// @file needs.ts
/// @author qry3977
/// Needs service class provides an interface for Need object, and a connection to backend
/// Used for admin and helper

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, tap, of } from 'rxjs';
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';

// Inerface for Need object
export interface Need {
  id: number;
  name: string;
  type: string;
  cost: number;
  quantity: number;
}

@Injectable({
  providedIn: 'root',
})

/**
 * Service responsible for retrieving cupboard needs from the backend API.
 */
export class NeedsService {
  private apiUrl = 'http://localhost:8080/cupboard';

  constructor(private http: HttpClient) {}

  /**
  * Retrieves all cupboard needs from the backend
  * @param Observable emitting the list of needs
  */
  getAllNeeds(): Observable<Need[]> {
    return this.http.get<Need[]>(this.apiUrl);
  }

  /**
   * Seaches for need objects in the backend that match the search term.
   * @param term 
   * @returns 
   */
  searchNeeds(term: string): Observable<Need[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<Need[]>(`${this.apiUrl}/search?name=${term}`).pipe(
      tap(x => x.length ?
         console.log(`found needs matching "${term}"`) :
         console.log(`no needs matching "${term}"`)),
      catchError(this.handleError<Need[]>('searchNeeds', []))
    );
  }
  
  /**
   * Handler for HTTP operation that failed.
   * @param operation 
   * @param result 
   * @returns 
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  /**
   * Sends a HTML request to backend to remove a need from cupboard by id
   * @param number id of need to remove
   */
  deleteNeed(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  createNeed(need: Need): Observable<Need> {
    return this.http.post<Need>(this.apiUrl, need)
  }
}
