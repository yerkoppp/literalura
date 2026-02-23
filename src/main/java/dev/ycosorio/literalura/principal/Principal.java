package dev.ycosorio.literalura.principal;

import dev.ycosorio.literalura.model.Autor;
import dev.ycosorio.literalura.model.Libro;
import dev.ycosorio.literalura.model.RespuestaApi;
import dev.ycosorio.literalura.model.RespuestaLibro;
import dev.ycosorio.literalura.repository.AutorRepository;
import dev.ycosorio.literalura.repository.LibrosRepository;
import dev.ycosorio.literalura.service.ConsumoApi;
import dev.ycosorio.literalura.service.ConvierteDatos;
import dev.ycosorio.literalura.service.ServicioLibro;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    // Códigos ANSI para colores y estilos
    private final String RESET = "\u001B[0m";
    private final String CIAN = "\u001B[36m";
    private final String VERDE = "\u001B[32m";
    private final String AMARILLO = "\u001B[33m";
    private final String CURSIVA = "\u001B[3m";
    private final String NEGRITA = "\u001B[1m";

    private final Scanner sc = new Scanner(System.in);
    private static final String BASE_URL = "https://gutendex.com/books/";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConvierteDatos conversor = new ConvierteDatos();

    private final ServicioLibro servicioLibro;
    public Principal(ServicioLibro servicioLibro){
        this.servicioLibro = servicioLibro;
    }

    public void inicializarLista(String busqueda){
        var json = consumoApi.obtenerDatos(BASE_URL+busqueda);
        RespuestaApi datos = conversor.obtenerDatos(json, RespuestaApi.class);
        if(datos.cantidad()==0){
            System.out.println(AMARILLO + "⚠️ No se encontraron resultados para su búsqueda. Intente con otras palabras." + RESET);}
        else {
            datos.listaDeLibros().forEach(l ->
                    System.out.println("(ID: "+l.id() + ") Título: " + l.titulo() + ", Autores:" + l.autores()));
            guardarLibro(datos.listaDeLibros());
        }
    }

    public void mostrarMenu(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = CIAN + NEGRITA + """
                    
                    ╭────────────────────────────────────────╮
                    │           📚 LiterAlura 📚             │
                    ╰────────────────────────────────────────╯
                    """ + RESET + VERDE + """
                    1 - Buscar libro por título y/o autor
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Buscar libros por autor
                    7 - Listar Top 10 más descargados
                    8 - Ver estadísticas generales
                    0 - Salir
                    ----------------------
                    """+ RESET;
            System.out.println(menu);
            System.out.print(CURSIVA + CIAN + "Elija una opción: " + RESET);
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> buscarLibrosEnApi();
                case 2 -> mostrarLibrosRegistrados();
                case 3 -> mostrarAutoresRegistrados();
                case 4 -> mostrarAutoresVivos();
                case 5 -> mostrarLibrosPorIdioma();
                case 6 -> mostrarLibrosPorAutor();
                case 7 -> mostrarTop10();
                case 8 -> generarEstadisticas();
                case 0 -> System.out.println(AMARILLO + "Cerrando la aplicación... ¡Hasta pronto!" + RESET);
                default -> System.out.println(AMARILLO + "⚠️ Opción inválida. Por favor, seleccione un número del menú." + RESET);
            }
        }
    }


    private void buscarLibrosEnApi() {
        System.out.println("¿Qué libro desea buscar?: ");
        var libroBuscado = sc.nextLine().replace(" ", "%20");
        String BASE_BUSQUEDA = "?search=";
        inicializarLista(BASE_BUSQUEDA+libroBuscado);
    }

    private void guardarLibro(List<RespuestaLibro> respuestaLibros){
        System.out.println(CIAN + "-".repeat(60) + RESET);
        System.out.print(CURSIVA + CIAN + "Ingrese el ID del libro que desea guardar (o 0 para cancelar): " + RESET);
        try {
            Long id = Long.valueOf(sc.nextLine());

            if(id == 0) return;

            respuestaLibros.stream()
                    .filter(l -> l.id().equals(id))
                    .findFirst()
                    .ifPresent(l -> {
                        servicioLibro.guardarLibro(l);
                        System.out.println(VERDE + NEGRITA + "✅ ¡Libro guardado exitosamente en la base de datos!" + RESET);
                        System.out.println(CIAN + "-".repeat(60) + RESET);
                    });
        } catch (NumberFormatException e) { //Evita el crash si no ingresan números
            System.out.println(AMARILLO + "⚠️ Error: Debe ingresar exclusivamente números." + RESET);
        }
    }

    private void mostrarLibrosRegistrados() {
        List<Libro> libros = servicioLibro.listarLibros();

        if (libros.isEmpty()) {
            System.out.println(AMARILLO + "📭 La base de datos está vacía. Use la opción 1 para buscar y guardar libros primero." + RESET);
            return;
        }
        libros.forEach(System.out::println);
    }
    private void mostrarAutoresRegistrados() {
        List<Autor> autores = servicioLibro.listarAutores();

        if (autores.isEmpty()) {
            System.out.println(AMARILLO + "📭 La base de datos está vacía. Use la opción 1 para buscar y guardar libros primero." + RESET);
            return;
        }
        autores.forEach(System.out::println);
    }
    private void mostrarAutoresVivos() {
        List<Autor> autores = servicioLibro.listarAutores();

        if (autores.isEmpty()) {
            System.out.println(AMARILLO + "📭 La base de datos está vacía. Use la opción 1 para buscar y guardar libros primero." + RESET);
            return;
        }
        System.out.println("Indique un año:");
        try {
            Integer anio = Integer.valueOf(sc.nextLine());
            servicioLibro.listarAutoresVivos(anio).forEach(System.out::println);
        } catch (NumberFormatException e){
            System.out.println(AMARILLO + "⚠️ Error: Entrada inválida. Por favor, ingrese únicamente números (ej: 1850)." + RESET);
        }
        }

    private void mostrarLibrosPorIdioma() {
        List<String> idiomas = servicioLibro.listarIdiomas();

        if (idiomas.isEmpty()) {
            System.out.println(AMARILLO + "📭 La base de datos está vacía. Use la opción 1 para buscar y guardar libros primero." + RESET);
            return;
        }

        menuIdiomas(idiomas);
    }

    private void menuIdiomas(List<String> idiomas) {

        System.out.println("\n--- Idiomas disponibles ---");
        for (int i = 0; i < idiomas.size(); i++) {
            String codigo = idiomas.get(i);
            // Convertimos el código ("en") al nombre completo en español ("inglés")
            String nombreIdioma = Locale.forLanguageTag(codigo).getDisplayLanguage(Locale.forLanguageTag("es"));

            // Capitalizamos la primera letra para que el menú se vea profesional ("Inglés")
            nombreIdioma = nombreIdioma.substring(0, 1).toUpperCase() + nombreIdioma.substring(1);

            System.out.println((i + 1) + " - " + nombreIdioma + " (" + codigo + ")");
        }
        System.out.println("0 - Cancelar");
        System.out.print("Seleccione el número del idioma: ");
        try {
            int opcion = Integer.parseInt(sc.nextLine());
            if (opcion == 0) return;

            // Validamos que la opción esté dentro del rango del menú
            if (opcion > 0 && opcion <= idiomas.size()) {
                String codigoSeleccionado = idiomas.get(opcion - 1);
                String nombreIdioma = Locale.forLanguageTag(codigoSeleccionado).getDisplayLanguage(Locale.forLanguageTag("es"));
                nombreIdioma = nombreIdioma.substring(0, 1).toUpperCase() + nombreIdioma.substring(1);

                System.out.println("\nBuscando libros en: " + nombreIdioma);
                servicioLibro.listarLibrosPorIdioma(codigoSeleccionado).forEach(System.out::println);
            } else {
                System.out.println("Opción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        }

    }

    public void mostrarLibrosPorAutor(){
        System.out.println("Ingrese un autor: ");
        var autorBuscado = sc.nextLine().toLowerCase();
        List<Libro> librosDelAutor = servicioLibro.buscarLibrosPorAutor(autorBuscado);
        if(!librosDelAutor.isEmpty()){
            librosDelAutor.forEach(System.out::println);
            return;
        }
        System.out.println(AMARILLO + "No se encontraron libros del autor en la base de datos.\n Use la opción 1 para buscar y guardar libros primero." + RESET);
    }

    private void mostrarTop10() {
        System.out.println("Top 10 Descargas");
        servicioLibro.listarTop10().forEach(System.out::println);
    }

    private void generarEstadisticas() {
        var libros = servicioLibro.listarLibros();

        if (libros.isEmpty()) {
            System.out.println(AMARILLO + "📭 La base de datos está vacía, no se pueden calcular las estadísticas.\n Use la opción 1 para buscar y guardar libros primero." + RESET);
            return;
        }

        java.util.DoubleSummaryStatistics est = servicioLibro.generarEstadisticas();

        if (est.getCount() == 0) {
            System.out.println(AMARILLO + "📭 La base de datos está vacía, no se pueden calcular las estadísticas.\n Use la opción 1 para buscar y guardar libros primero." + RESET);
            return;
        }

        System.out.println(CIAN + NEGRITA + "\n📊 --- Estadísticas Globales de Descargas ---" + RESET);
        System.out.println("Promedio de descargas: " + VERDE + Math.round(est.getAverage()) + RESET);
        System.out.println("Mayor cantidad:        " + VERDE + est.getMax() + RESET);
        System.out.println("Menor cantidad:        " + VERDE + est.getMin() + RESET);
        System.out.println("Total evaluados:       " + VERDE + est.getCount() + RESET);
        System.out.println(CIAN + "---------------------------------------------" + RESET);
    }
}
