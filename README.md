# Laboratorio 2.1: Funciones y contratos funcionales

Proyecto Maven de consola para Java 21. El caso práctico separa la selección,
transformación y acción sobre incidencias mediante `Predicate`, `Function`,
`Consumer`, `Supplier` y el contrato propio `ReglaIncidencia`.

## Ejecución

```text
mvn -v
mvn test
mvn exec:java -Dexec.mainClass=ec.edu.espe.pa.funcional.App
```

El proyecto no altera la lista de entrada. La solución imperativa se conserva
como referencia de equivalencia y la solución funcional recibe las funciones
como parámetros.

## Estructura

```text
src/main/java/ec/edu/espe/pa/funcional/
├── contrato/     FabricaIncidencia.java, ReglaIncidencia.java
├── funcional/    ProcesadorFuncional.java
├── imperativo/   ProcesadorImperativo.java
├── modelo/       Incidencia.java
├── reglas/       PrioridadAlta.java, ReglasIncidencia.java
├── App.java
└── Demostracion.java
```

Las pruebas unitarias se encuentran en `src/test/java` y validan las reglas,
la equivalencia entre las implementaciones y la conservación de los datos
originales.
