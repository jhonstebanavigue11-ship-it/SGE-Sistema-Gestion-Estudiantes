# Sistema de Gestión de Estudiantes (SGE) básico

Aplicación por consola en Java para agregar, mostrar y buscar estudiantes por ID.

## Funcionalidades
1. Agregar estudiante (ID único, nombre, carrera y promedio entre 0.0 y 5.0)
2. Mostrar todos los estudiantes
3. Buscar estudiante por ID
4. Salir

## Estructuras de control usadas
- `while` con bandera (`banderaSalir`) para el menú principal
- `switch` para las opciones del menú
- `if` / `if-else` para validaciones y para el resultado de la búsqueda
- `for` para recorrer la lista
- `do-while` y `try-catch` para validar entradas
- Variable de conteo: `totalEstudiantes`
- Estructura de datos: `ArrayList<Estudiante>`

## Cómo ejecutarlo
Requiere JDK 11 o superior.

```bash
javac SGE.java
java SGE
```

o directamente:

```bash
java SGE.java
```
