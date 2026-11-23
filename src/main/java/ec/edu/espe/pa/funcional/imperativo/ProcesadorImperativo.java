package ec.edu.espe.pa.funcional.imperativo;

import ec.edu.espe.pa.funcional.modelo.Incidencia;

import java.util.ArrayList;
import java.util.List;

/** Solución de referencia: selección y transformación dentro de un ciclo. */
public class ProcesadorImperativo {

    public List<String> titulosQueRequierenAtencion(List<Incidencia> incidencias) {
        List<String> resultado = new ArrayList<>();
        for (Incidencia incidencia : incidencias) {
            if ("ABIERTA".equals(incidencia.estado())
                    && incidencia.prioridad() >= 4) {
                resultado.add(incidencia.titulo());
            }
        }
        return resultado;
    }

    public void mostrarQueRequierenAtencion(List<Incidencia> incidencias) {
        for (String titulo : titulosQueRequierenAtencion(incidencias)) {
            System.out.println(titulo);
        }
    }
}
