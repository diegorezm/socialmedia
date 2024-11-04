import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponseDto } from '../dtos/login-response-dto';
import { UserDTO } from '../dtos/user.dto';
import { BehaviorSubject, Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { type User } from '../models/user.model';

const TOKEN_KEY = 'token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private url: string = '/api/auth';
  private userSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  public user$: Observable<User | null> = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    this.userSubject.subscribe(user => {
      if (!user) {
        this.profile().subscribe();
      }
    });
    console.log(this.user$);
  }

  login(email: string, password: string) {
    return this.http.post<LoginResponseDto>(`${this.url}/login`, { email, password }).pipe(
      tap(response => {
        this.saveToken(response.token.token);
        this.profile().subscribe();
      }),
      catchError(this.handleError.bind(this))
    );
  }

  register(payload: UserDTO) {
    return this.http.post<any>(`${this.url}/register`, payload).pipe(
      catchError(this.handleError.bind(this))
    );
  }

  profile() {
    return this.http.get<User>(`${this.url}/profile`).pipe(
      tap(response => {
        this.setUser(response);
      }),
      catchError(this.handleError.bind(this))
    );
  }

  isLoggedIn(): Observable<boolean> {
    const token = localStorage.getItem(TOKEN_KEY);
    if (!token) {
      return of(false);
    }
    if (this.userSubject.getValue()) {
      return of(true);
    }
    return this.profile().pipe(
      map(user => !!user),
      catchError(() => of(false))
    );
  }

  logout() {
    localStorage.removeItem(TOKEN_KEY);
    this.clearserUser();
  }

  private saveToken(token: string) {
    localStorage.setItem(TOKEN_KEY, token);
  }

  clearserUser() {
    this.userSubject.next(null);
  }

  setUser(user: User) {
    this.userSubject.next(user);
  }

  getUser(): Observable<User | null> {
    return this.user$
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.error?.error) {
      errorMessage = error.error.error;
    }
    return throwError(() => new Error(errorMessage));
  }

  private isUser(obj: any): obj is User {
    return obj && obj.id && obj.email && obj.name && obj.createdAt && obj.updatedAt
  }

}
