/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package deso.delivery_app;

import java.util.Scanner;

/**
 *
 * @author BMPC
 */
public class Delivery_app {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        // Vendedores
        consignaVendedores();
        consignaClientes();
        Coordenada c1 = new Coordenada(36, -86);
        Coordenada c2 = new Coordenada(34, 118.5);
        Vendedor v = new Vendedor("Pepe", "Herndarias 833", c1);
        Cliente c = new Cliente("Pepito", "27-28033214-8", "pepe@test.com", "Herndarias 836", c2);
        System.out.println("Distancia entre " + c1 + " y " + c2 + ": " + v.distancia(c) + " km"
        );
    }

    public static void consignaVendedores() {
        Vendedor[] vendedores = new Vendedor[3];
        vendedores[0] = new Vendedor("Pepe", "Herndarias 833", new Coordenada(-40, -63));
        vendedores[1] = new Vendedor("Mario", "General Paz 6000", new Coordenada(-40.5, -63.5));
        vendedores[2] = new Vendedor("Juan", "Pedro Zenteno 3000", new Coordenada(-41, -64));
        // xd
        String input;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Ingrese la opcion para filtrar por vendedor");
            System.out.println("1. Id\n2. Nombre");
            input = scan.nextLine();
        } while (!("1".equals(input) || "2".equals(input)));
        Vendedor v = null;
        switch (input) {
            case "1" -> {
                System.out.println("Ingrese un id");
                int id = -1;
                try {
                    id = scan.nextInt();
                } catch (Exception e) {
                    System.out.println("Argumento ilegal");
                }
                for (Vendedor vendedor : vendedores) {
                    if (vendedor.equalsId(id)) {
                        v = vendedor;
                        System.out.println(v);
                        break;
                    }
                }
            }
            case "2" -> {
                System.out.println("Ingrese un nombre");
                String nombre = scan.nextLine();
                for (Vendedor vendedor : vendedores) {
                    if (vendedor.equalsNombre(nombre)) {
                        v = vendedor;
                        System.out.println(v);
                        break;
                    }
                }
            }
        }
        if (v == null) {
            System.out.println("Vendedor no encontrado");
        }

        int id = (int) (Math.random() * 3);
        System.out.println("Eliminando un vendedor aleatorio id: " + (id + 1));
        vendedores[id] = null;
    }

    public static void consignaClientes() {
        Cliente[] clientes = new Cliente[3];
        clientes[0] = new Cliente("Pepito", "27-28033214-8", "pepe@test.com", "Herndarias 836", new Coordenada(-40, -63.5));
        clientes[1] = new Cliente("Marito", "27-28033414-8", "mario@test.com", "General Paz 6002", new Coordenada(-40.5, -64));
        clientes[2] = new Cliente("Juancito", "27-28033514-8", "juan@test.com", "Pedro Zenteno 3002", new Coordenada(-41, -64.5));
        // xd
        String input;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Ingrese la opcion para filtrar por cliente");
            System.out.println("1. Id\n2. Nombre");
            input = scan.nextLine();
        } while (!("1".equals(input) || "2".equals(input)));
        Cliente v = null;
        switch (input) {
            case "1" -> {
                System.out.println("Ingrese un id");
                int id = -1;
                try {
                    id = scan.nextInt();
                } catch (Exception e) {
                    System.out.println("Argumento ilegal");
                }
                for (Cliente cliente : clientes) {
                    if (cliente.equalsId(id)) {
                        v = cliente;
                        System.out.println(v);
                        break;
                    }
                }
            }
            case "2" -> {
                System.out.println("Ingrese un nombre");
                String nombre = scan.nextLine();
                for (Cliente cliente : clientes) {
                    if (cliente.equalsNombre(nombre)) {
                        v = cliente;
                        System.out.println(v);
                        break;
                    }
                }
            }
        }
        if (v == null) {
            System.out.println("Cliente no encontrado");
        }

        int id = (int) (Math.random() * 3);
        System.out.print("Eliminando un cliente aleatorio id: " + (id + 1));
        clientes[id] = null;
    }
}
