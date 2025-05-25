package simuladorInterplanetario;

import java.util.Scanner;

public class SimuladorInterplanetario {

    private static double fuelReerva = 0;
    private static double oxigenoReserva = 0;
    private static double fuelCosumido = 0;
    private static double oxigenoCosumido = 0;
    private static double fuel;
    private static double oxigeno;
    private static boolean aumentoRecursos;
    private static boolean naveFallandoGasolina = false;
    private static boolean naveFallandoOxigeno = false;
    private static int reparacionesRealizadas = 0;

    //Mensajes
    // Nave
    // Calculadora
    // Menus

    public static void startGame() {
        var sc = new Scanner(System.in);

        int destino;
        int nave;
        int eventosSubitos;

        // Inicimos el programa
        System.out.println("""
                                 .   *     o     .
                O   .  *    o .   .    O   .  *    o  *  o     *    .   o
                 Hola Bienvenido al Simulador de Vuelo interplanetario
                   o   *    .    * o   *   .     *   . *   .   o   *  o    *
                                 .  *  o    .   *
                     """);
        do {
            String[] menu1 = Menu.menuInicial();

            for (String mensaje : menu1) {
                System.out.println(mensaje);
                Mensajes.mensajesStop1();
            }

            destino = sc.nextInt();
            sc.nextLine(); // Limpia el buffer

            if (destino < 1 || destino > 7) {
                System.out.println("Destino no válido");
            }
        } while (destino < 1 || destino > 7);

        Mensajes.mensajesStop1();
        // Despues de seleccionar el destino, nos pide el nave
        do {
            String[] secuencia = Menu.menuNaves(destino);

            for (String mensaje : secuencia) {
                System.out.println(mensaje);
                Mensajes.mensajesStop1();
            }

            nave = sc.nextInt();
            sc.nextLine(); // Limpia el buffer

            if (nave < 1 || nave > 3) {
                System.out.println("Nave no válida");
            }
        } while (nave < 1 || nave > 3);

        Mensajes.mensajesStop1();

        int cantidadTripulantes;

        // Despues de seleccionar la nave, nos pide la cantidad de personas

        System.out.println("Vamos a " + Nave.planetSelect(destino) + " con una nave " + Nave.naveSelect(nave));
        Mensajes.mensajesStop1();
        do {
            System.out.println("Este viaje se realizará con cuántas personas?");
            cantidadTripulantes = sc.nextInt();
            if (cantidadTripulantes < 1 || cantidadTripulantes > Nave.capacidadNave(nave)) {
                System.out.println("Capacidad no válida. Intente nuevamente.");
            }
        } while (cantidadTripulantes < 1 || cantidadTripulantes > Nave.capacidadNave(nave));

        Mensajes.mensajesStop1();

        // Despues de seleccionar la cantidad de personas, calculamos los eventos
        // aleatorios

        eventosSubitos = CalculadoraNave.calcularEventosAleatorios(CalculadoraNave.distanceKM(destino));
        int eventosCompletados = 0;

        // Mostramos el tiempo y distancia

        System.out.println(CalculadoraNave.calculateDistanceAndTime(destino, nave));
        Mensajes.mensajesStop1();
        // Asignamos los recursos iniciales

        fuel = CalculadoraNave.calculateFuel(destino, nave);
        oxigeno = CalculadoraNave.calculateOxigen(destino, nave, cantidadTripulantes);

        int opcion;
        aumentoRecursos = false;

        // Ingresamos al segundo menu

        do {
            String[] menu2 = Menu.menuSecond();

            for (String mensaje : menu2) {
                System.out.println(mensaje);
                Mensajes.mensajesStop1();
            }

            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                sc.next(); // Limpiar entrada no válida
            }
            opcion = sc.nextInt();
            sc.nextLine(); // Limpia el buffer.

            switch (opcion) {
                case 1:
                    if (!aumentoRecursos) {
                        System.out
                                .println("La cantidad de combustible necesario es: " + fuel + " Kilogramos");

                        Mensajes.mensajesStop1();

                        System.out.println("La cantidad de oxigeno necesario para " + cantidadTripulantes
                                + " personas es: " + oxigeno + " Kilogramos");

                        Mensajes.mensajesStop1();

                        System.out.println("Es recomendable aumentar los recursos de la nave para evitar imprevistos");

                        Mensajes.mensajesStop1();

                    } else {
                        System.out.println("La cantidad de combustible es: " + fuel
                                + " Kilogramos y la reserva de combustible es: " + fuelReerva + " Kilogramos");
                        Mensajes.mensajesStop1();

                        System.out.println("La cantidad de oxigeno necesario para " + cantidadTripulantes
                                + " personas es: " + oxigeno + " Kilogramos y la reserva de oxigeno es: "
                                + oxigenoReserva + " Kilogramos");
                        Mensajes.mensajesStop1();

                    }
                    break;
                case 2:
                    CalculadoraNave.calculateAumentoRecursos(sc, cantidadTripulantes);
                    aumentoRecursos = true;
                    break;
                case 3:
                    System.out.println("La nave se encuentra en optimas condiciones");
                    break;
                case 4:
                    String[] secuencia = Nave.secuenciaDespegue();

                    for (String mensaje : secuencia) {
                        System.out.println(mensaje);
                        Mensajes.mensajesStop1();
                    }
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 4);

        // Cuando inciamos el despegue de la nave, empezamos a contar las etapas

        for (int etapa = 1; etapa <= 10; etapa++) {

            boolean etapaValida = consultarEtapa(etapa, eventosCompletados, eventosSubitos);

            if (fuelCosumido > fuel) {

                Mensajes.mensajeMuerto();
                break;

            }

            if (oxigenoCosumido > oxigeno) {

                Mensajes.mensajeMuerto();
                break;

            }

            // Verifica si los recursos son suficientes para continuar con la etapa
            if (!Nave.validarRecursos((fuel - fuelCosumido), (oxigeno - oxigenoCosumido))) {
                break; // Si los recursos no son suficientes, termina el viaje
            }

            if (!etapaValida) {

                // Validamos al azar si se presentara un evento subito
                int randomValue = (Math.random() < 0.5) ? 1 : 2;

                // Validamos al azar si el evento es falla del sistema o asteroides
                if (randomValue == 1) {
                    Nave.eventoSubitoFallaSistema(sc);
                } else {
                    Nave.eventoSubitoAsteroides(sc);
                }
            }

            // Mostramos el mensaje de salida de la nave
            if (etapa == 1) {

                System.out.println("Salimos de la Tierra");
                System.out.println("Precione enter para continuar");
                sc.nextLine();
            }

            System.out.println(Nave.naveVolando(etapa));

            // Validamos si es necesario las reservas de combustible y oxígeno
            Nave.consumirReservaCombustible();
            Nave.consumirReservaOxigeno();

            Mensajes.mensajesStop1();

            System.out.println("El proceso del viaje es " + etapa + "0%");

            Mensajes.mensajesStop1();

            // Validamos si la nave tiene una perdida de combustible
            if (naveFallandoGasolina) {
                // Si la nave está fallando, se pierde un 5% de combustible.
                fuelCosumido += (fuel / 10) + (fuel * 0.05);
                System.out.println("La nave está fallando y esta perdiendo un 5% de combustible.");
                Mensajes.mensajesStop1();
            } else {
                fuelCosumido += (fuel / 10);
            }

            // Validamos si la nave tiene una perdida de oxígeno
            if (naveFallandoOxigeno) {
                // Si la nave está fallando, se pierde un 5% de oxígeno.
                oxigenoCosumido += (oxigeno / 10) + (oxigeno * 0.05);
                System.out.println("La nave está fallando y esta perdiendo un 5% de oxigeno.");
                Mensajes.mensajesStop1();
            } else {
                oxigenoCosumido += (oxigeno / 10);
            }

            // Calculamos el porcentaje de combustible y oxígeno que quedan
            double porcentajeCombustible = ((fuel - fuelCosumido) / fuel) * 100;
            double porcentajeOxigeno = ((oxigeno - oxigenoCosumido) / oxigeno) * 100;

            if (fuelCosumido > fuel) {
                Mensajes.mensajeMuerto();
                break;
            } else {
                System.out.println("El combustible consumido es " + fuelCosumido + " kilogramos. Aun nos quedan "
                        + porcentajeCombustible + "% de combustible.");
            }
            Mensajes.mensajesStop1();
            if (oxigenoCosumido > oxigeno) {
                Mensajes.mensajeMuerto();
                break;
            } else {
                System.out.println("El oxigeno consumido es " + oxigenoCosumido + " kilogramos. Aun nos quedan "
                        + porcentajeOxigeno + "% de oxigeno.");
            }
            Mensajes.mensajesStop1();
            // System.out.println("Precione enter para continuar");

        }

        // Validamos si comcluyo el proceso con exito
        if (!Nave.validarRecursos((fuel - fuelCosumido), (oxigeno - oxigenoCosumido))) {
            System.out.println("Mision abortada, no hay suficiente combustible o oxígeno para continuar");
        } else {
            System.out.println("Proceso completado, llegamos a " + Nave.planetSelect(destino));
            Mensajes.mensajeLlegaste();

            // Logros
            System.out.println("Logros desbloqueados:");
            evaluarLogros(destino, true, eventosSubitos, reparacionesRealizadas);

        }
        sc.close();
    }

    private static void evaluarLogros(int destino, boolean viajeExitoso, int eventosSubitos, int reparacionesRealizadas) {
    // 🌟 Viajero Estelar
    if (fuelReerva == 0 && oxigenoReserva == 0 && viajeExitoso) {
        System.out.println("🌟 Logro desbloqueado: Viajero Estelar");
    }

    // 🔥 Contra todo pronóstico
    double porcentajeCombustible = (fuel - fuelCosumido) / fuel;
    double porcentajeOxigeno = (oxigeno - oxigenoCosumido) / oxigeno;

    if (viajeExitoso && (porcentajeCombustible < 0.05 || porcentajeOxigeno < 0.05)) {
        System.out.println("🔥 Logro desbloqueado: Contra todo pronóstico");
    }

    // 🔧 Mecánico Espacial
    if (reparacionesRealizadas >= 2) {
        System.out.println("🔧 Logro desbloqueado: Mecánico Espacial");
    }

    // 🚀 Sin un rasguño
    if (eventosSubitos == 0 && !naveFallandoGasolina && !naveFallandoOxigeno) {
        System.out.println("🚀 Logro desbloqueado: Sin un rasguño");
    }

    // 🌌 Explorador Supremo
    if (destino == 7 && viajeExitoso) {
        System.out.println("🌌 Logro desbloqueado: Explorador Supremo");
    }

    // 📉 Eficiencia Total
    if (porcentajeCombustible > 0.5 && porcentajeOxigeno > 0.5) {
        System.out.println("📉 Logro desbloqueado: Eficiencia Total");
    }
    }


    // Función que consulta si una etapa es válida o no
    private static boolean consultarEtapa(int etapa, int eventosCompletados, int eventosSubitos) {

        if (eventosCompletados < eventosSubitos) {
            return Math.random() > 0.5; // Simula si una etapa es válida (50% de probabilidades de fallar)
        } else {
            return true;
        }
    }

    // Setters para modificar los recursos y estados de la nave
    public static void setFuelReerva(double fuelReerva) {
        SimuladorInterplanetario.fuelReerva = fuelReerva;
    }

    public static void setOxigenoReserva(double oxigenoReserva) {
        SimuladorInterplanetario.oxigenoReserva = oxigenoReserva;
    }

    public static void setFuel(double fuel) {
        SimuladorInterplanetario.fuel = fuel;
    }

    public static void setOxigeno(double oxigeno) {
        SimuladorInterplanetario.oxigeno = oxigeno;
    }

    public static void setAumentoRecursos(boolean aumentoRecursos) {
        SimuladorInterplanetario.aumentoRecursos = aumentoRecursos;
    }

    public static void setFuelCosumido(double fuelCosumido) {
        SimuladorInterplanetario.fuelCosumido = fuelCosumido;
    }

    public static void setOxigenoCosumido(double oxigenoCosumido) {
        SimuladorInterplanetario.oxigenoCosumido = oxigenoCosumido;
    }

    public static void setNaveFallandoGasolina(boolean naveFallandoGasolina) {
        SimuladorInterplanetario.naveFallandoGasolina = naveFallandoGasolina;
    }

    public static void setNaveFallandoOxigeno(boolean naveFallandoOxigeno) {
        SimuladorInterplanetario.naveFallandoOxigeno = naveFallandoOxigeno;
    }

    public static void setReparacionesRealizadas(int reparacionesRealizadas) {
        SimuladorInterplanetario.reparacionesRealizadas = reparacionesRealizadas;
    }


    // Getters para acceder a los recursos y estados de la nave
    public static double getFuelReerva() {
        return fuelReerva;
    }

    public static double getOxigenoReserva() {
        return oxigenoReserva;
    }

    public static double getFuel() {
        return fuel;
    }

    public static double getOxigeno() {
        return oxigeno;
    }

    public static boolean isAumentoRecursos() {
        return aumentoRecursos;
    }

    public static double getFuelCosumido() {
        return fuelCosumido;
    }

    public static double getOxigenoCosumido() {
        return oxigenoCosumido;
    }
    public static boolean isNaveFallandoGasolina() {
        return naveFallandoGasolina;
    }
    public static boolean isNaveFallandoOxigeno() {
        return naveFallandoOxigeno;
    }
    public static int getReparacionesRealizadas() {
        return reparacionesRealizadas;
    }

    public static void reset() {
        fuelReerva = 0;
        oxigenoReserva = 0;
        fuelCosumido = 0;
        oxigenoCosumido = 0;
        fuel = 0;
        oxigeno = 0;
        aumentoRecursos = false;
        naveFallandoGasolina = false;
        naveFallandoOxigeno = false;
        reparacionesRealizadas = 0;
    }
    
    public static void calcularTiempoSenal() {
    double velocidadLuz = 299792.458; // km/s

    String[] planetas = {
        "Mercurio", "Venus", "Marte", "Júpiter", "Saturno", "Urano", "Neptuno", "Plutón"
    };
    
    double[] distancias = {
        91.7, 41.4, 78.3, 628.7, 1275.0, 2721.0, 4351.0, 5869.7  // en millones de km
    };

    System.out.println("Tiempo que tarda una señal en llegar a cada planeta:");
    for (int i = 0; i < planetas.length; i++) {
        double tiempoSegundos = (distancias[i] * 1_000_000) / velocidadLuz;
        double tiempoMinutos = tiempoSegundos / 60;
        System.out.printf("- %s: %.2f minutos\n", planetas[i], tiempoMinutos);
    }
}

}
