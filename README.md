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

Las evidencias reproducibles completas se generan en Windows con:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\run_evidence.ps1
py .\scripts\txt_to_png.py
```

El informe final es `Informe_Practica_Laboratorio_Evidencias.docx`. Los TXT de
`evidence/` conservan comando, hora, salida completa, código de salida y estado;
las imágenes oscuras equivalentes quedan en `evidence/img/`. La evidencia de
mutación conserva el fallo real y restaura el operador antes de la prueba final.

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
