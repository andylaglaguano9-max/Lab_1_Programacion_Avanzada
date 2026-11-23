package ec.edu.espe.pa.funcional.reglas;

import ec.edu.espe.pa.funcional.modelo.Incidencia;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/** Funciones nombradas del dominio de incidencias. */
public final class ReglasIncidencia {

    private ReglasIncidencia() {
    }

    public static final int UMBRAL_PRIORIDAD_ALTA = 4;

    // Predicate: recibe una Incidencia y devuelve una decisión booleana.
    public static final Predicate<Incidencia> ESTA_ABIERTA =
            incidencia -> "ABIERTA".equals(incidencia.estado());

    // Predicate: recibe una Incidencia y decide si alcanza el umbral.
    public static final Predicate<Incidencia> PRIORIDAD_ALTA =
            incidencia -> incidencia.prioridad() >= UMBRAL_PRIORIDAD_ALTA;

    // Predicate compuesto: una incidencia requiere ambas condiciones.
    public static final Predicate<Incidencia> REQUIERE_ATENCION =
            ESTA_ABIERTA.and(PRIORIDAD_ALTA);

    // Function: recibe una Incidencia y transforma sus datos en un resumen.
    public static final Function<Incidencia, String> RESUMEN =
            incidencia -> incidencia.id() + " - " + incidencia.titulo();

    // Function: recibe una Incidencia y transforma su prioridad en categoría.
    public static final Function<Incidencia, String> CATEGORIA = incidencia -> {
        return switch (incidencia.prioridad()) {
            case 5 -> "CRITICA";
            case 4 -> "ALTA";
            case 2, 3 -> "MEDIA";
            case 1 -> "BAJA";
            default -> throw new IllegalArgumentException(
                    "Prioridad fuera de rango: " + incidencia.prioridad());
        };
    };

    // Consumer: recibe una Incidencia, produce un efecto y no devuelve valor.
    public static final Consumer<Incidencia> NOTIFICAR =
            incidencia -> System.out.println("[ATENCION] " + RESUMEN.apply(incidencia));

    /** Supplier: no recibe entrada y genera una incidencia sintética nueva. */
    public static Supplier<Incidencia> generadorSintetico() {
        AtomicInteger contador = new AtomicInteger(1);
        String[] estados = {"ABIERTA", "EN_PROCESO", "CERRADA"};

        return () -> {
            int n = contador.getAndIncrement();
            return new Incidencia(
                    "INC-%03d".formatted(n),
                    "Incidencia sintética " + n,
                    estados[n % estados.length],
                    (n % 5) + 1);
        };
    }

    /** Regla usada para ilustrar una referencia a método estático. */
    public static boolean esCritica(Incidencia incidencia) {
        return incidencia.prioridad() == 5;
    }
}
