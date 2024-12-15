import { Component, Input } from '@angular/core';

interface Theme {
  id: number;
  name: string;
  description?: string;
  created_at: string;
  updated_at?: string;
}

@Component({
  selector: 'app-theme-card',
  templateUrl: './theme-card.component.html',
  styleUrls: ['./theme-card.component.scss'],
})
export class ThemeCardComponent {
  @Input() theme!: Theme;
}
