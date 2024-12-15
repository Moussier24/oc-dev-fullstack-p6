import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Article {
  id: number;
  title: string;
  content: string;
  author_username: string;
  theme_name: string;
  created_at: string;
}

interface CreateArticleRequest {
  title: string;
  content: string;
  theme_id: number;
}

export interface Comment {
  id: number;
  content: string;
  author_username: string;
  created_at: string;
}

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private readonly API_URL = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getFeed(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.API_URL}/articles/feed`);
  }

  createArticle(data: CreateArticleRequest): Observable<Article> {
    return this.http.post<Article>(`${this.API_URL}/articles`, data);
  }

  getArticle(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.API_URL}/articles/${id}`);
  }

  addComment(articleId: number, content: string): Observable<Comment> {
    return this.http.post<Comment>(`${this.API_URL}/comments`, {
      content,
      article_id: articleId,
    });
  }

  getArticleComments(articleId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(
      `${this.API_URL}/comments/article/${articleId}`
    );
  }
}
