import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, tap, of } from 'rxjs';

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
export class Basket {
  private apiUrl = 'http://localhost:8080/basket';

  constructor(private http: HttpClient) {}

  getAllNeeds(): Observable<Need[]> {
      return this.http.get<Need[]>(this.apiUrl);
    }

  addToBasket(need: Need): Observable<any> {
    return this.http.post(`${this.apiUrl}`, need ).pipe(
      tap(() => console.log(`Added need with ID ${need.id} to basket`)),
      catchError(this.handleError<Need[]>('addToBasket', []))
    );
  }

  removeFromBasket(needId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${needId}`);
  }

  getNeed(needId: number): Observable<Need> {
    return this.http.get<Need>(`${this.apiUrl}/${needId}`);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
