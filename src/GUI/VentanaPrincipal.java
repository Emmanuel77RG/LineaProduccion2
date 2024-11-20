package GUI;

import Hilos.Camion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {
    private JPanel panelBanda;
    private JLabel contadorPorEmpaquetar;
    private JLabel contadorEmpaquetados;
    private ArrayList<Camion> camiones;
    private ArrayList<JPanel> panelesCamiones; // Paneles de los camiones
    private ArrayList<JLabel> contadoresCamiones; // Contadores de los camiones

    public VentanaPrincipal() {
        setTitle("Simulación de Línea de Producción");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Contadores de artículos
        contadorPorEmpaquetar = new JLabel("Productos por empaquetar: 0");
        contadorPorEmpaquetar.setBounds(50, 10, 300, 30);
        add(contadorPorEmpaquetar);

        contadorEmpaquetados = new JLabel("Productos empaquetados: 0");
        contadorEmpaquetados.setBounds(400, 10, 300, 30);
        add(contadorEmpaquetados);

        // Banda transportadora
        panelBanda = new JPanel();
        panelBanda.setBounds(50, 50, 600, 100);
        panelBanda.setBackground(Color.BLUE);
        panelBanda.setLayout(null); // Layout manual para mover las cajitas
        add(panelBanda);

        // Inicializar camiones
        camiones = new ArrayList<>();
        panelesCamiones = new ArrayList<>();
        contadoresCamiones = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            JLabel contadorCamion = new JLabel("0");
            contadorCamion.setHorizontalAlignment(SwingConstants.CENTER);
            contadorCamion.setBounds(700, 30 + (i * 100), 100, 20);
            add(contadorCamion);
            contadoresCamiones.add(contadorCamion);

            JPanel camionPanel = new JPanel();
            camionPanel.setBounds(700, 50 + (i * 100), 100, 60);
            camionPanel.setBackground(Color.RED);
            add(camionPanel);
            panelesCamiones.add(camionPanel);

            // Crear objeto Camion y agregarlo a la lista
            Camion camion = new Camion(20, contadorCamion);
            camiones.add(camion);
        }
    }

    public void actualizarPorEmpaquetar(int cantidad) {
        contadorPorEmpaquetar.setText("Productos por empaquetar: " + cantidad);
    }

    public void actualizarEmpaquetados(int cantidad) {
        contadorEmpaquetados.setText("Productos empaquetados: " + cantidad);
    }

    public void agregarProductoBanda() {
        JPanel producto = new JPanel();
        producto.setBackground(Color.ORANGE);
        producto.setBounds(10, 35, 30, 30); // Posición inicial
        panelBanda.add(producto);
        panelBanda.revalidate();
        panelBanda.repaint();

        // Iniciar animación de la cajita
        moverCajita(producto);
    }

    public void moverCajita(JPanel producto) {
        new Thread(() -> {
            try {
                for (int x = 10; x <= 570; x += 10) { // Mover la cajita a la derecha
                    producto.setBounds(x, 35, 30, 30);
                    Thread.sleep(100); // Velocidad de movimiento
                }
                panelBanda.remove(producto); // Remover la cajita al final de la banda
                panelBanda.revalidate();
                panelBanda.repaint();

                // Agregar producto a un camión
                agregarProductoCamion();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void agregarProductoCamion() {
        for (int i = 0; i < camiones.size(); i++) {
            Camion camion = camiones.get(i);
            if (!camion.estaLleno()) {
                camion.agregarProducto();
                if (camion.estaLleno()) {
                    eliminarCamion(i); // Eliminar camión si está lleno
                }
                break;
            }
        }
    }

    public void eliminarCamion(int index) {
        // Eliminar camión lleno
        JPanel camionPanel = panelesCamiones.get(index);
        JLabel contadorCamion = contadoresCamiones.get(index);

        // Animación para desaparecer el camión
        new Thread(() -> {
            try {
                for (int alpha = 255; alpha >= 0; alpha -= 15) {
                    camionPanel.setBackground(new Color(255, 0, 0, Math.max(alpha, 0)));
                    Thread.sleep(50);
                }
                camionPanel.setVisible(false);
                contadorCamion.setVisible(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public boolean todosCamionesLlenos() {
        for (Camion camion : camiones) {
            if (!camion.estaLleno()) {
                return false;
            }
        }
        return true;
    }
}