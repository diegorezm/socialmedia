import { Component } from '@angular/core';
import { AuthFormComponent } from "../../../components/users/auth-form/auth-form.component";
import { UserDTO } from "../../../dtos/user.dto";
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [AuthFormComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loading = false;

  constructor(private notificationService: NotificationService, private authService: AuthService, private router: Router) {
  }

  submitFn(user: UserDTO) {
    this.loading = true;
    this.authService.login(user.email, user.password).subscribe({
      complete: () => {
        this.loading = false;
      },
      next: () => {
        this.notificationService.success('You are now logged in!');
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.loading = false;
        this.notificationService.error(err.message ?? "An unknown error occurred.");
      },
    });
  }
}
