package simuladorInterplanetario;

public class Mensajes {

    // Retornos de mensajes asccii
     public static void mensajeLlegaste() {
        System.out.println("""
                  L       L        EEEEE  GGGG   AAAAA  SSSSS  TTTTT  EEEEE
                  L       L        E     G       A     A S        T    E
                  L       L        EEEE  G  GG   AAAAAAA SSSSS    T    EEEE
                  L       L        E     G   G   A     A     S    T    E
                  LLLLL   LLLLL    EEEEE GGGG    A     A SSSSS    T    EEEEE
                """);
    }

    public static void mensajeMuerto() {
        System.out.println("""
                  M   M  U   U  EEEEE  RRRR    TTTTT   OOO
                  MM MM  U   U  E      R   R    T    O   O
                  M M M  U   U  EEEE   RRRR     T    O   O
                  M   M  U   U  E      R  R     T    O   O
                  M   M  UUUUU  EEEEE  R   R    T     OOO
                """);
    }

    // Funcion que retrasa los mensajes de salida 1 segundo
    public static void mensajesStop1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
