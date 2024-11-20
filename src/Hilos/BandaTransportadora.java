package Hilos;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class BandaTransportadora extends JPanel {
    private CopyOnWriteArrayList<JPanel> productos; // Manejo seguro para concurrencia

    public BandaTransportadora() {
        setLayout(null); // Layout manual para mover las cajas
        setBackground(Color.BLUE);
        setBounds(50, 50, 600, 100); // Ajusta el tamaño y posición según sea necesario

        productos = new CopyOnWriteArrayList<>();
    }

    public void agregarProductoBanda() {
        JPanel producto = new JPanel();
        producto.setBackground(Color.ORANGE);
        producto.setBounds(10, 35, 30, 30); // Posición inicial de la caja
        productos.add(producto); // Agregar a la lista de productos
        add(producto); // Agregar al panel
        repaint();
        revalidate();

        // Mover la cajita
        moverProducto(producto);
    }

    private void moverProducto(JPanel producto) {
        new Thread(() -> {
            try {
                for (int x = 10; x <= 570; x += 10) { // Mover hacia la derecha
                    producto.setBounds(x, 35, 30, 30);
                    Thread.sleep(100); // Velocidad de movimiento
                }
                removerProductoBanda(producto); // Eliminar al final del recorrido
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void removerProductoBanda(JPanel producto) {
        if (productos.contains(producto)) {
            productos.remove(producto); // Eliminar de la lista
            remove(producto); // Eliminar del panel
            repaint();
            revalidate();
        }
    }
}
