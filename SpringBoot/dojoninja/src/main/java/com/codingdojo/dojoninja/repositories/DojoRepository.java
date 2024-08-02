package com.codingdojo.dojoninja.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.dojoninja.models.Dojo;

@Repository
public interface DojoRepository extends CrudRepository<Dojo, Long> {
}
