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

  getAllNeeds(userId: number): Observable<Need[]> {
    return this.http.get<Need[]>(`${this.apiUrl}/${userId}`);
  }


  addToBasket(userId: number, need: Need): Observable<Need | null> {
    return this.http.post<Need>(`${this.apiUrl}/${userId}`, need).pipe(
      tap(() => console.log(`Added need with ID ${need.id} to basket for user ${userId}`)),
      catchError(this.handleError<Need | null>('addToBasket', null))
    );
}

  removeFromBasket(userId: number, needId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${userId}/${needId}`).pipe(
      tap(() => console.log(`Removed need with ID ${needId} from basket for user ${userId}`)),
      catchError(this.handleError<void>('removeFromBasket'))
    );
  }

  checkout(userId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/checkout/${userId}`, {}).pipe(
      catchError(this.handleError<any>())
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
