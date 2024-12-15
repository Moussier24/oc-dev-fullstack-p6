import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Theme {
  id: number;
  name: string;
  description: string;
  subscribers_count: number;
  articles_count: number;
  is_subscribed: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private readonly API_URL = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getThemes(): Observable<Theme[]> {
    return this.http.get<Theme[]>(`${this.API_URL}/themes`);
  }

  subscribe(themeId: number): Observable<void> {
    return this.http.post<void>(
      `${this.API_URL}/themes/${themeId}/subscribe`,
      {}
    );
  }
}
