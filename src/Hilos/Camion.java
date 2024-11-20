package Hilos;

import javax.swing.*;

public class Camion {
    private int capacidadMaxima;
    private int productosActuales;
    private JLabel contador; // Etiqueta para mostrar la cantidad de productos en este camión

    public Camion(int capacidadMaxima, JLabel contador) {
        this.capacidadMaxima = capacidadMaxima;
        this.productosActuales = 0;
        this.contador = contador;
        actualizarContador();
    }

    // Método para agregar un producto al camión
    public boolean agregarProducto() {
        if (productosActuales < capacidadMaxima) {
            productosActuales++;
            actualizarContador();
            return true; // Producto agregado exitosamente
        }
        return false; // Camión lleno
    }

    // Verifica si el camión está lleno
    public boolean estaLleno() {
        return productosActuales >= capacidadMaxima;
    }

    // Actualiza el contador gráfico de este camión
    private void actualizarContador() {
        contador.setText(String.valueOf(productosActuales));
    }

    // Reinicia el camión para ser reutilizado
    public void reiniciar() {
        productosActuales = 0;
        actualizarContador();
    }
}
