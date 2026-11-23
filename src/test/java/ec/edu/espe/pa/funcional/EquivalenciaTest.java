package ec.edu.espe.pa.funcional;

import ec.edu.espe.pa.funcional.funcional.ProcesadorFuncional;
import ec.edu.espe.pa.funcional.imperativo.ProcesadorImperativo;
import ec.edu.espe.pa.funcional.modelo.Incidencia;
import ec.edu.espe.pa.funcional.reglas.ReglasIncidencia;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquivalenciaTest {

    private final ProcesadorImperativo imperativo = new ProcesadorImperativo();
    private final ProcesadorFuncional funcional = new ProcesadorFuncional();

    static Stream<List<Incidencia>> escenarios() {
        return Stream.of(
                List.of(),
                List.of(new Incidencia(
                        "INC-001", "Caída del servidor", "ABIERTA", 5)),
                List.of(
                        new Incidencia("INC-004", "Lentitud en reportes", "ABIERTA", 3),
                        new Incidencia("INC-005", "Error de acceso", "ABIERTA", 4),
                        new Incidencia("INC-003", "Fallo de respaldo", "CERRADA", 5)),
                Stream.generate(ReglasIncidencia.generadorSintetico())
                        .limit(50)
                        .toList());
    }

    @ParameterizedTest
    @MethodSource("escenarios")
    void ambasSolucionesProducenElMismoResultado(List<Incidencia> datos) {
        List<Incidencia> copia = List.copyOf(datos);

        List<String> esperado = imperativo.titulosQueRequierenAtencion(datos);
        List<String> obtenido = funcional.titulosQueRequierenAtencion(datos);

        assertEquals(esperado, obtenido);
        assertEquals(copia, datos);
    }
}
