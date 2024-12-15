import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Theme } from './theme.service';

export interface UserProfile {
  username: string;
  email: string;
}

export interface UpdateProfileRequest {
  user_name?: string;
  email?: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly API_URL = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getProfile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.API_URL}/users/me`);
  }

  updateProfile(data: UpdateProfileRequest): Observable<UserProfile> {
    return this.http.put<UserProfile>(`${this.API_URL}/auth/profile`, data);
  }

  getSubscriptions(): Observable<Theme[]> {
    return this.http.get<Theme[]>(`${this.API_URL}/themes/subscribed`);
  }

  unsubscribe(themeId: number): Observable<void> {
    return this.http.post<void>(
      `${this.API_URL}/themes/${themeId}/unsubscribe`,
      {}
    );
  }
}
