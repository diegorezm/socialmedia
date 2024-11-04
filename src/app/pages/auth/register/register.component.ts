import { Component, OnInit } from '@angular/core';
import { AuthFormComponent } from "../../../components/users/auth-form/auth-form.component";
import { UserDTO } from "../../../dtos/user.dto";
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [AuthFormComponent],
  providers: [AuthService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent implements OnInit {

  loading = false;

  constructor(private notificationService: NotificationService, private authService: AuthService, private router: Router) {
  }

  submitFn(user: UserDTO) {
    this.loading = true;
    this.authService.register(user).subscribe({
      complete: () => {
        this.loading = false;
      },
      next: () => {
        this.notificationService.success('User created successfully!');
        this.router.navigate(['/auth/login']);
      },
      error: (error) => {
        this.loading = false;
        this.notificationService.error(error.message ?? "An unknown error occurred.");
      }
    });
  }
  ngOnInit(): void {
  }
}
