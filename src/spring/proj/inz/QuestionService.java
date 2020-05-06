package com.tognyp.springsecurity.demo.service;

import java.util.List;

import com.tognyp.springsecurity.demo.entity.Question;

public interface QuestionService {
	
	public void save(Question theQuestion, Long questionnaireId);

	public List<Question> getQuestions(int id);
}
