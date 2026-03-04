import { Component, signal } from '@angular/core';
import { UsersService } from './core/services/users';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class AppComponent {

  protected readonly title = signal('ufund-frontend');

  constructor(private usersService: UsersService) {}

  get username(): string | null {
    const user = this.usersService.getCurrentUser();
    return user ? user.username : null;
  }
}