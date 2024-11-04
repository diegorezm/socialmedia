import { Component, Input } from '@angular/core';
import { UserDTO } from "../../../dtos/user.dto";
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { LucideAngularModule, User, Mail, Lock } from 'lucide-angular'
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-auth-form',
  standalone: true,
  imports: [ReactiveFormsModule, LucideAngularModule, CommonModule],
  templateUrl: './auth-form.component.html',
  styleUrl: './auth-form.component.css'
})
export class AuthFormComponent {
  @Input() fields: UserDTO = {
    name: '',
    email: '',
    password: '',
  }
  @Input() submitFn: (user: UserDTO) => void = () => { }
  @Input() mode: 'login' | 'register' = 'login'
  @Input() loading: boolean = false

  authForm: FormGroup;

  UserIcon = User;
  MailIcon = Mail;
  LockIcon = Lock;

  constructor(private fb: FormBuilder) {
    this.authForm = this.fb.group({
      email: new FormControl(this.fields.email, [Validators.required, Validators.email]),
      password: new FormControl(this.fields.password, [Validators.required]),
    });
  }

  ngOnInit(): void {
    if (this.mode === 'register') {
      this.authForm.addControl('name', new FormControl(this.fields.name, [Validators.required, Validators.minLength(3)]));
    }
    this.authForm.patchValue(this.fields);
  }

  onSubmit() {
    if (this.authForm.valid) {
      this.submitFn(this.authForm.value)
    }
  }
}
