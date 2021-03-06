package com.tognyp.springsecurity.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tognyp.springsecurity.demo.dao.QuestionDao;
import com.tognyp.springsecurity.demo.dao.ResponseDao;
import com.tognyp.springsecurity.demo.entity.Question;
import com.tognyp.springsecurity.demo.entity.Response;
import com.tognyp.springsecurity.demo.entity.User;
import com.tognyp.springsecurity.demo.user.QuestResponse;
import com.tognyp.springsecurity.demo.user.ResponsesViewModel;

/**
* Response service implementation use DAO design pattern
* 
*
* 
* @version 1.0
* @since   2020-06-03
*/

@Service
public class ResponseServiceImpl implements ResponseService {

	@Autowired
	private ResponseDao responseDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public void save(ResponsesViewModel responsesViewModel, User user, String passwd) {
		
		List<QuestResponse> tmpResponses = responsesViewModel.getResponses();
		
		List<Response> theResponses = new ArrayList<>();
		
		for(QuestResponse q : tmpResponses) {
			Response tmpResponse = new Response();
			String verification = passwd + q.getQuestionnaireId() + q.getQuestionId() + q.getText();
			tmpResponse.setQuestionId(q.getQuestionId());
			tmpResponse.setQuestionnaireId(q.getQuestionnaireId());
			tmpResponse.setText(q.getText());
			tmpResponse.setUser(passwordEncoder.encode(passwd));
			tmpResponse.setVerification(passwordEncoder.encode(verification));
			theResponses.add(tmpResponse);
		}
		
		responseDao.save(theResponses);
	}

	@Override
	@Transactional
	public List<Response> findByUsername(String username, String questionnaireId) {
		return responseDao.findByUsername(username, questionnaireId);
		
	}

	@Override
	@Transactional
	public List<Response> findByQuestionnaireId(String questionnaireId) {
		
		List<Response> tmpResponses = responseDao.findByQuestionnaireId(questionnaireId);
		
		List<Response> toRemove = new ArrayList<>();
		List<Response> toAdd = new ArrayList<>();
		
		for(Response r : tmpResponses) {
			
			Question tmpQuestion = questionDao.getQuestionById(r.getQuestionId().toString());
			if(tmpQuestion.getType().equals("2")) {
				String[] separateText = r.getText().split(",");
				for(String s : separateText) {
					Response tmpResp = new Response();
					tmpResp.setId(r.getId());
					tmpResp.setQuestionId(r.getQuestionId());
					tmpResp.setQuestionnaireId(r.getQuestionnaireId());
					tmpResp.setText(s);
					tmpResp.setUser(r.getUser());
					tmpResp.setVerification(r.getVerification());
					toAdd.add(tmpResp);
				}
				toRemove.add(r);
			}
			
		}
		
		tmpResponses.removeAll(toRemove);
		tmpResponses.addAll(toAdd);
		
		return tmpResponses;
	}

}
