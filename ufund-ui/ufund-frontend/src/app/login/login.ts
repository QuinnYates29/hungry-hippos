import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService, LoginRequest, User } from '../core/services/users';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  standalone: false,
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private usersService: UsersService,
    private router: Router
  ) {}

  /**
   * Called when login form is submitted.
   * Attempts to authenticate user with backend.
   */
  onSubmit(): void {

    const credentials: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.usersService.login(credentials)
      .subscribe({
        next: (user: User) => {

          // Store entire user object in localStorage
          localStorage.setItem('currentUser', JSON.stringify(user));

          // Route based on role
          if (user.role === 'ADMIN') {
            this.router.navigate(['/admin/dashboard']);
          } else {
            this.router.navigate(['/helper/helper-dashboard']);
          }
        },
        error: () => {
          this.errorMessage = 'Invalid username or password';
        }
      });
  }
}