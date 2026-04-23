import { Component, signal } from '@angular/core';
import { UsersService } from './core/services/users';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class AppComponent {

  protected readonly title = signal('ufund-frontend');
  showProfile = false;

  constructor(private usersService: UsersService, private router: Router) {}

  get username(): string | null {
    const user = this.usersService.getCurrentUser();
    return user ? user.username : null;
  }

  get fundingLink(): string {
    const user = this.usersService.getCurrentUser();
    if (user?.role === 'ADMIN') {
      return '/admin/dashboard';
    } else {
      return '/helper/helper-dashboard';
    }
  }
  get hipposLink(): string {
    const user = this.usersService.getCurrentUser();
    if (user?.role === 'ADMIN') {
      return '/admin/hippos-dashboard';
    } else {
      return '/helper/hippos-dashboard';
    }
  }

  toggleProfile() {
    this.showProfile = !this.showProfile;
  }

  onLogout() {
    this.showProfile = false; 
    this.usersService.logout(); 
    this.router.navigate(['/login']);
  }
}