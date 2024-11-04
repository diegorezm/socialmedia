import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponseDto } from '../dtos/login-response-dto';
import { UserDTO } from '../dtos/user.dto';
import { throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private url: string = '/api/auth';

  constructor(private http: HttpClient, private notificationService: NotificationService) { }

  login(email: string, password: string) {
    return this.http.post<LoginResponseDto>(`${this.url}/login`, { email, password }).pipe(
      tap(response => {
        this.notificationService.success('You are logged in.');
        this.saveToken(response.token.token);
      }),
      catchError(this.handleError.bind(this))
    );
  }

  register(payload: UserDTO) {
    return this.http.post<any>(`${this.url}/register`, payload).pipe(
      tap(response => {
        this.notificationService.success('Account created successfully!');
      }),
      catchError(this.handleError.bind(this))
    );
  }

  profile() {
    return this.http.get<LoginResponseDto>(`${this.url}/profile`);
  }

  private saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.error?.error) {
      errorMessage = error.error.error;
    }
    this.notificationService.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
