package com.codingdojo.Overflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.Overflow.models.Tag;
import com.codingdojo.Overflow.repositories.TagRepository;

@Service
public class TagService {
	@Autowired
	private TagRepository tagRepository;
	
	public Tag findBySubject(String subject) {
		return tagRepository.findBySubjectIs(subject);
	}
	
	public Tag addTag(Tag tag) {
		return tagRepository.save(tag);
	}
}