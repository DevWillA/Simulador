package simuladorInterplanetario;

public class Menu {

    // Funciones de menus
    public static String[] menuNaves(int destino) {

        System.out.println("Para su viaje a " + Nave.planetSelect(destino) + " necesitamos una nave.");

        Mensajes.mensajesStop1();

        return new String[] {
                "¿Qué tipo de nave desea?",
                "1. Nave: Star Voyager | Capacidad: 3 personas | Velocidad: 100000 km/h",
                "2. Nave: Cosmo Cruiser | Capacidad: 2 personas | Velocidad: 89000 km/h",
                "3. Nave: Galaxy Explorer | Capacidad: 4 personas | Velocidad: 80000 km/h"
        };

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
}
