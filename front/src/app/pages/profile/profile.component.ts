import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService, UserProfile } from '../../services/user.service';
import { Theme } from '../../services/theme.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  subscriptions: Theme[] = [];
  loading = true;
  saving = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) {
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });

    // Initialiser le formulaire avec les données du localStorage
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.profileForm.patchValue({
        username: currentUser.username,
        email: currentUser.email,
      });
      this.loading = false;
    }
  }

  ngOnInit(): void {
    this.loadSubscriptions();
  }

  loadSubscriptions(): void {
    this.error = '';
    this.userService.getSubscriptions().subscribe({
      next: (subscriptions) => {
        this.subscriptions = subscriptions;
      },
      error: (error) => {
        this.error =
          error.error?.message || 'Erreur lors du chargement des abonnements';
      },
    });
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      this.error = '';
      this.saving = true;
      this.userService.updateProfile(this.profileForm.value).subscribe({
        next: (profile) => {
          const currentUser = this.authService.getCurrentUser();
          if (currentUser) {
            this.authService.updateCurrentUser({
              ...currentUser,
              username: profile.username,
              email: profile.email,
            });
          }
          this.saving = false;
        },
        error: (error) => {
          this.error =
            error.error?.message || 'Erreur lors de la mise à jour du profil';
          this.saving = false;
        },
      });
    }
  }

  unsubscribe(theme: Theme): void {
    this.error = '';
    this.userService.unsubscribe(theme.id).subscribe({
      next: () => {
        this.subscriptions = this.subscriptions.filter(
          (t) => t.id !== theme.id
        );
      },
      error: (error) => {
        this.error = error.error?.message || 'Erreur lors du désabonnement';
      },
    });
  }

  logout(): void {
    this.authService.logout();
  }
}
