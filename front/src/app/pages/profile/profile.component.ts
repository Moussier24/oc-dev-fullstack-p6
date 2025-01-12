import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService, UserProfile } from '../../services/user.service';
import { Theme } from '../../services/theme.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy {
  profileForm: FormGroup;
  subscriptions: Theme[] = [];
  loading = true;
  saving = false;
  error = '';
  private subs: Subscription[] = [];

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

  ngOnDestroy(): void {
    this.subs.forEach((sub) => sub.unsubscribe());
  }

  loadSubscriptions(): void {
    this.error = '';
    const sub = this.userService.getSubscriptions().subscribe({
      next: (subscriptions) => {
        this.subscriptions = subscriptions;
      },
      error: (error) => {
        this.error =
          error.error?.message || 'Erreur lors du chargement des abonnements';
      },
    });
    this.subs.push(sub);
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      this.error = '';
      this.saving = true;
      const sub = this.userService
        .updateProfile(this.profileForm.value)
        .subscribe({
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
      this.subs.push(sub);
    }
  }

  unsubscribe(theme: Theme): void {
    this.error = '';
    const sub = this.userService.unsubscribe(theme.id).subscribe({
      next: () => {
        this.subscriptions = this.subscriptions.filter(
          (t) => t.id !== theme.id
        );
      },
      error: (error) => {
        this.error = error.error?.message || 'Erreur lors du désabonnement';
      },
    });
    this.subs.push(sub);
  }

  logout(): void {
    this.authService.logout();
  }
}
