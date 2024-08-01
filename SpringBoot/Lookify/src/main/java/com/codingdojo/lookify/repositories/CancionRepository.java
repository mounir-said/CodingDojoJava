package com.codingdojo.lookify.repositories;

import com.codingdojo.lookify.models.Cancion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancionRepository extends CrudRepository<Cancion, Long> {
    List<Cancion>findAll();

    /*
    Cuando definimos un método en un repositorio de Spring Data JPA,
    la nomenclatura es importante.
    Spring Data JPA analiza el nombre del método y,
    basándose en ciertas palabras clave clave como findBy,
    getBy, readBy, queryBy, searchBy, entre otros, identifica
    lo que se debe hacer con el método.
    */
    List<Cancion> findByArtista(String artista);
}
