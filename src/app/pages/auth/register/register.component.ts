import { Component, OnInit } from '@angular/core';
import { AuthFormComponent } from "../../../components/users/auth-form/auth-form.component";
import { UserDTO } from "../../../dtos/user.dto";
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';

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

  constructor(private authService: AuthService, private router: Router) {
  }

  submitFn(user: UserDTO) {
    this.loading = true;
    this.authService.register(user).subscribe({
      complete: () => {
        this.loading = false;
      },
      next: () => {
        this.router.navigate(['/auth/login']);
      },
    });
  }
  ngOnInit(): void {
  }
}
