package com.tognyp.springsecurity.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tognyp.springsecurity.demo.entity.Question;
import com.tognyp.springsecurity.demo.entity.Questionnaire;

@Repository
public class QuestionDaoImpl implements QuestionDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Question theQuestion, Long questionnaireId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Questionnaire tempQuestionnaire = currentSession.get(Questionnaire.class, questionnaireId);
		theQuestion.setQuestionnaire(tempQuestionnaire);
		
		currentSession.saveOrUpdate(theQuestion);

	}

	@Override
	public List<Question> getQuestions(int theId) {

		Session currentSession = sessionFactory.getCurrentSession();
		
		/*Query<Question> theQuery = currentSession.createQuery(
				"select Question.title from Question question"
				+ " join question.questionnaire questionnaire"
				+ " where questionnaide.id =: theId ", Question.class);
		*/
		Query<Question> theQuery = currentSession.createQuery("from Question", Question.class);
		
		//theQuery.setParameter("theId", theId);
		
		List<Question> questions = theQuery.getResultList();
		
		return questions;
	}

	@Override
	public Question getQuestionById(String id) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Question theQuestion = new Question();
		
		System.out.println("parsing id: " + id);
		
		//int theId = Integer.parseInt(id);
		Long theId = Long.parseLong(id);
		
		System.out.println("id parsed: " + theId);
		
		theQuestion = currentSession.get(Question.class, theId);
		
		System.out.println("getQuestionById: Question = " + theQuestion);
		
		return theQuestion;
	}

}
