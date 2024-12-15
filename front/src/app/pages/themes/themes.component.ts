import { Component, OnInit } from '@angular/core';
import { ThemeService, Theme } from '../../services/theme.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  themes: Theme[] = [];
  loading = true;
  error = '';

  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    this.loadThemes();
  }

  loadThemes(): void {
    this.themeService.getThemes().subscribe({
      next: (themes) => {
        this.themes = themes;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erreur lors du chargement des thèmes';
        this.loading = false;
      },
    });
  }

  subscribe(theme: Theme): void {
    this.themeService.subscribe(theme.id).subscribe({
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
  }
}
