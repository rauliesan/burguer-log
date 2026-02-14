import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-navbar',
    imports: [RouterLink, RouterLinkActive],
    templateUrl: './navbar.html',
    styleUrl: './navbar.css'
})
export class NavbarComponent {
    burguerName = '';

    constructor(private authService: AuthService, private router: Router) {
        const burguer = this.authService.getBurguer();
        this.burguerName = burguer?.name || 'Burguer';
    }

    logout(): void {
        this.authService.logout();
        this.router.navigate(['/login']);
    }
}
