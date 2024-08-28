package com.codingdojo.Overflow.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.Overflow.models.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
	Tag findBySubjectIs(String subject);
}