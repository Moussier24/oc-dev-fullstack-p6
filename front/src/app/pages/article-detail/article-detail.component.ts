import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  ArticleService,
  Article,
  Comment,
} from '../../services/article.service';
import { forkJoin, Subscription } from 'rxjs';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'],
})
export class ArticleDetailComponent implements OnInit, OnDestroy {
  article?: Article;
  comments: Comment[] = [];
  commentForm: FormGroup;
  loading = true;
  submitting = false;
  error = '';
  private subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private articleService: ArticleService,
    private fb: FormBuilder
  ) {
    this.commentForm = this.fb.group({
      content: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (isNaN(id)) {
      this.router.navigate(['/articles']);
      return;
    }

    this.loadArticleAndComments(id);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  loadArticleAndComments(id: number): void {
    const sub = forkJoin({
      article: this.articleService.getArticle(id),
      comments: this.articleService.getArticleComments(id),
    }).subscribe({
      next: ({ article, comments }) => {
        this.article = article;
        this.comments = comments;
        this.loading = false;
      },
      error: () => {
        this.error = "Erreur lors du chargement de l'article";
        this.loading = false;
      },
    });
    this.subscriptions.push(sub);
  }

  onSubmit(): void {
    if (this.commentForm.valid && this.article) {
      this.submitting = true;
      const sub = this.articleService
        .addComment(this.article.id, this.commentForm.value.content)
        .subscribe({
          next: (comment) => {
            this.comments = [comment, ...this.comments];
            this.commentForm.reset();
            this.submitting = false;
          },
          error: () => {
            this.error = "Erreur lors de l'ajout du commentaire";
            this.submitting = false;
          },
        });
      this.subscriptions.push(sub);
    }
  }

  goBack(): void {
    this.router.navigate(['/articles']);
  }
}
