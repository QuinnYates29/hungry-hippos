// core/guards/auth.guard.ts
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService } from '../services/users';

export const authGuard = () => {
  const usersService = inject(UsersService);
  const router = inject(Router);

  if (usersService.getCurrentUser()) {
    return true; // User is logged in, let them through
  } else {
    router.navigate(['/login']); // Kick them to login
    return false;
  }
};