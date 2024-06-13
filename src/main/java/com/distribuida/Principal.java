package com.distribuida;

import com.google.gson.Gson;
import com.distribuida.db.Book;
import com.distribuida.servicios.ServicioBookImpl;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.Routing;

import java.util.List;

import static spark.Spark.*;

public class Principal {

    static SeContainer container;
    private static final Gson gson = new Gson();
    static List<Book> listarLibros(ServerRequest req, ServerResponse res) {
        var servicio = container.select(ServicioBookImpl.class)
                .get();
        res.send(gson.toJson(servicio.findAll()));
        return l;
    }

 /*   static Book buscarLibro(Request req, Response res) {
        var servicio = container.select(ServicioBookImpl.class)
                .get();
        res.type("application/json");

        String _id = req.params(":id");

        var book =  servicio.findById(Integer.valueOf(_id));

        if(book==null) {
            // 404
            halt(404, "Libro no encontrado");
        }

        return book;
    }

    static String actualizarLibro(Request req, Response res) {
        var servicio = container.select(ServicioBookImpl.class).get();
        res.type("application/json");
        Gson gson = new Gson();
        var libro = gson.fromJson(req.body(), Book.class);

        String _id = req.params(":id");

        var book =  servicio.findById(Integer.valueOf(_id));

        book.setAuthor(libro.getAuthor());
        book.setIsbn(libro.getIsbn());
        book.setPrice(libro.getPrice());
        book.setTitle(libro.getTitle());

        // Llama al servicio para crear la persona
        servicio.update(book);

        return gson.toJson("Libro actualizado");

    }

    static String crearLibro(Request req, Response res) {
        var servicio = container.select(ServicioBookImpl.class).get();
        res.type("application/json");
        Gson gson = new Gson();
        var libro = gson.fromJson(req.body(), Book.class);

        // Llama al servicio para crear la persona
        servicio.insert(libro);

        return gson.toJson("Libro registrado");

    }

    static String eliminarLibro(Request req, Response res) {
        var servicio = container.select(ServicioBookImpl.class)
                .get();
        res.type("application/json");

        String _id = req.params(":id");

        servicio.delete(Integer.valueOf(_id));

        return "Libro eliminado";
    }
*/
    public static void main(String[] args) {
        container = SeContainerInitializer
                .newInstance()
                .initialize();

        ServicioBookImpl servicio = container.select(ServicioBookImpl.class)
                .get();

        Gson gson = new Gson();

        HttpRouting routing = HttpRouting.builder().get("/books", Principal::listarLibros, gson::toJson)
                .get("/books/:id", Principal::buscarLibro, gson::toJson)
                .post("/books", Principal::crearLibro, gson::toJson)
                .put("/books/:id", Principal::actualizarLibro, gson::toJson)
                .put("/books/:id", Principal::actualizarLibro, gson::toJson).build();


        WebServer server = WebServer.builder()
                .host("localhost")
                .port(8080)
                .build()
                .start();



        /*get("/books", Principal::listarLibros, gson::toJson);
        get("/books/:id", Principal::buscarLibro, gson::toJson);
        post("/books", Principal::crearLibro, gson::toJson);
        put("/books/:id", Principal::actualizarLibro, gson::toJson);
        put("/books/:id", Principal::actualizarLibro, gson::toJson);*/

    }
}
