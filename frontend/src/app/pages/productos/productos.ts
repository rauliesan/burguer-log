import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { AuthService } from '../../services/auth.service';
import { ProductoResponse } from '../../models/producto.model';
import { CurrencyPipe } from '@angular/common';

@Component({
    selector: 'app-productos',
    imports: [FormsModule, CurrencyPipe],
    templateUrl: './productos.html',
    styleUrl: './productos.css'
})
export class ProductosComponent implements OnInit {
    productos: ProductoResponse[] = [];
    nombre = '';
    precio: number | null = null;
    error = '';
    success = '';
    loading = false;

    editandoId: number | null = null;
    editNombre = '';
    editPrecio: number | null = null;

    private burguerId = 0;

    constructor(private productoService: ProductoService, private authService: AuthService) { }

    ngOnInit(): void {
        this.burguerId = this.authService.getBurguerId() || 0;
        this.cargarProductos();
    }

    cargarProductos(): void {
        this.productoService.obtenerProductos(this.burguerId).subscribe({
            next: (productos) => this.productos = productos,
            error: () => this.error = 'Error al cargar productos.'
        });
    }

    crearProducto(): void {
        if (!this.nombre || !this.precio || this.precio <= 0) {
            this.error = 'Introduce un nombre y un precio válido.';
            return;
        }
        this.loading = true;
        this.error = '';
        this.success = '';
        this.productoService.crearProducto({
            nombre: this.nombre, precio: this.precio, idBurguer: this.burguerId
        }).subscribe({
            next: () => {
                this.success = `Producto "${this.nombre}" creado.`;
                this.nombre = '';
                this.precio = null;
                this.loading = false;
                this.cargarProductos();
            },
            error: (err) => {
                this.loading = false;
                this.error = err.error?.mensaje || 'Error al crear producto.';
            }
        });
    }

    empezarEdicion(producto: ProductoResponse): void {
        this.editandoId = producto.id;
        this.editNombre = producto.nombre;
        this.editPrecio = producto.precio;
        this.error = '';
        this.success = '';
    }

    cancelarEdicion(): void {
        this.editandoId = null;
    }

    guardarEdicion(): void {
        if (!this.editNombre || !this.editPrecio || this.editPrecio <= 0) {
            this.error = 'Nombre y precio son obligatorios.';
            return;
        }
        this.error = '';
        this.success = '';
        this.productoService.editarProducto(this.editandoId!, {
            nombre: this.editNombre, precio: this.editPrecio, idBurguer: this.burguerId
        }).subscribe({
            next: () => {
                this.success = 'Producto actualizado.';
                this.editandoId = null;
                this.cargarProductos();
            },
            error: (err) => {
                this.error = err.error?.mensaje || 'Error al editar.';
            }
        });
    }

    eliminarProducto(id: number, nombre: string): void {
        this.error = '';
        this.success = '';
        this.productoService.eliminarProducto(id).subscribe({
            next: () => {
                this.success = `"${nombre}" eliminado.`;
                this.cargarProductos();
            },
            error: (err) => {
                this.error = err.error?.mensaje || 'Error al eliminar.';
            }
        });
    }
}
