/// @file needs.ts
/// @author qry3977
/// Needs service class provides an interface for Need object, and a connection to backend
/// Used for admin and helper

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Inerface for Need object
export interface Need {
  id: number;
  name: string;
  type: string;
  cost: number;
  quantity: number;
}

@Injectable({
  providedIn: 'root',
})

export class NeedsService {
  private apiUrl = '';
}
