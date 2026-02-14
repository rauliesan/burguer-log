import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MesaService } from '../../services/mesa.service';
import { AuthService } from '../../services/auth.service';
import { MesaResponse } from '../../models/mesa.model';

@Component({
    selector: 'app-mesas',
    imports: [FormsModule],
    templateUrl: './mesas.html',
    styleUrl: './mesas.css'
})
export class MesasComponent implements OnInit {
    mesas: MesaResponse[] = [];
    nuevoNumero: number | null = null;
    error = '';
    success = '';
    loading = false;
    private burguerId = 0;

    constructor(private mesaService: MesaService, private authService: AuthService) { }

    ngOnInit(): void {
        this.burguerId = this.authService.getBurguerId() || 0;
        this.cargarMesas();
    }

    cargarMesas(): void {
        this.mesaService.obtenerMesas(this.burguerId).subscribe({
            next: (mesas) => this.mesas = mesas.sort((a, b) => a.numero - b.numero),
            error: () => this.error = 'Error al cargar las mesas.'
        });
    }

    crearMesa(): void {
        if (!this.nuevoNumero || this.nuevoNumero < 1) {
            this.error = 'Introduce un número de mesa válido.';
            return;
        }
        this.loading = true;
        this.error = '';
        this.success = '';
        this.mesaService.crearMesa({ numero: this.nuevoNumero, idBurguer: this.burguerId }).subscribe({
            next: () => {
                this.success = `Mesa ${this.nuevoNumero} creada correctamente.`;
                this.nuevoNumero = null;
                this.loading = false;
                this.cargarMesas();
            },
            error: (err) => {
                this.loading = false;
                this.error = err.error?.mensaje || 'Error al crear la mesa.';
            }
        });
    }

    eliminarMesa(numero: number): void {
        this.error = '';
        this.success = '';
        this.mesaService.eliminarMesa(this.burguerId, numero).subscribe({
            next: () => {
                this.success = `Mesa ${numero} eliminada.`;
                this.cargarMesas();
            },
            error: (err) => {
                this.error = err.error?.mensaje || 'Error al eliminar la mesa.';
            }
        });
    }
}
