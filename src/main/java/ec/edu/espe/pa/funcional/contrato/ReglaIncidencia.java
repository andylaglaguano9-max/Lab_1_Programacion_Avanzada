package ec.edu.espe.pa.funcional.contrato;

import ec.edu.espe.pa.funcional.modelo.Incidencia;

import java.util.Objects;

/** Contrato de dominio para una regla que evalúa una incidencia. */
@FunctionalInterface
public interface ReglaIncidencia {

    boolean evaluar(Incidencia incidencia);

    /** Combina esta regla y otra usando evaluación corta. */
    default ReglaIncidencia y(ReglaIncidencia otra) {
        Objects.requireNonNull(otra, "La regla a combinar es obligatoria");
        return incidencia -> this.evaluar(incidencia) && otra.evaluar(incidencia);
    }
}
