package com.codingdojo.lookify.controllers;

import com.codingdojo.lookify.models.Cancion;
import com.codingdojo.lookify.services.CancionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiCancionesController {
    private final CancionService cancionService;
    public ApiCancionesController(CancionService cancionService){
        this.cancionService = cancionService;
    }

    //GET para todas las canciones
    @GetMapping("/canciones")
    public List<Cancion> returnCanciones(){
        return cancionService.allCanciones();
    }

    //POST para cargar canciones
    @PostMapping("/canciones")
    public Cancion addCancion(@RequestBody Cancion cancion){
        return cancionService.crearCancion(cancion);
    }

    //GET para buscar por artista
    @GetMapping("/canciones/busqueda/{artista}")
    public List<Cancion> buscar(@PathVariable("artista") String artista){
        return cancionService.findPorArtista(artista);
    }

    //DELETE para borrar canciones
    @DeleteMapping("/canciones/delete/{id}")
    public String eliminarCancion(@PathVariable("id") Long id){
        cancionService.deleteCancion(id);
        return "Cancion co id: "+id+" ha sido eliminada";
    }

    //GET para obtener el top 10 (En las pruebas tuve un error por haber cargado canciones con valores nulos, hay que tener en cuenta esos detalles, los null rompieron el codigo del servicio, me di cuenta despues de un rato)
    @GetMapping("/canciones/topTen")
    public List<Cancion> topTen(){
        return cancionService.armarTop10();
    }

    //GET para obtener cancion por id
    @GetMapping("/canciones/{id}")
    public Cancion buscarPorId(@PathVariable("id") Long id){
        return cancionService.findCancion(id);
    }
}
