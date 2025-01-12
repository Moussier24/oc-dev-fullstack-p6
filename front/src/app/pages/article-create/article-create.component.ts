import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { ThemeService, Theme } from '../../services/theme.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-article-create',
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss'],
})
export class ArticleCreateComponent implements OnInit, OnDestroy {
  articleForm: FormGroup;
  themes: Theme[] = [];
  loading = false;
  error = '';
  private subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private articleService: ArticleService,
    private themeService: ThemeService,
    private router: Router
  ) {
    this.articleForm = this.fb.group({
      title: ['', [Validators.required]],
      content: ['', [Validators.required]],
      theme_id: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.loadThemes();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  loadThemes(): void {
    const sub = this.themeService.getThemes().subscribe({
      next: (themes) => {
        this.themes = themes;
      },
      error: () => {
        this.error = 'Erreur lors du chargement des thèmes';
      },
    });
    this.subscriptions.push(sub);
  }

  onSubmit(): void {
    if (this.articleForm.valid) {
      this.loading = true;
      const sub = this.articleService
        .createArticle(this.articleForm.value)
        .subscribe({
          next: () => {
            this.router.navigate(['/articles']);
          },
          error: (error) => {
            this.loading = false;
            this.error =
              error.error?.message || "Erreur lors de la création de l'article";
          },
        });
      this.subscriptions.push(sub);
    }
  }
}
