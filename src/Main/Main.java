package Main;

import GUI.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);

        while (true) {
            // Solicitar la cantidad de paquetes al usuario
            try {
                String input = JOptionPane.showInputDialog(null, "Ingrese la cantidad de paquetes (o 0 para salir):", 
                                                           "Configuración de Producción", JOptionPane.QUESTION_MESSAGE);

                if (input == null || input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
                    continue;
                }

                int cantidadPaquetes = Integer.parseInt(input);

                // Validar entrada del usuario
                if (cantidadPaquetes == 0) {
                    JOptionPane.showMessageDialog(null, "El programa se cerrará. ¡Gracias!");
                    System.exit(0);
                }

                if (cantidadPaquetes < 0) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número mayor o igual a 0.");
                    continue;
                }

                // Simulación de productos en la banda transportadora
                new Thread(() -> {
                    try {
                        for (int i = 0; i < cantidadPaquetes; i++) {
                            ventana.agregarProductoBanda();
                            Thread.sleep(500); // Tiempo entre paquetes
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Esperar hasta que todos los productos hayan sido procesados (simulación simple)
                Thread.sleep(cantidadPaquetes * 500 + 1000);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
