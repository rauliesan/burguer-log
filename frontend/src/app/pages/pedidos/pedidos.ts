import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { PedidoService } from '../../services/pedido.service';
import { MesaService } from '../../services/mesa.service';
import { ProductoService } from '../../services/producto.service';
import { AuthService } from '../../services/auth.service';
import { PedidoResponse, LineaPedidoDTO } from '../../models/pedido.model';
import { MesaResponse } from '../../models/mesa.model';
import { ProductoResponse } from '../../models/producto.model';

@Component({
    selector: 'app-pedidos',
    imports: [FormsModule, CurrencyPipe, DatePipe],
    templateUrl: './pedidos.html',
    styleUrl: './pedidos.css'
})
export class PedidosComponent implements OnInit {
    pedidos: PedidoResponse[] = [];
    mesas: MesaResponse[] = [];
    productos: ProductoResponse[] = [];

    // Nuevo pedido
    mesaSeleccionada: number | null = null;
    lineas: { productoId: number; nombre: string; precio: number; cantidad: number }[] = [];

    error = '';
    success = '';
    loading = false;
    creandoPedido = false;
    private burguerId = 0;

    constructor(
        private pedidoService: PedidoService,
        private mesaService: MesaService,
        private productoService: ProductoService,
        private authService: AuthService
    ) { }

    ngOnInit(): void {
        this.burguerId = this.authService.getBurguerId() || 0;
        this.cargarDatos();
    }

    cargarDatos(): void {
        this.pedidoService.obtenerPedidos(this.burguerId).subscribe({
            next: (pedidos) => this.pedidos = pedidos.sort((a, b) => {
                if (a.finalizado !== b.finalizado) return a.finalizado ? 1 : -1;
                return new Date(b.fechaInicio).getTime() - new Date(a.fechaInicio).getTime();
            })
        });
        this.mesaService.obtenerMesas(this.burguerId).subscribe({
            next: (mesas) => this.mesas = mesas.sort((a, b) => a.numero - b.numero)
        });
        this.productoService.obtenerProductos(this.burguerId).subscribe({
            next: (productos) => this.productos = productos
        });
    }

    toggleCrearPedido(): void {
        this.creandoPedido = !this.creandoPedido;
        this.mesaSeleccionada = null;
        this.lineas = [];
        this.error = '';
        this.success = '';
    }

    agregarProducto(producto: ProductoResponse): void {
        const existente = this.lineas.find(l => l.productoId === producto.id);
        if (existente) {
            existente.cantidad++;
        } else {
            this.lineas.push({
                productoId: producto.id,
                nombre: producto.nombre,
                precio: producto.precio,
                cantidad: 1
            });
        }
    }

    eliminarLinea(index: number): void {
        this.lineas.splice(index, 1);
    }

    cambiarCantidad(index: number, delta: number): void {
        this.lineas[index].cantidad += delta;
        if (this.lineas[index].cantidad <= 0) {
            this.lineas.splice(index, 1);
        }
    }

    get totalPedido(): number {
        return this.lineas.reduce((sum, l) => sum + l.precio * l.cantidad, 0);
    }

    crearPedido(): void {
        if (!this.mesaSeleccionada) {
            this.error = 'Selecciona una mesa.';
            return;
        }
        if (this.lineas.length === 0) {
            this.error = 'Añade al menos un producto.';
            return;
        }
        this.loading = true;
        this.error = '';

        // We need the mesa ID, not the number, for CreatePedido.
        const lineasDTO: LineaPedidoDTO[] = this.lineas.map(l => ({
            productoId: l.productoId,
            cantidad: l.cantidad
        }));

        this.pedidoService.crearPedido({
            mesaId: this.mesaSeleccionada,
            lineas: lineasDTO
        }).subscribe({
            next: () => {
                this.success = 'Pedido creado correctamente.';
                this.creandoPedido = false;
                this.lineas = [];
                this.mesaSeleccionada = null;
                this.loading = false;
                this.cargarDatos();
            },
            error: (err) => {
                this.loading = false;
                this.error = err.error?.mensaje || 'Error al crear el pedido.';
            }
        });
    }

    cerrarPedido(id: number): void {
        this.error = '';
        this.success = '';
        this.pedidoService.cerrarPedido(id).subscribe({
            next: (res) => {
                this.success = `Pedido #${id} cerrado. Total: ${res.precioFinal?.toFixed(2)}€`;
                this.cargarDatos();
            },
            error: (err) => {
                this.error = err.error?.mensaje || 'Error al cerrar el pedido.';
            }
        });
    }
}
