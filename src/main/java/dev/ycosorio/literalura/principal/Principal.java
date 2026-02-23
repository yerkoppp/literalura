package dev.ycosorio.literalura.principal;

import dev.ycosorio.literalura.model.Autor;
import dev.ycosorio.literalura.model.Libro;
import dev.ycosorio.literalura.model.RespuestaApi;
import dev.ycosorio.literalura.model.RespuestaLibro;
import dev.ycosorio.literalura.repository.AutorRepository;
import dev.ycosorio.literalura.repository.LibrosRepository;
import dev.ycosorio.literalura.service.ConsumoApi;
import dev.ycosorio.literalura.service.ConvierteDatos;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private static final String BASE_URL = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();

    private LibrosRepository librosRepositorio;
    private AutorRepository autorRepository;

    public Principal(LibrosRepository librosRepositorio, AutorRepository autorRepository){
        this.librosRepositorio = librosRepositorio;
        this.autorRepository = autorRepository;
    }

    public void obtenerLibros(){
        try {
            inicializarLista("");
        } catch (Exception e) {
            System.err.println("[!] Alerta: No se pudieron cargar los libros en tiempo real.");
            System.err.println("Motivo: " + e.getMessage());
        }
    }

    public void inicializarLista(String busqueda){
        var json = consumoApi.obtenerDatos(BASE_URL+busqueda);
        RespuestaApi datos = conversor.obtenerDatos(json, RespuestaApi.class);
        if(datos.cantidad()==0){
            System.out.println("Libro no encontrado, intente con otro.");
        }
        else {
            datos.listaDeLibros().stream().forEach(l ->
                    System.out.println("(ID: "+l.id() + ") Título: " + l.titulo() + ", Autores:" + l.autores()));
            guardarLibro(datos.listaDeLibros());
        }
    }

    public void mostrarMenu(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    -------- Menú --------
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ----------------------
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1; // Forzamos al default del switch
            }

            switch (opcion) {
                case 1 -> buscarLibrosEnApi();
                case 2 -> mostrarLibrosRegistrados();
                case 3 -> mostrarAutoresRegistrados();
                case 4 -> mostrarAutoresVivos();
                case 5 -> mostrarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida, intente nuevamente.");
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
        System.out.println("-".repeat(60));
        System.out.println("Ingrese el ID del libro que desea guardar o presione 0 para salir: ");
        try {
            Long id = Long.valueOf(sc.nextLine());

            if(id == 0) return;

            respuestaLibros.stream()
                    .filter(l -> l.id().equals(id))
                    .findFirst()
                    .ifPresent(l -> {
                        librosRepositorio.save(new Libro(l));
                        System.out.println("Libro guardado en la base de datos.");
                        System.out.println("-".repeat(60));
                    });
        } catch (NumberFormatException e) { //Evita el crash si no ingresan números
            System.out.println("Debe ingresar un número válido.");
        }
    }


    private void mostrarLibrosRegistrados() {
        List<Libro> libros = librosRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("Aún no hay autores registrados.");
            return;
        }
        librosRepositorio.findAll().stream().forEach(System.out::println);
    }
    private void mostrarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Aún no hay autores registrados.");
            return;
        }
        autores.stream().forEach(System.out::println);
    }
    private void mostrarAutoresVivos() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Aún no hay autores registrados.");
            return;
        }
        System.out.println("Indique un año:");
        Integer anio = Integer.valueOf(sc.nextLine());
        autorRepository.autoresVivosEnDeterminadoAnio(anio).stream().forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        List<String> idiomas = librosRepositorio.findIdiomasRegistrados();

        if (idiomas.isEmpty()) {
            System.out.println("Aún no hay libros registrados.");
            return;
        }

        menuIdiomas(idiomas);
    }

    private void menuIdiomas(List<String> idiomas){

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
                librosRepositorio.findByIdiomas(codigoSeleccionado).forEach(System.out::println);
            } else {
                System.out.println("Opción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
        }

    }
}
