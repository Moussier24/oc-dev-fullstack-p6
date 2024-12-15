import { Component, OnInit } from '@angular/core';
import { ArticleService, Article } from '../../services/article.service';
import { Router } from '@angular/router';

type SortType = 'date' | 'title' | 'theme';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss'],
})
export class ArticlesComponent implements OnInit {
  articles: Article[] = [];
  loading = true;
  error = '';

  constructor(private articleService: ArticleService, private router: Router) {}

  ngOnInit(): void {
    this.loadArticles();
  }

  loadArticles(): void {
    this.articleService.getFeed().subscribe({
      next: (articles) => {
        this.articles = articles;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erreur lors du chargement des articles';
        this.loading = false;
      },
    });
  }

  createArticle(): void {
    this.router.navigate(['/articles/new']);
  }

  sortBy(type: SortType): void {
    switch (type) {
      case 'date':
        this.articles.sort(
          (a, b) =>
            new Date(b.created_at).getTime() - new Date(a.created_at).getTime()
        );
        break;
      case 'title':
        this.articles.sort((a, b) => a.title.localeCompare(b.title));
        break;
      case 'theme':
        this.articles.sort((a, b) => a.theme_name.localeCompare(b.theme_name));
        break;
    }
    // Force change detection
    this.articles = [...this.articles];
  }
}
