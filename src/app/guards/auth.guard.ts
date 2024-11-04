import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';
import { map, tap } from 'rxjs/operators';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const notificationService = inject(NotificationService);
  const router = inject(Router);

  return authService.isLoggedIn().pipe(map(isLoggedIn => {
    if (isLoggedIn === false) {
      notificationService.error('You must be logged in to access this page.');
      return router.parseUrl('/auth/login');
    }
    return true;
  }));
};
