import { Routes } from '@angular/router';
import { RegisterComponent } from "./pages/auth/register/register.component";
import { LoginComponent } from "./pages/auth/login/login.component";
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { AppLayoutComponent } from './components/layouts/app-layout/app-layout.component';
import { authGuard } from './guards/auth.guard';
import { alreadyAuthenticatedGuard } from './guards/already-authenticated.guard';

export const routes: Routes = [{
  path: 'auth',
  component: AuthLayoutComponent,
  canActivate: [alreadyAuthenticatedGuard],
  children: [
    {
      path: 'register',
      component: RegisterComponent,
    },
    {
      path: 'login',
      component: LoginComponent,
    },
  ],
},
{
  path: '',
  component: AppLayoutComponent,
  canActivate: [authGuard],
}
];
