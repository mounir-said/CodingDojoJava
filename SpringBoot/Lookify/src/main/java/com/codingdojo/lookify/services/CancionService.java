package com.codingdojo.lookify.services;

import com.codingdojo.lookify.models.Cancion;
import com.codingdojo.lookify.repositories.CancionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CancionService {
    private final CancionRepository cancionRepository;
    public CancionService(CancionRepository cancionRepository){
        this.cancionRepository = cancionRepository;
    }

    //Devolver todas las canciones
    public List<Cancion> allCanciones(){
        return cancionRepository.findAll();
    }

    //Crear canción
    public Cancion crearCancion(Cancion c){
        return cancionRepository.save(c);
    }

    //Buscar cancion y retornala si esta, sino retorna null
    public Cancion findCancion(Long id){
        Optional<Cancion> optionalCancion = cancionRepository.findById(id);
        if (optionalCancion.isPresent()){
            return optionalCancion.get();
        }else{
            return null;
        }
    }

    //Buscar cancion por artista
    public List<Cancion> findPorArtista(String artista){
        return cancionRepository.findByArtista(artista);
    }

    //Eliminar canción
    public void deleteCancion(Long id){
        if(cancionRepository.existsById(id)){
            cancionRepository.deleteById(id);
        }else{
            throw new NoSuchElementException("No se encontró el elemento con id "+ id);
        }
    }

    //Encontrar las 10 canciones con el rango mas alto
    public List<Cancion> armarTop10(){
        List<Cancion> allSongs = cancionRepository.findAll();

        //Revisar este código, necesito integrar conceptos para entenderlo mejor
        List<Cancion> sortedSongs = allSongs.stream()
                .sorted(Comparator.comparingInt(Cancion::getClasificacion).reversed())
                .collect(Collectors.toList());

        return sortedSongs.subList(0, Math.min(sortedSongs.size(),10));
    }
}
