/// @file users.service.ts
/// @author qry3977
/// Users service class provides an interface for User objects and a connection to the backend API.

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap, catchError } from 'rxjs';

/**
 * Interface for User object
 */
export interface User {
  id: number;
  username: string;
  password: string;
  role: string; // "ADMIN" or "HELPER"
}

/**
 * DTO for login requests
 */
export interface LoginRequest {
  username: string;
  password: string;
}

@Injectable({
  providedIn: 'root',
})

/**
 * Service responsible for interacting with backend User API.
 */
export class UsersService {
  private apiUrl = 'http://localhost:8080/users'; // Base URL for users API

  constructor(private http: HttpClient) {}
  currentUser: any = null;

  /**
   * Function which keeps state by setting current user logged in
   * @param user user object to update
   */
  setCurrentUser(user: any) {
    this.currentUser = user;
  }

  /**
   * Function returning current user state
   * @returns user object of current user logged in
   */
  getCurrentUser() {
    return this.currentUser;
  }

  /**
   * Retrieves all users from the backend
   * @returns Observable emitting an array of User objects
   */
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl).pipe(
      tap(users => console.log(`Fetched ${users.length} users`)),
      catchError(this.handleError<User[]>('getAllUsers', []))
    );
  }

  /**
   * Retrieves a user by their ID
   * @param id User ID
   * @returns Observable emitting the User object
   */
  getUserById(id: number): Observable<User | null> {
    return this.http.get<User>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError<User | null>('getUserById', null))
    );
  }

  /**
   * Creates a new user in the backend
   * @param user User object to create
   * @returns Observable emitting the created User
   */
  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user).pipe(
      tap(newUser => console.log(`Created user: ${newUser.username}`)),
      catchError(this.handleError<User>('createUser'))
    );
  }

  /**
   * Deletes a user by ID
   * @param id ID of the user to delete
   * @returns Observable emitting void
   */
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => console.log(`Deleted user with id ${id}`)),
      catchError(this.handleError<void>('deleteUser'))
    );
  }

  /**
   * Updates a user in the backend
   * @param user Updated user object
   * @returns Observable emitting the updated User
   */
  updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${user.id}`, user).pipe(
      tap(() => console.log(`Updated user with id ${user.id}`)),
      catchError(this.handleError<User>('updateUser'))
    );
  }

  /**
   * Attempts to log in a user with username and password
   * @param credentials LoginRequest object containing username and password
   * @returns Observable emitting the logged-in User
   */
  login(credentials: LoginRequest): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/login`, credentials).pipe(
      tap(user => console.log(`User ${user.username} logged in`)),
      catchError(this.handleError<User>('login'))
    );
  }

  /**
   * Generic error handler for HTTP operations
   * @param operation Name of the operation that failed
   * @param result Optional value to return as fallback
   * @returns Function that returns an Observable of the fallback result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}