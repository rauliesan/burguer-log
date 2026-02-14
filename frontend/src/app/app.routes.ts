import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', loadComponent: () => import('./pages/login/login').then(m => m.LoginComponent) },
    { path: 'register', loadComponent: () => import('./pages/register/register').then(m => m.RegisterComponent) },
    {
        path: '',
        loadComponent: () => import('./pages/layout/layout').then(m => m.LayoutComponent),
        canActivate: [authGuard],
        children: [
            { path: 'dashboard', loadComponent: () => import('./pages/dashboard/dashboard').then(m => m.DashboardComponent) },
            { path: 'mesas', loadComponent: () => import('./pages/mesas/mesas').then(m => m.MesasComponent) },
            { path: 'productos', loadComponent: () => import('./pages/productos/productos').then(m => m.ProductosComponent) },
            { path: 'pedidos', loadComponent: () => import('./pages/pedidos/pedidos').then(m => m.PedidosComponent) },
        ]
    },
    { path: '**', redirectTo: 'login' }
];
