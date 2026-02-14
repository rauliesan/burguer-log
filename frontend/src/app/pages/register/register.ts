import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-register',
    imports: [FormsModule, RouterLink],
    templateUrl: './register.html',
    styleUrl: './register.css'
})
export class RegisterComponent {
    name = '';
    address = '';
    town = '';
    email = '';
    password = '';
    error = '';
    loading = false;

    constructor(private authService: AuthService, private router: Router) { }

    onSubmit(): void {
        if (!this.name || !this.email || !this.password) {
            this.error = 'Nombre, email y contraseña son obligatorios.';
            return;
        }
        this.loading = true;
        this.error = '';
        this.authService.register({
            name: this.name,
            address: this.address,
            town: this.town,
            email: this.email,
            password: this.password
        }).subscribe({
            next: () => {
                this.router.navigate(['/dashboard']);
            },
            error: (err) => {
                this.loading = false;
                this.error = err.error?.mensaje || 'Error al registrar.';
            }
        });
    }
}
