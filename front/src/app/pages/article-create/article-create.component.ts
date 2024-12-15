import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { ThemeService, Theme } from '../../services/theme.service';

@Component({
  selector: 'app-article-create',
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss'],
})
export class ArticleCreateComponent implements OnInit {
  articleForm: FormGroup;
  themes: Theme[] = [];
  loading = false;
  error = '';

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

  loadThemes(): void {
    this.themeService.getThemes().subscribe({
      next: (themes) => {
        this.themes = themes;
      },
      error: () => {
        this.error = 'Erreur lors du chargement des thèmes';
      },
    });
  }

  onSubmit(): void {
    if (this.articleForm.valid) {
      this.loading = true;
      this.articleService.createArticle(this.articleForm.value).subscribe({
        next: () => {
          this.router.navigate(['/articles']);
        },
        error: (error) => {
          this.loading = false;
          this.error =
            error.error?.message || "Erreur lors de la création de l'article";
        },
      });
    }
  }
}
