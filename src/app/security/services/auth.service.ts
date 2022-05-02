import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, retry, throwError} from "rxjs";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  basePath: string = 'http://localhost:3000/api/v1/auth';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) { }

  // API Error Handling
  handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // Default Error Handling
      console.log(`An error occurred ${error.status}, body was: ${error.error}`);
    } else {
      // Unsuccessful Response Error Code returned from Backend
      console.log(`Backend returned code ${error.status}, body was: ${error.error}`);
    }
    // Return Observable with Error Message to Client
    return throwError(() => new Error('Something happened with request, please try again later.'));
  }

  // Sign-Up
  signUp(user: User): Observable<any> {
    return this.http.post(`${this.basePath}/sign-up`, user)
      .pipe(retry(2), catchError(this.handleError));
  }

  // Sign-In
  signIn(user: User) {
    return this.http.post(`${this.basePath}/sign-in`, user)
      .pipe(retry(2), catchError(this.handleError));
  }

  // Get Token
  getToken() {
    return localStorage.getItem('accessToken');
  }

  // Set Token
  setToken(accessToken: string) {
    localStorage.setItem('accessToken', accessToken);
  }

  // Get Current User
  getCurrentUser() {
    return localStorage.getItem('currentUser');
  }
  // Set Current User
  setCurrentUser(user: string) {
    localStorage.setItem('currentUser', user);
  }
  // Is Signed-In
  get isSignedIn(): boolean {
    let accessToken = this.getToken();
    return accessToken !== null;
  }

  // Sign-Out
  signOut() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('currentUser');
  }

}
