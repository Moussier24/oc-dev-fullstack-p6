import { Component, OnInit, OnDestroy } from '@angular/core';
import { ThemeService, Theme } from '../../services/theme.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit, OnDestroy {
  themes: Theme[] = [];
  loading = true;
  error = '';
  private subscriptions: Subscription[] = [];

  constructor(private themeService: ThemeService) {}

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
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erreur lors du chargement des thèmes';
        this.loading = false;
      },
    });
    this.subscriptions.push(sub);
  }

  subscribe(theme: Theme): void {
    const sub = this.themeService.subscribe(theme.id).subscribe({
      next: () => {
        const index = this.themes.findIndex((t) => t.id === theme.id);
        if (index !== -1) {
          this.themes[index] = {
            ...theme,
            is_subscribed: true,
            subscribers_count: theme.subscribers_count + 1,
          };
          // Force change detection
          this.themes = [...this.themes];
        }
      },
      error: () => {
        this.error = "Erreur lors de l'abonnement au thème";
      },
    });
    this.subscriptions.push(sub);
  }
}
