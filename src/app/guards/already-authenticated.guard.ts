import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';
import { map } from 'rxjs';

export const alreadyAuthenticatedGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const notificationService = inject(NotificationService)
  const router = inject(Router)

  return authService.isLoggedIn().pipe(map(isLoggedIn => {
    if (isLoggedIn === true) {
      notificationService.error('You are already logged in.');
      return router.parseUrl('/');
    }
    return true;
  }));

};
