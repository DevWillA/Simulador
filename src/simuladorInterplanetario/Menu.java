package simuladorInterplanetario;

import java.util.Scanner;

public class Menu {

    // Funciones de menus
    public static String[] menuNaves(int destino) {

        System.out.println("Para su viaje a " + Nave.planetSelect(destino) + " necesitamos una nave.");

        Mensajes.mensajesStop1();

        return new String[] {
                "¿Qué tipo de nave desea?",
                "1. Nave: Star Voyager | Capacidad: 3 personas | Velocidad: 100000 km/h",
                "2. Nave: Cosmo Cruiser | Capacidad: 2 personas | Velocidad: 89000 km/h",
                "3. Nave: Galaxy Explorer | Capacidad: 4 personas | Velocidad: 80000 km/h",
                "4. Salir "
            };
            
    }

    public static boolean confirmarSalida() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Está seguro que desea salir? (S/N): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s");
    }

    public static void mostrarMenuNaves(int destino) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] menu = menuNaves(destino);

            for (String opcion : menu) {
                System.out.println(opcion);
            }

            System.out.print("Seleccione una opción: ");
            int seleccion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            if (seleccion == 4) {
                if (confirmarSalida()) {
                    System.out.println("Saliendo del sistema... ¡Hasta luego!");
                    System.exit(0);
                } else {
                    System.out.println("Volviendo al menú...\n");
                }
            } else {
                System.out.println("Seleccionaste la opción " + seleccion);
                break; // Continuar con el flujo si se eligió una nave
            }
        }
    }

    public static String[] menuSecond() {
        return new String[] {
                "¿Ahora que desea hacer?",
                "1. Verificar los recursos de la nave",
                "2. Modificar los recursos de la nave",
                "3. Verificar el estado de la nave",
                "4. Iniciar el viaje"
        };
       
    }

    public static String[] menuInicial() {
        return new String[] {
                "¿Cuál es tu destino?",
                "1. Mercurio",
                "2. Venus",
                "3. Marte",
                "4. Júpiter",
                "5. Saturno",
                "6. Urano",
                "7. Neptuno"
        };

       
    }

    public static String[] menuTrajes() {
        return new String[] {
            "Seleccione el tipo de traje espacial:",
            "1. Traje Atmosférico (planetas con aire respirable)",
            "2. Traje Presurizado (ambientes hostiles con presión baja)",
            "3. Traje Blindado (ambientes extremos y con radiación)",
            "4. Salir"
        };
    }
}
