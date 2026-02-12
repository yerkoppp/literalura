package dev.ycosorio.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicioLibro {
    private Map<String, Double> tasasFiltradas;

    private static final String BASE_URL = "https://gutendex.com/books/";

    // Extraemos los códigos directamente del Enum
    public ServicioLibro(){
        try {
            inicializarLista();
        } catch (Exception e) {
            System.err.println("[!] Alerta: No se pudieron cargar los libros en tiempo real.");
            System.err.println("Motivo: " + e.getMessage());
        }
    }
    public void inicializarLista(){

        ObjectMapper mapper = new ObjectMapper();

        String url = BASE_URL;

        try {
            HttpClient cliente = HttpClient.newHttpClient();
            // Creando la solicitud
            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            RespuestaApi data = mapper.readValue(respuesta.body(), RespuestaApi.class);


            data.listaDeLibros().stream().forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la API: " + e.getMessage());
        }
    }
}
