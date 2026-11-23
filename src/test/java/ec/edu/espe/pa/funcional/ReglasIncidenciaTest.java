package ec.edu.espe.pa.funcional;

import ec.edu.espe.pa.funcional.contrato.ReglaIncidencia;
import ec.edu.espe.pa.funcional.funcional.ProcesadorFuncional;
import ec.edu.espe.pa.funcional.modelo.Incidencia;
import ec.edu.espe.pa.funcional.reglas.PrioridadAlta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static ec.edu.espe.pa.funcional.reglas.ReglasIncidencia.CATEGORIA;
import static ec.edu.espe.pa.funcional.reglas.ReglasIncidencia.ESTA_ABIERTA;
import static ec.edu.espe.pa.funcional.reglas.ReglasIncidencia.PRIORIDAD_ALTA;
import static ec.edu.espe.pa.funcional.reglas.ReglasIncidencia.REQUIERE_ATENCION;
import static ec.edu.espe.pa.funcional.reglas.ReglasIncidencia.RESUMEN;
import static ec.edu.espe.pa.funcional.reglas.ReglasIncidencia.generadorSintetico;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReglasIncidenciaTest {

    private final Incidencia abiertaAlta =
            new Incidencia("INC-001", "Caída del servidor", "ABIERTA", 5);
    private final Incidencia abiertaBaja =
            new Incidencia("INC-002", "Error tipográfico", "ABIERTA", 2);
    private final Incidencia cerradaAlta =
            new Incidencia("INC-003", "Fallo de respaldo", "CERRADA", 5);

    @Test
    void contratoPropioAdmiteClaseClaseAnonimaYLambda() {
        ReglaIncidencia conNombre = new PrioridadAlta();
        ReglaIncidencia anonima = new ReglaIncidencia() {
            @Override
            public boolean evaluar(Incidencia incidencia) {
                return ESTA_ABIERTA.test(incidencia);
            }
        };
        ReglaIncidencia lambda = incidencia -> ESTA_ABIERTA.test(incidencia);

        assertTrue(conNombre.evaluar(abiertaAlta));
        assertTrue(anonima.evaluar(abiertaAlta));
        assertTrue(lambda.evaluar(abiertaAlta));
        assertFalse(anonima.evaluar(cerradaAlta));
    }

    @Test
    void predicadoEstaAbiertaDistingueEstados() {
        assertTrue(ESTA_ABIERTA.test(abiertaAlta));
        assertFalse(ESTA_ABIERTA.test(cerradaAlta));
    }

    @Test
    void requiereAtencionSoloSiEstaAbiertaYEsDePrioridadAlta() {
        assertTrue(REQUIERE_ATENCION.test(abiertaAlta));
        assertFalse(REQUIERE_ATENCION.test(abiertaBaja));
        assertFalse(REQUIERE_ATENCION.test(cerradaAlta));
    }

    @ParameterizedTest
    @CsvSource({"3, false", "4, true", "5, true"})
    void prioridadAltaRespetaElUmbral(int prioridad, boolean esperado) {
        Incidencia incidencia = new Incidencia("INC-L", "Límite", "ABIERTA", prioridad);
        assertEquals(esperado, PRIORIDAD_ALTA.test(incidencia));
    }

    @Test
    void composicionDePredicadosIncluyeNegateYOr() {
        Predicate<Incidencia> noAbierta = ESTA_ABIERTA.negate();
        Predicate<Incidencia> abiertaDePrioridadBaja =
                ESTA_ABIERTA.and(PRIORIDAD_ALTA.negate());
        Predicate<Incidencia> criticaOAbierta =
                PRIORIDAD_ALTA.and(i -> i.prioridad() == 5).or(ESTA_ABIERTA);

        assertTrue(noAbierta.test(cerradaAlta));
        assertTrue(abiertaDePrioridadBaja.test(abiertaBaja));
        assertTrue(criticaOAbierta.test(cerradaAlta));
    }

    @ParameterizedTest
    @CsvSource({
            "1, BAJA",
            "3, MEDIA",
            "4, ALTA",
            "5, CRITICA"
    })
    void categoriaRespetaTodosLosCasosSolicitados(int prioridad, String esperada) {
        Incidencia incidencia = new Incidencia("INC-C", "Categoría", "ABIERTA", prioridad);
        assertEquals(esperada, CATEGORIA.apply(incidencia));
    }

    @Test
    void funcionesYComposicionAndThenYComposeProducenElMismoResultado() {
        assertEquals("INC-001 - Caída del servidor", RESUMEN.apply(abiertaAlta));
        java.util.function.Function<String, String> aMayusculas = String::toUpperCase;
        java.util.function.Function<Incidencia, String> conAndThen =
                RESUMEN.andThen(aMayusculas);
        java.util.function.Function<Incidencia, String> conCompose =
                aMayusculas.compose(RESUMEN);

        assertEquals(
                "INC-001 - CAÍDA DEL SERVIDOR",
                conAndThen.apply(abiertaAlta));
        assertEquals(conAndThen.apply(abiertaAlta), conCompose.apply(abiertaAlta));
    }

    @Test
    void consumerRegistraSoloLasSeleccionadas() {
        List<String> bitacora = new ArrayList<>();
        Consumer<Incidencia> registrar = incidencia -> bitacora.add(incidencia.id());

        new ProcesadorFuncional().ejecutar(
                List.of(abiertaAlta, abiertaBaja, cerradaAlta),
                REQUIERE_ATENCION,
                registrar);

        assertEquals(List.of("INC-001"), bitacora);
    }

    @Test
    void supplierGeneraIdsDistintosYPrioridadesValidas() {
        Supplier<Incidencia> generador = generadorSintetico();
        Incidencia primera = generador.get();
        Incidencia segunda = generador.get();

        assertNotEquals(primera.id(), segunda.id());
        assertTrue(primera.prioridad() >= 1 && primera.prioridad() <= 5);
        assertTrue(segunda.prioridad() >= 1 && segunda.prioridad() <= 5);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    void rechazaPrioridadesFueraDeRango(int prioridad) {
        assertThrows(IllegalArgumentException.class,
                () -> new Incidencia("INC-X", "Inválida", "ABIERTA", prioridad));
    }

}
