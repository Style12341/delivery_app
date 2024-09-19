/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package deso.delivery_app;

import deso.delivery_app.testing.Entrega3;

import java.util.Scanner;

/**
 * @author BMPC
 */
public class Delivery_app {

    public final static String SEARCHBYID = "1";
    public final static String SEARCHBYNAME = "2";

    public static void main(String[] args) {
        Entrega3.run();
    }

    public static void entrega1() {
        System.out.println("Hello World!");
        // Vendedores
        consignaVendedores();
        consignaClientes();
        Coordenada c1 = new Coordenada(36, -86);
        Coordenada c2 = new Coordenada(34, 118.5);
        Vendedor v = new Vendedor("Pepe", "Herndarias 833", "26-32787999-6", c1);
        Cliente c = new Cliente("Pepito", "Gomez", "27-28033214-8", "pepe@test.com", "Herndarias 836", c2);
        System.out.println("Distancia entre " + c1 + " y " + c2 + ": " + v.distancia(c) + " km"
        );
    }

    public static void consignaVendedores() {
        Vendedor[] vendedores = new Vendedor[3];
        vendedores[0] = new Vendedor("Pepe", "Herndarias 833", "27-40727599-8", new Coordenada(-40, -63));
        vendedores[1] = new Vendedor("Mario", "General Paz 6000", "26-13787921-6", new Coordenada(-40.5, -63.5));
        vendedores[2] = new Vendedor("Juan", "Pedro Zenteno 3000", "27-21722984-8", new Coordenada(-41, -64));

        Vendedor vendedor = null;

        try {
            vendedor = (Vendedor) handleInput(vendedores, "vendedor");
            if (vendedor != null) System.out.println(vendedor);
            else System.out.println("Vendedor no encontrado");
        } catch (ClassCastException e) {
            System.out.println("Vendedor no encontrado");
        }

        deleteRandom(vendedores);
    }

    public static void consignaClientes() {
        Cliente[] clientes = new Cliente[3];
        clientes[0] = new Cliente("Pepito", "Perez", "27-28033214-8", "pepe@test.com", "Herndarias 836", new Coordenada(-40, -63.5));
        clientes[1] = new Cliente("Marito", "Ledesma", "27-28033414-8", "mario@test.com", "General Paz 6002", new Coordenada(-40.5, -64));
        clientes[2] = new Cliente("Juancito", "Capri", "27-28033514-8", "juan@test.com", "Pedro Zenteno 3002", new Coordenada(-41, -64.5));

        Cliente cliente = null;
        try {
            cliente = (Cliente) handleInput(clientes, "clientes");
            if (cliente != null) System.out.println(cliente);
            else System.out.println("Cliente no encontrado");
        } catch (ClassCastException e) {
            System.out.println("Cliente no encontrado");
        }

        deleteRandom(clientes);

    }

    public static ISearcheable handleInput(ISearcheable[] arr, String name) {
        ISearcheable search = null;
        String input = "Ninguna Opcion";
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Ingrese la opcion para filtrar en " + name);
            System.out.println("1. Id\n2. Nombre");
            input = scan.nextLine();
        } while (!(SEARCHBYID.equals(input) || SEARCHBYNAME.equals(input)));
        if (input.equals(SEARCHBYID)) {
            search = idSearch(arr);
        } else if (input.equals(SEARCHBYNAME)) {
            search = nombreSearch(arr);
        }

        return search;
    }


    public static ISearcheable idSearch(ISearcheable[] arr) {
        ISearcheable result = null;
        System.out.println("Ingrese un id");
        long id = -1;
        Scanner scan = new Scanner(System.in);
        try {
            id = scan.nextInt();
        } catch (Exception e) {
            System.out.println("Argumento ilegal");
        }
        for (ISearcheable entidad : arr) {
            if (entidad.equalsId(id)) {
                result = entidad;
                break;
            }
        }
        return result;
    }

    public static ISearcheable nombreSearch(ISearcheable[] arr) {
        ISearcheable result = null;
        System.out.println("Ingrese un nombre");
        Scanner scan = new Scanner(System.in);
        String nombre = scan.nextLine();
        for (ISearcheable entidad : arr) {
            if (entidad.equalsNombre(nombre)) {
                result = entidad;
                break;
            }
        }
        return result;
    }

    public static void deleteRandom(ISearcheable[] arr) {
        int id = (int) (Math.random() * arr.length);
        System.out.println("Eliminando un cliente aleatorio id: " + (id + 1));
        arr[id] = null;
    }


}
