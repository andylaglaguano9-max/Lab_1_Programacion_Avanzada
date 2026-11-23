package ec.edu.espe.pa.funcional;

import ec.edu.espe.pa.funcional.contrato.FabricaIncidencia;
import ec.edu.espe.pa.funcional.contrato.ReglaIncidencia;
import ec.edu.espe.pa.funcional.modelo.Incidencia;
import ec.edu.espe.pa.funcional.reglas.PrioridadAlta;
import ec.edu.espe.pa.funcional.reglas.ReglasIncidencia;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static ec.edu.espe.pa.funcional.reglas.ReglasIncidencia.*;

/** Ejemplos ejecutables de las fases demostrativas del laboratorio. */
public class Demostracion {

    public static void main(String[] args) {
        // Fase 2: las lambdas se ejecutan al invocar test, apply, accept o get.
        Predicate<Incidencia> estaAbierta =
                incidencia -> "ABIERTA".equals(incidencia.estado());
        Function<Incidencia, String> obtenerResumen =
                incidencia -> incidencia.id() + " - " + incidencia.titulo();
        Consumer<Incidencia> mostrar = incidencia -> System.out.println(incidencia.titulo());
        Supplier<Incidencia> crearEjemplo =
                () -> new Incidencia("INC-001", "Error de acceso", "ABIERTA", 3);

        Incidencia ejemplo = crearEjemplo.get();
        boolean abierta = estaAbierta.test(ejemplo);
        String resumen = obtenerResumen.apply(ejemplo);
        mostrar.accept(ejemplo);
        System.out.println("Abierta: " + abierta + " | Resumen: " + resumen);

        // Fase 3: clase con nombre, clase anónima y lambda para el mismo contrato.
        ReglaIncidencia prioridadConClase = new PrioridadAlta();
        ReglaIncidencia abiertaAnonima = new ReglaIncidencia() {
            @Override
            public boolean evaluar(Incidencia incidencia) {
                return "ABIERTA".equals(incidencia.estado());
            }
        };
        ReglaIncidencia abiertaLambda = incidencia -> "ABIERTA".equals(incidencia.estado());
        ReglaIncidencia requiereAtencion = abiertaLambda.y(new PrioridadAlta());
        Predicate<Incidencia> comoPredicado = requiereAtencion::evaluar;

        System.out.println("Reglas: "
                + prioridadConClase.evaluar(ejemplo) + ", "
                + abiertaAnonima.evaluar(ejemplo) + ", "
                + abiertaLambda.evaluar(ejemplo) + ", "
                + comoPredicado.test(ejemplo));

        List<Incidencia> incidencias = List.of(
                new Incidencia("INC-001", "Caída del servidor", "ABIERTA", 5),
                new Incidencia("INC-002", "Error tipográfico", "ABIERTA", 2),
                new Incidencia("INC-003", "Fallo de respaldo", "CERRADA", 5));

        // Fase 5: composición de Predicate, Function y Consumer.
        Predicate<Incidencia> noAbierta = ESTA_ABIERTA.negate();
        Predicate<Incidencia> pendienteBaja = ESTA_ABIERTA.and(PRIORIDAD_ALTA.negate());
        Predicate<Incidencia> revisarHoy = REQUIERE_ATENCION.or(i -> i.prioridad() == 5);

        Function<String, String> aMayusculas = String::toUpperCase;
        Function<Incidencia, String> resumenMayusculas = RESUMEN.andThen(aMayusculas);
        Function<Incidencia, String> mismoResultado = aMayusculas.compose(RESUMEN);
        Function<Incidencia, String> etiqueta = CATEGORIA.andThen(c -> "[" + c + "]");

        List<String> bitacora = new ArrayList<>();
        Consumer<Incidencia> registrar = incidencia -> bitacora.add(incidencia.id());
        Consumer<Incidencia> notificarYRegistrar = NOTIFICAR.andThen(registrar);

        System.out.println("No abiertas: " + incidencias.stream().filter(noAbierta).count());
        System.out.println("Abiertas de prioridad baja: "
                + incidencias.stream().filter(pendienteBaja).count());
        System.out.println("Para revisar hoy: "
                + incidencias.stream().filter(revisarHoy).count());
        System.out.println(resumenMayusculas.apply(incidencias.get(0)));
        System.out.println(mismoResultado.apply(incidencias.get(0)));
        System.out.println(etiqueta.apply(incidencias.get(0)));
        notificarYRegistrar.accept(incidencias.get(0));

        // Referencias a métodos: estático, objeto particular, instancia y constructor.
        Predicate<Incidencia> esCritica = ReglasIncidencia::esCritica;
        incidencias.forEach(System.out::println);
        Function<Incidencia, String> titulo = Incidencia::titulo;
        Supplier<List<Incidencia>> nuevaLista = ArrayList::new;
        FabricaIncidencia fabrica = Incidencia::new;

        System.out.println("Crítica: " + esCritica.test(incidencias.get(0)));
        System.out.println("Título: " + titulo.apply(incidencias.get(0)));
        System.out.println("Nueva lista vacía: " + nuevaLista.get());
        System.out.println("Fabricada: " + fabrica.crear(
                "INC-004", "Prueba", "ABIERTA", 4));
        System.out.println("Bitácora: " + bitacora);
    }
}
