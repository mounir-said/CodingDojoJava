package com.codingdojo.Overflow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.Overflow.models.Answer;
import com.codingdojo.Overflow.repositories.AnswerRepository;

@Service
public class AnswerService {
	@Autowired
	private AnswerRepository answerRepository;
	
	public List<Answer> questionAnswers(Long questionId) {
		return answerRepository.findByQuestionIdIs(questionId);
	}
	
	public Answer addAnswer(Answer answer) {
		return answerRepository.save(answer);
	}
}