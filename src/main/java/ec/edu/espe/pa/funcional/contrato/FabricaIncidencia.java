package ec.edu.espe.pa.funcional.contrato;

import ec.edu.espe.pa.funcional.modelo.Incidencia;

/** Contrato propio porque java.util.function no tiene cuatro entradas. */
@FunctionalInterface
public interface FabricaIncidencia {

    Incidencia crear(String id, String titulo, String estado, int prioridad);
}
