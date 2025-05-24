package simuladorInterplanetario;

import java.util.Scanner;

public class Nave {

    private static double fuel = 0;
    private static double fuelCosumido = 0;
    private static double fuelReerva = 0;
    private static double oxigeno = 0;
    private static double oxigenoCosumido = 0;
    private static double oxigenoReserva = 0;
    private static int reparacionesRealizadas = 0;
    private static boolean naveFallandoOxigeno = SimuladorInterplanetario.isNaveFallandoOxigeno();
    private static boolean naveFallandoGasolina = SimuladorInterplanetario.isNaveFallandoGasolina();

    // Funcion que valida si la nave tiene reservas de combustible y las consume de
    // ser necesario
    public static void consumirReservaCombustible() {
        fuel = SimuladorInterplanetario.getFuel();
        fuelCosumido = SimuladorInterplanetario.getFuelCosumido();
        fuelReerva = SimuladorInterplanetario.getFuelReerva();

        if ((fuel - fuelCosumido) <= fuelReerva && fuelReerva > 0) {

            System.out.println("¡Atención! El combustible se esta agotando, se pasaran las reservas a la nave.");
            Mensajes.mensajesStop1();
            fuel += fuelReerva;
            SimuladorInterplanetario.setFuel(fuel);
            SimuladorInterplanetario.setFuelReerva(0); // Actualiza el combustible en la simulación

        }
    }

    public static String naveVolando(int count) {
        return switch (count) {
            case 1 -> """
                            _______________
                    ----   |        ___    \\
                    ---    |       |   |     \\
                    ---    |       |___|     /
                    ----   |_______________/
                    """;
            case 2 -> """
                                _______________
                    --------   |        ___    \\
                    -------    |       |   |     \\
                    -------    |       |___|     /
                    --------   |_______________/
                    """;
            case 3 -> """
                                   _______________
                    -----------   |        ___    \\
                    ----------    |       |   |     \\
                    ----------    |       |___|     /
                    -----------   |_______________/
                    """;
            case 4 -> """
                                       _______________
                    ---------------   |        ___    \\
                    --------------    |       |   |     \\
                    --------------    |       |___|     /
                    ---------------   |_______________/
                    """;
            case 5 -> """
                                           _______________
                    -------------------   |        ___    \\
                    ------------------    |       |   |     \\
                    ------------------    |       |___|     /
                    -------------------   |_______________/
                    """;
            case 6 -> """
                                               _______________
                    -----------------------   |        ___    \\
                    ----------------------    |       |   |     \\
                    ----------------------    |       |___|     /
                    -----------------------   |_______________/
                    """;
            case 7 -> """
                                                   _______________
                    ---------------------------   |        ___    \\
                    --------------------------    |       |   |     \\
                    --------------------------    |       |___|     /
                    ---------------------------   |_______________/
                    """;
            case 8 -> """
                                                       _______________
                    -------------------------------   |        ___    \\
                    ------------------------------    |       |   |     \\
                    ------------------------------    |       |___|     /
                    -------------------------------   |_______________/
                    """;
            case 9 -> """
                                                           _______________
                    -----------------------------------   |        ___    \\
                    ----------------------------------    |       |   |     \\
                    ----------------------------------    |       |___|     /
                    -----------------------------------   |_______________/
                    """;
            case 10 -> """
                                                               _______________
                    ---------------------------------------   |        ___    \\
                    --------------------------------------    |       |   |     \\
                    --------------------------------------    |       |___|     /
                    ---------------------------------------   |_______________/
                    """;
            default -> """
                        _______________
                       |        ___    \\
                       |       |   |     \\
                       |       |___|     /
                       |_______________/
                    """;

        };
    }

    // Funcion que valida si la nave tiene reservas de oxígeno y las consume de ser
    // necesario
    public static void consumirReservaOxigeno() {
        oxigeno = SimuladorInterplanetario.getOxigeno();
        oxigenoCosumido = SimuladorInterplanetario.getOxigenoCosumido();
        oxigenoReserva = SimuladorInterplanetario.getOxigenoReserva();

        if ((oxigeno - oxigenoCosumido) <= oxigenoReserva && oxigenoReserva > 0) {

            System.out.println("¡Atención! El oxígeno se esta agotando, se pasaran las reservas a la nave.");
            Mensajes.mensajesStop1();
            oxigeno += oxigenoReserva;
            SimuladorInterplanetario.setOxigeno(oxigeno);
            SimuladorInterplanetario.setOxigenoReserva(0);

        }
    }

     // Función se encarga de validar si los recursos (combustible y oxígeno) son
    // suficientes para la siguiente etapa.
    public static boolean validarRecursos(double fuelRestante, double oxigenoRestante) {
        if (fuelRestante <= 0) {
            System.out.println("¡Alerta! El combustible se ha agotado antes de tiempo. ¡La nave se detendrá!");
            Mensajes.mensajesStop1();
            return false; // Retorna false si el combustible se ha agotado
        }

        if (oxigenoRestante <= 0) {
            System.out.println("¡Alerta! El oxígeno se ha agotado antes de tiempo. ¡La nave se detendrá!");
            Mensajes.mensajesStop1();
            return false; // Retorna false si el oxígeno se ha agotado
        }

        return true; // Si los recursos están bien, continúa
    }

     // Funcion que determina la velocidad de una nave
    public static int velocidadNave(int nave) {
        return switch (nave) {
            case 1 -> 100000;
            case 2 -> 89000;
            case 3 -> 80000;
            default -> -1;
        };
    }

    public static int capacidadNave(int nave) {
        return switch (nave) {
            case 1 -> 3;
            case 2 -> 2;
            case 3 -> 4;
            default -> -1;
        };
    }

    // Funcion que genera un evento subito: Falla del sistema
    public static void eventoSubitoFallaSistema(Scanner sc) {

        int opcion;
        double porcentajeOxigenoPerdido = 0.05; // 5% de oxígeno perdido por cada etapa.
        double oxigenoExtra = 0; // Para almacenar la cantidad de oxígeno perdido.

        System.out.println("""
                   +----------------------------------+
                   |  ⚠️  SYSTEM FAILURE DETECTED  ⚠️  |
                   |----------------------------------|
                   |        ERROR CODE: 503           |
                   |   CRITICAL SYSTEM MALFUNCTION    |
                   |                                  |
                   |  REBOOT IMMEDIATELY OR CONTACT   |
                   |        TECHNICAL SUPPORT         |
                   +----------------------------------+

                        [!!] SYSTEM SHUTTING DOWN...
                """);
        Mensajes.mensajesStop1();

        do {
            System.out.println("Evento subito: Fallo del sistema");
            Mensajes.mensajesStop1();
            System.out.println("Que acción tomar?");
            Mensajes.mensajesStop1();
            System.out.println("1. Reparar fallo");
            Mensajes.mensajesStop1();
            System.out.println("2. Seguir con la nave fallando");

            opcion = sc.nextInt();
            sc.nextLine(); // Limpia el buffer.

            if (opcion != 1 && opcion != 2) {
                System.out.println("Opción no válida");
                Mensajes.mensajesStop1();

            }
        } while (opcion != 1 && opcion != 2);

        if (opcion == 1) {
            reparacionesRealizadas = SimuladorInterplanetario.getReparacionesRealizadas();
            reparacionesRealizadas++;
            System.out.println("Fallo reparado");
            Mensajes.mensajesStop1();
            SimuladorInterplanetario.setNaveFallandoOxigeno(false);
            System.out.println("La reparacion retrazo la nave");
            Mensajes.mensajesStop1();
            double tiempoReparacion = (int) (Math.random() * 100);
            System.out.println("Tiempo de reparación: " + tiempoReparacion + " minutos");
            Mensajes.mensajesStop1();


            oxigeno = SimuladorInterplanetario.getOxigeno();
            oxigenoCosumido = SimuladorInterplanetario.getOxigenoCosumido();
            // Durante la reparación, se pierde un porcentaje de oxígeno.
            oxigenoExtra = ((oxigeno - oxigenoCosumido) * porcentajeOxigenoPerdido); // 5% de oxígeno durante la
                                                                                     // reparación.
            oxigenoCosumido += oxigenoExtra;
            SimuladorInterplanetario.setOxigenoCosumido(oxigenoCosumido);// Reducimos el oxígeno disponible.

            System.out.println("Oxígeno consumido durante la reparación: " + oxigenoExtra);
            Mensajes.mensajesStop1();

            oxigenoCosumido = SimuladorInterplanetario.getOxigenoCosumido();
            System.out.println("Oxígeno restante: " + (oxigeno - oxigenoCosumido));
            Mensajes.mensajesStop1();
            System.out.println("Oxigeno consumido: " + oxigenoCosumido);
            Mensajes.mensajesStop1();
        } else {
            System.out.println("Nave fallando");
            // La nave pierde un 5% de oxígeno por el fallo.
            oxigenoExtra = (oxigeno * porcentajeOxigenoPerdido); // 5% de oxígeno perdido por fallo.
            oxigenoCosumido += oxigenoExtra; // Reducimos el oxígeno disponible.
            SimuladorInterplanetario.setOxigenoCosumido(oxigenoCosumido);

            System.out.println("Oxígeno consumido debido al fallo: " + oxigenoExtra);
            Mensajes.mensajesStop1();

            oxigenoCosumido = SimuladorInterplanetario.getOxigenoCosumido();
            System.out.println("Oxígeno restante: " + (oxigeno - oxigenoCosumido));
            Mensajes.mensajesStop1();
            System.out.println("Oxigeno consumido: " + oxigenoCosumido);
            Mensajes.mensajesStop1();

            SimuladorInterplanetario.setNaveFallandoOxigeno(true); // Marcamos que la nave está fallando.
        }

        Nave.consumirReservaOxigeno();

        if ((oxigeno - oxigenoCosumido) <= 0) {
            System.out.println("¡Atención! El oxígeno se ha agotado. La nave está en peligro crítico.");
            Mensajes.mensajesStop1();
            // Aquí podrías finalizar la misión o ejecutar alguna lógica de emergencia.
        }
    }

    public static void eventoSubitoAsteroides(Scanner sc) {
        int opcion;
        int opcion2;
        double gasolinaExtra = 0; // Para almacenar la gasolina perdida.
        double porcentajePerdida = 0.05; // El 5% de gasolina que se pierde por cada etapa de fallo.
        System.out.println("""
                   *  o     *    .   o
                     .   *     o     .
                  .   .    O   .  *    o
                   o   *   .     *   .
                 *   .   o   *  o    *
                     o   *    .    *
                  .  *  o    .   *
                """);
        Mensajes.mensajesStop1();
        do {
            System.out.println("Evento subito: Asteroides");
            Mensajes.mensajesStop1();
            System.out.println("¿Qué acción tomar?");
            Mensajes.mensajesStop1();
            System.out.println("1. Tomar ruta nueva");
            Mensajes.mensajesStop1();
            System.out.println("2. Atravesar");
            Mensajes.mensajesStop1();

            opcion = sc.nextInt();
            sc.nextLine(); // Limpia el buffer.

            if (opcion != 1 && opcion != 2) {
                System.out.println("Opción no válida");
                Mensajes.mensajesStop1();
            }
        } while (opcion != 1 && opcion != 2);

        if (opcion == 1) {
            // Si se elige atravesar asteroides, se gasta gasolina.
            gasolinaExtra = fuel * 0.05; // El gasto de gasolina por atravesar los asteroides.
            System.out.println("El evento de asteroides ha consumido: " + gasolinaExtra + " Kilogramos de gasolina.");
            Mensajes.mensajesStop1();


            fuelCosumido = SimuladorInterplanetario.getFuelCosumido();
            fuelCosumido += gasolinaExtra; // Reducimos el combustible.
            SimuladorInterplanetario.setFuelCosumido(fuelCosumido); // Actualizamos el combustible consumido.
        } else if (opcion == 2) {
            // Si se decide atravesar los asteroides, también se puede generar un fallo.
            System.out.println("Atravesando Asteroides");
            Mensajes.mensajesStop1();
            boolean azar = Math.random() > 0.5; // Determina si el atravesar los asteroides es exitoso o no.

            if (azar) {
                System.out.println("Atravesando sin problemas");
                Mensajes.mensajesStop1();
                gasolinaExtra = fuel * 0.05; // El gasto de gasolina por atravesar los asteroides.
                System.out
                        .println("El evento de asteroides ha consumido: " + gasolinaExtra + " Kilogramos de gasolina.");
                Mensajes.mensajesStop1();

                fuelCosumido = SimuladorInterplanetario.getFuelCosumido();
                fuelCosumido += gasolinaExtra; // Reducimos el combustible.
                SimuladorInterplanetario.setFuelCosumido(fuelCosumido); // Actualizamos el combustible consumido.
            } else {
                System.out.println("Atravesando con problemas");
                do {
                    System.out.println("Se presenta un fallo en la nave");
                    Mensajes.mensajesStop1();
                    System.out.println("¿Qué acción tomar?");
                    Mensajes.mensajesStop1();
                    System.out.println("1. Reparar fallo");
                    Mensajes.mensajesStop1();
                    System.out.println("2. Seguir con la nave fallando");
                    opcion2 = sc.nextInt();
                    sc.nextLine(); // Limpia el buffer.

                    if (opcion2 != 1 && opcion2 != 2) {
                        System.out.println("Opción no válida");
                        Mensajes.mensajesStop1();
                    }
                } while (opcion2 != 1 && opcion2 != 2);

                if (opcion2 == 1) {
                    // Si se repara, se pierde menos gasolina que si la nave sigue fallando.
                    System.out.println("Fallo reparado");
                    Mensajes.mensajesStop1();

                    SimuladorInterplanetario.setNaveFallandoGasolina(false); // Actualizamos el estado de la nave.
                    System.out.println("La reparación retrasó la nave, pero no pierde más gasolina.");
                    Mensajes.mensajesStop1();
                    gasolinaExtra = fuel * 0.05; // Se pierde menos gasolina en reparación.
                    System.out.println("Gasolina consumida en la reparación: " + gasolinaExtra + " Kilogramos.");
                    Mensajes.mensajesStop1();

                    fuelCosumido = SimuladorInterplanetario.getFuelCosumido();
                    fuelCosumido += gasolinaExtra; // Reducimos el combustible.
                    SimuladorInterplanetario.setFuelCosumido(fuelCosumido); // Actualizamos el combustible consumido.
                } else {
                    // Si sigue fallando, se pierde un 5% adicional de gasolina.
                    System.out.println("Nave fallando");
                    Mensajes.mensajesStop1();

                    fuelCosumido = SimuladorInterplanetario.getFuelCosumido();
                    gasolinaExtra = ((fuel - fuelCosumido) * porcentajePerdida); // Se pierde un 5% de la gasolina
                                                                                 // actual.
                    System.out.println("Gasolina perdida por fallo: " + gasolinaExtra + " Kilogramos.");
                    Mensajes.mensajesStop1();
                    fuelCosumido += gasolinaExtra; // Reducimos el combustible.
                    SimuladorInterplanetario.setFuelCosumido(fuelCosumido); // Actualizamos el combustible consumido.
                    SimuladorInterplanetario.setNaveFallandoGasolina(true); // Marcamos que la nave está fallando.
                }
            }
        }

        // Mostrar el resultado final de la gasolina.
        fuelCosumido = SimuladorInterplanetario.getFuelCosumido();
        System.out.println("El combustible restante es: " + (fuel - fuelCosumido) + " Kilogramos.");
        Mensajes.mensajesStop1();
        System.out.println("Combustible consumido: " + fuelCosumido);
        Mensajes.mensajesStop1();

        // De ser necesario consumimos reservas
        Nave.consumirReservaCombustible();

        if (fuel - fuelCosumido <= 0) {
            System.out.println("¡Atención! El combustible se ha agotado. La nave está en peligro crítico.");
            Mensajes.mensajesStop1();
        }
    }

    public static String naveSelect(int nave) {
        return switch (nave) {
            case 1 -> "Star Voyager";
            case 2 -> "Cosmo Cruiser";
            case 3 -> "Galaxy Explorer";
            default -> "Opcion aun no creada en este universo";
        };
    }

    public static String[] secuenciaDespegue() {
        return new String[] {
                "Iniciando Despegue....",
                "Despegamos en",
                "5",
                "4",
                "3",
                "2",
                "1",
                "Despegue......"
        };
    }

    public static String planetSelect(int destino) {
        return switch (destino) {
            case 1 -> "Mercurio";
            case 2 -> "Venus";
            case 3 -> "Marte";
            case 4 -> "Júpiter";
            case 5 -> "Saturno";
            case 6 -> "Urano";
            case 7 -> "Neptuno";
            default -> "Opcion aun no creada en este universo";
        };
    }

}
