import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { MesaService } from '../../services/mesa.service';
import { ProductoService } from '../../services/producto.service';
import { PedidoService } from '../../services/pedido.service';
import { BurguerResponse } from '../../models/burguer.model';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.html',
    styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {
    burguer: BurguerResponse | null = null;
    totalMesas = 0;
    totalProductos = 0;
    pedidosActivos = 0;
    totalPedidos = 0;

    constructor(
        private authService: AuthService,
        private mesaService: MesaService,
        private productoService: ProductoService,
        private pedidoService: PedidoService
    ) { }

    ngOnInit(): void {
        this.burguer = this.authService.getBurguer();
        if (!this.burguer) return;

        const id = this.burguer.id;

        this.mesaService.obtenerMesas(id).subscribe({
            next: (mesas) => this.totalMesas = mesas.length
        });

        this.productoService.obtenerProductos(id).subscribe({
            next: (productos) => this.totalProductos = productos.length
        });

        this.pedidoService.obtenerPedidos(id).subscribe({
            next: (pedidos) => {
                this.totalPedidos = pedidos.length;
                this.pedidosActivos = pedidos.filter(p => !p.finalizado).length;
            }
        });
    }
}
