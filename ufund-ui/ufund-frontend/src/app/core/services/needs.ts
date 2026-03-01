/// @file needs.ts
/// @author qry3977
/// Needs service class provides an interface for Need object, and a connection to backend
/// Used for admin and helper

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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
   * Sends a HTML request to backend to remove a need from cupboard by id
   * @param number id of need to remove
   */
  deleteNeed(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
