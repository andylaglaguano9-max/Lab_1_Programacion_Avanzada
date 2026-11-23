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

## Evidencias y reporte

Las evidencias reproducibles se generan con:

```text
py scripts/generar_evidencias.py
py scripts/insertar_evidencias_docx.py
```

El informe final es `Informe_Practica_Laboratorio_Evidencias.docx`. Las capturas
de `evidencias/capturas/` conservan la salida real de Maven, la ejecucion de
`App`, el codigo fuente y la mutacion temporal del umbral.

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
