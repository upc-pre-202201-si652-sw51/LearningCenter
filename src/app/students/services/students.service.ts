import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { catchError, Observable, retry, throwError } from "rxjs";
import { Student} from "../model/student";

@Injectable({
  providedIn: 'root'
})
export class StudentsService {
  // Students Endpoint
  basePath = 'http://localhost:3000/api/v1/students';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  }

  constructor(private http: HttpClient) { }

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

  // Create Student
  create(item: any): Observable<Student> {
    return this.http.post<Student>(this.basePath, JSON.stringify(item), this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  // Get Student by id
  getById(id: any): Observable<Student> {
    return this.http.get<Student>(`${this.basePath}/${id}`, this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  // Get all students
  getAll(): Observable<Student> {
    return this.http.get<Student>(this.basePath, this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  update(id: any, item: any): Observable<Student> {
    return this.http.put<Student>(`${this.basePath}/${id}`, JSON.stringify(item),this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  // Delete student
  delete(id: any): Observable<Student> {
    return this.http.delete<Student>(`${this.basePath}/${id}`, this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }


}
