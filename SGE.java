import java.util.ArrayList;
import java.util.Scanner;

/**
 * Sistema de Gestión de Estudiantes (SGE) básico - aplicación por consola.
 * Funcionalidades: agregar, mostrar, buscar por ID y salir.
 */
public class SGE {

    // Lista dinámica: crece sin límite fijo, por eso no hay que controlar un tamaño máximo.
    private static final ArrayList<Estudiante> estudiantes = new ArrayList<>();

    // Variable de CONTEO: lleva el total de estudiantes registrados.
    private static int totalEstudiantes = 0;

    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        // Variable BANDERA: controla cuándo termina el ciclo principal.
        boolean banderaSalir = false;
        int opcion;

        // Repetición condicionada al comienzo (while): el menú se repite hasta que la bandera cambie.
        while (!banderaSalir) {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            // Decisión múltiple (switch): dirige el flujo a la opción elegida.
            switch (opcion) {
                case 1:
                    agregarEstudiante();
                    break;
                case 2:
                    mostrarEstudiantes();
                    break;
                case 3:
                    buscarPorId();
                    break;
                case 4:
                    banderaSalir = true; // Se activa la bandera: el while termina.
                    break;
                default:
                    System.out.println("\nOpción no válida. Elija un número entre 1 y 4.");
            }
        }

        // Mensaje de despedida: se muestra una vez que el ciclo principal terminó.
        System.out.println("\nSaliendo del sistema. ¡Hasta pronto!");
        teclado.close();
    }

    // ---------------------------------------------------------------
    // Funciones reutilizables
    // ---------------------------------------------------------------

    /** Imprime el menú principal. */
    private static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE GESTIÓN DE ESTUDIANTES =====");
        System.out.println("1. Agregar estudiante");
        System.out.println("2. Mostrar todos los estudiantes");
        System.out.println("3. Buscar estudiante por ID");
        System.out.println("4. Salir");
        System.out.println("=============================================");
    }

    /** Pide los datos, valida y agrega un estudiante a la lista. */
    private static void agregarEstudiante() {
        System.out.println("\n--- Agregar estudiante ---");
        int id = leerEntero("ID (número entero positivo): ");

        // Decisión ANIDADA: el segundo if solo se evalúa si el primero se cumple.
        if (id > 0) {
            if (buscarIndicePorId(id) == -1) {
                // El ID es positivo y no está repetido: se piden los demás datos.
                String nombre = leerTextoNoVacio("Nombre completo: ");
                String carrera = leerTextoNoVacio("Carrera: ");
                double promedio = leerPromedio("Promedio (0.0 a 5.0): ");

                estudiantes.add(new Estudiante(id, nombre, carrera, promedio));
                totalEstudiantes++; // Conteo: aumenta en 1 por cada registro exitoso.
                System.out.println("Estudiante agregado correctamente. Total registrados: " + totalEstudiantes);
            } else {
                System.out.println("Ya existe un estudiante con ese ID. Registro cancelado.");
            }
        } else {
            System.out.println("El ID debe ser mayor que cero. Registro cancelado.");
        }
    }

    /** Recorre la lista y muestra todos los estudiantes. */
    private static void mostrarEstudiantes() {
        System.out.println("\n--- Lista de estudiantes ---");

        // Decisión simple: si no hay registros, se informa y se termina la función.
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados todavía.");
            return;
        }

        // Repetición controlada por contador (for ascendente).
        for (int i = 0; i < estudiantes.size(); i++) {
            System.out.print((i + 1) + ". ");
            mostrarEstudiante(estudiantes.get(i));
        }
        System.out.println("Total de estudiantes: " + totalEstudiantes);
    }

    /** Busca un estudiante por su ID y muestra el resultado. */
    private static void buscarPorId() {
        System.out.println("\n--- Buscar estudiante por ID ---");
        int id = leerEntero("ID a buscar: ");
        int indice = buscarIndicePorId(id);

        // Decisión doble (if/else): encontrado o no encontrado.
        if (indice != -1) {
            System.out.println("Estudiante encontrado:");
            mostrarEstudiante(estudiantes.get(indice));
        } else {
            System.out.println("No se encontró ningún estudiante con el ID " + id + ".");
        }
    }

    /** Muestra los datos de UN estudiante. */
    private static void mostrarEstudiante(Estudiante e) {
        System.out.println("ID: " + e.getId() + " | Nombre: " + e.getNombre()
                + " | Carrera: " + e.getCarrera() + " | Promedio: " + e.getPromedio());
    }

    /** Devuelve la posición del estudiante en la lista, o -1 si no existe. */
    private static int buscarIndicePorId(int id) {
        int posicion = -1;
        int i = 0;
        // Repetición condicionada al comienzo: se detiene apenas lo encuentra.
        while (i < estudiantes.size() && posicion == -1) {
            if (estudiantes.get(i).getId() == id) {
                posicion = i;
            }
            i++;
        }
        return posicion;
    }

    // ---------------------------------------------------------------
    // Validación de entradas (evita que el programa se caiga)
    // ---------------------------------------------------------------

    /** Lee un entero; si el usuario escribe texto, vuelve a pedirlo. */
    private static int leerEntero(String mensaje) {
        int valor = 0;
        boolean valido = false;
        // Repetición condicionada al final (do-while): se pide al menos una vez.
        do {
            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();
            try {
                valor = Integer.parseInt(entrada);
                valido = true;
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida: debe ingresar un número entero.");
            }
        } while (!valido);
        return valor;
    }

    /** Lee un promedio entre 0.0 y 5.0. */
    private static double leerPromedio(String mensaje) {
        double valor = 0;
        boolean valido = false;
        do {
            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim().replace(',', '.');
            try {
                valor = Double.parseDouble(entrada);
                if (valor >= 0.0 && valor <= 5.0) {
                    valido = true;
                } else {
                    System.out.println("El promedio debe estar entre 0.0 y 5.0.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida: debe ingresar un número (ej. 4.2).");
            }
        } while (!valido);
        return valor;
    }

    /** Lee un texto que no puede quedar vacío. */
    private static String leerTextoNoVacio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede quedar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }
}

/** Clase que representa a un estudiante. */
class Estudiante {
    private final int id;
    private final String nombre;
    private final String carrera;
    private final double promedio;

    public Estudiante(int id, String nombre, String carrera, double promedio) {
        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCarrera() { return carrera; }
    public double getPromedio() { return promedio; }
}
