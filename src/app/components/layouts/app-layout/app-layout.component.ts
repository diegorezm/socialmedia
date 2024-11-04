import { Component, OnInit } from '@angular/core';
import { LogoComponent } from '../../ui/logo/logo.component';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-app-layout',
  standalone: true,
  imports: [LogoComponent],
  templateUrl: './app-layout.component.html',
  styleUrl: './app-layout.component.css'
})
export class AppLayoutComponent implements OnInit {

  user: User | null;

  constructor(private authService: AuthService, private router: Router) {
    this.user = null;
    this.authService.getUser().subscribe(user => {
      if (user) {
        this.user = user;
      } else {
        this.router.navigate(['/auth/login']);
      }
    });
  }

  getUserFistLetter() {
    return this.user?.name.charAt(0).toUpperCase();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  ngOnInit(): void {
  }
}
