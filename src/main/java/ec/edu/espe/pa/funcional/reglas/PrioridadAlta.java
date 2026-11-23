package ec.edu.espe.pa.funcional.reglas;

import ec.edu.espe.pa.funcional.contrato.ReglaIncidencia;
import ec.edu.espe.pa.funcional.modelo.Incidencia;

/** Implementación con nombre del contrato de regla de prioridad alta. */
public class PrioridadAlta implements ReglaIncidencia {

    @Override
    public boolean evaluar(Incidencia incidencia) {
        return incidencia.prioridad() >= ReglasIncidencia.UMBRAL_PRIORIDAD_ALTA;
    }
}
