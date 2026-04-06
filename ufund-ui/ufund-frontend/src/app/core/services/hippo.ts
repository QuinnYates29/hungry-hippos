import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';

/**
 * Interface matching the Hippo model in the backend.
 * Uses number[] for birthDate to handle Jackson's default LocalDate array format.
 */
export interface Hippo {
  id: number;
  name: string;
  species: string;
  gender: string;
  birthDate: number[]; 
  weight: number;
  latitude: number;
  longitude: number;
}

@Injectable({
  providedIn: 'root'
})
export class HippoService {
  // Ensure this matches your Spring Boot server port
  private readonly URL = 'http://localhost:8080/hippos';

  private selectedHippoSource = new BehaviorSubject<Hippo | null>(null);
  selectedHippo$ = this.selectedHippoSource.asObservable();

  constructor(private http: HttpClient) { }

  setSelectedHippo(hippo: Hippo | null) {
    this.selectedHippoSource.next(hippo);
  }

  /** GET /hippos - Retrieves all hippos */
  getHippos(): Observable<Hippo[]> {
    return this.http.get<Hippo[]>(this.URL);
  }

  /** GET /hippos/{id} - Retrieves a single hippo by ID */
  getHippo(id: number): Observable<Hippo> {
    return this.http.get<Hippo>(`${this.URL}/${id}`);
  }

  /** GET /hippos/?name={name} - Searches hippos by name */
  searchHippos(name: string): Observable<Hippo[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Hippo[]>(`${this.URL}/`, { params });
  }

  /** POST /hippos - Creates a new hippo */
  createHippo(hippo: Hippo): Observable<Hippo> {
    return this.http.post<Hippo>(this.URL, hippo);
  }

  /** PUT /hippos - Updates an existing hippo */
  updateHippo(hippo: Hippo): Observable<Hippo> {
    return this.http.put<Hippo>(this.URL, hippo);
  }

  /** DELETE /hippos/{id} - Deletes a hippo by ID */
  deleteHippo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.URL}/${id}`);
  }
}