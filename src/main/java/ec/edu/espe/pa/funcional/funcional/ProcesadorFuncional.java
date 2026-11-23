package ec.edu.espe.pa.funcional.funcional;

import ec.edu.espe.pa.funcional.modelo.Incidencia;
import ec.edu.espe.pa.funcional.reglas.ReglasIncidencia;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/** Procesador que recibe las responsabilidades como funciones intercambiables. */
public class ProcesadorFuncional {

    /** Selecciona elementos y transforma cada elemento seleccionado. */
    public <R> List<R> seleccionar(
            List<Incidencia> incidencias,
            Predicate<Incidencia> regla,
            Function<Incidencia, R> transformacion) {
        Objects.requireNonNull(incidencias, "Las incidencias son obligatorias");
        Objects.requireNonNull(regla, "La regla es obligatoria");
        Objects.requireNonNull(transformacion, "La transformación es obligatoria");

        return incidencias.stream()
                .filter(regla)
                .map(transformacion)
                .toList();
    }

    public List<String> titulosQueRequierenAtencion(List<Incidencia> incidencias) {
        return seleccionar(
                incidencias,
                ReglasIncidencia.REQUIERE_ATENCION,
                Incidencia::titulo);
    }

    /** Ejecuta una acción únicamente sobre los elementos que cumplen la regla. */
    public void ejecutar(
            List<Incidencia> incidencias,
            Predicate<Incidencia> regla,
            Consumer<Incidencia> accion) {
        Objects.requireNonNull(incidencias, "Las incidencias son obligatorias");
        Objects.requireNonNull(regla, "La regla es obligatoria");
        Objects.requireNonNull(accion, "La acción es obligatoria");

        incidencias.stream()
                .filter(regla)
                .forEach(accion);
    }
}
