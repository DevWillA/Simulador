package simuladorInterplanetario;

import java.util.Scanner;

public class CalculadoraNave {


    private static double fuel;
    private static double oxigeno;

    // Funcion que calcula el aumento de recursos y lo asigna a las reservas
    public static void calculateAumentoRecursos(Scanner sc, int cantidadTripulantes) {

        fuel = SimuladorInterplanetario.getFuel();
        oxigeno = SimuladorInterplanetario.getOxigeno();

        if (SimuladorInterplanetario.isAumentoRecursos()) {

            System.out.println(
                    "Recuerde que ya lleva reservas de combustible y oxígeno, por lo tanto se tendran en cuenta los recursos base para las nuevas reservas");
            Mensajes.mensajesStop1();
        }
        System.out.println(
                "En cantidad de %, ¿cuánto quiere aumentar el combustible? (Máximo puede aumentar en un 30%)");
        double aumentoC = sc.nextDouble();
        Mensajes.mensajesStop1();
        System.out
                .println("En cantidad de %, ¿cuánto quiere aumentar el oxígeno? (Máximo puede aumentar en un 30%)");
        double aumentoO = sc.nextDouble();
        Mensajes.mensajesStop1();

        if (aumentoC <= 30 && aumentoO <= 30) {
            SimuladorInterplanetario.setFuelReerva((fuel * aumentoC / 100));
            SimuladorInterplanetario.setOxigenoReserva((oxigeno * aumentoO / 100));

            System.out.println("La cantidad de combustible es: " + fuel + " Kilogramos"
                    + " y la reserva de combustible es: " + SimuladorInterplanetario.getFuelReerva() + " Kilogramos");
            Mensajes.mensajesStop1();
            System.out.println("La cantidad de oxígeno para " + cantidadTripulantes + " personas es: " + oxigeno
                    + " Kilogramos" + " y la reserva de oxígeno es: " + SimuladorInterplanetario.getOxigenoReserva() + " Kilogramos");
            Mensajes.mensajesStop1();

        } else {
            System.out.println("El aumento excede el total máximo");
            Mensajes.mensajesStop1();
        }
    }

     // Funcion que regersa el tiempo de vuelo en Dias
    public static double timeDD(int destino, int nave) {
        double distancia = distanceKM(destino) * 1e6; // Distancia en kilómetros
        double velocidad = Nave.velocidadNave(nave); // Velocidad promedio en km/h
        double tiempoHH = distancia / velocidad;
        return tiempoHH / 24; // Convertir a días
    }

     // Funcion que calcula la distancia y el tiempo de viaje al destino
    public static String calculateDistanceAndTime(int destino, int nave) {
        if (distanceKM(destino) == -1) {
            return "Opcion aun no creada en este universo, Destino no válido.";
        }

        int tiempoMM = (int) Math.ceil(timeDD(destino, nave) / 30.0); // Redondeo hacia arriba
        return String.format(
                "La distancia a %s es %.1f millones de kilómetros desde la Tierra y el tiempo estimado es de %d meses.",
                Nave.planetSelect(destino), distanceKM(destino), tiempoMM);
    }

     // Funcion que calcula la gasolina nesesaria para el viaje segun el destino
    public static double calculateFuel(int destino, int nave) {
        if (distanceKM(destino) == -1) {
            return 0.0;
        }

        double distanciaMillonesKM = distanceKM(destino);
        double consumoBasePorMillonKM = 500;
        double factorVelocidad = Nave.velocidadNave(nave) / 100000.0; // Ajuste basado en velocidad (normalizado con 100,000
                                                                 // km/h)
        double consumoTotal = consumoBasePorMillonKM * distanciaMillonesKM * factorVelocidad;

        consumoTotal += consumoTotal + 0.1; // Agregamos un 10% para llegar ccon mas base

        return consumoTotal;
    }

    public static double calculateOxigen(int destino, int nave, int cantidadTripulantes) {
        // Consumo promedio de oxigeno de una persona 0.84kg
        double consumoTotal = cantidadTripulantes * timeDD(destino, nave) * 24 * 0.84; // Oxígeno en kilogramos
                                                                                       // considerando horas

        consumoTotal += consumoTotal * 0.1; // Agregamos un 10% para llegar ccon mas base
        return consumoTotal;
    }

    // Función que calcula el número de eventos aleatorios basados en la distancia
    public static int calcularEventosAleatorios(double distancia) {
        // A mayor distancia, mayor probabilidad de eventos
        double probabilidadEvento = Math.min(1, distancia / 5000.0);
        int eventos = 1; // Comenzamos con al menos un evento

        // Generar eventos aleatorios basados en la probabilidad
        for (int i = 0; i < 7; i++) {
            if (Math.random() < probabilidadEvento) {
                eventos++;
            }
        }

        // Limitar el número total de eventos a un máximo de 7
        return Math.min(eventos, 7);
    }

    public static double distanceKM(int destino) {
        return switch (destino) {
            case 1 -> 91.7;
            case 2 -> 41.4;
            case 3 -> 225;
            case 4 -> 778;
            case 5 -> 1429;
            case 6 -> 2871;
            case 7 -> 4497;
            default -> -1;
        };
    }
}
