import { Component } from '@angular/core';
import { AuthFormComponent } from "../../../components/users/auth-form/auth-form.component";
import { UserDTO } from "../../../dtos/user.dto";
import { NotificationService } from '../../../services/notification.service';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [AuthFormComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loading = false;

  constructor(private authService: AuthService, private router: Router) {
  }

  submitFn = (user: UserDTO) => {
    this.loading = true;
    this.authService.login(user.email, user.password).subscribe({
      complete: () => {
        this.loading = false;
      },
      next: () => {
        this.router.navigate(['/']);
      },
    });
  }
}
