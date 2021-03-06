package com.tognyp.springsecurity.demo.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tognyp.springsecurity.demo.entity.Question;
import com.tognyp.springsecurity.demo.entity.Questionnaire;
import com.tognyp.springsecurity.demo.service.QuesionnaireService;
import com.tognyp.springsecurity.demo.service.QuestionService;

/**
* Controller responsible for Question operation
* 
*
* 
* @version 1.0
* @since   2020-06-03
*/

@Controller
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuesionnaireService questionnaireService;

	/**
	* Saving question to database
	* 
	*
	* @param theQuestion Question model include necessary data
	* @param request Http Request to get parameters from view
	* @return Redirect to show questionnaire view
	* @version 1.0
	* @since   2020-06-03
	*/
	
	@PostMapping("/saveQuestion")
	public String saveQuestion(HttpServletRequest request, @ModelAttribute("question") Question theQuestion) {

		String temp = request.getParameter("questionnaire");
		System.out.println("title: " + theQuestion.getTitle());
		System.out.println("id: " + theQuestion.getId());
		System.out.println("type: " + theQuestion.getType());
		System.out.println("questionnaireid: " + theQuestion.getQuestionnaire().getId());
		questionService.save(theQuestion, Long.parseLong(temp));
		
		return "redirect:/questionnaires/show-questionnaire";
	}
	
	/**
	* Getting add question view
	* 
	*
	* @param theModel Model passed to view
	* @return View used to add question
	* @version 1.0
	* @since   2020-06-03
	*/
	
	@GetMapping("/addQuestion")
	public String addQuestion(Model theModel) {
		
		Question tempQuestion = new Question();
		theModel.addAttribute("question", tempQuestion);
		
		return "add-question";
	}
	
	/**
	* Showing questions view
	* 
	*
	* @param theModel Model passed to view
	* @param request Http Request to get parameters from view
	* @return View used to show questions
	* @version 1.0
	* @since   2020-06-03
	*/
	
	@GetMapping("/showQuestions")
	public String showQuestions(HttpServletRequest request, Model theModel) {
		
		Questionnaire tempQuestionnaire = new Questionnaire();
		tempQuestionnaire = questionnaireService.findQuestionnaireById(Long.parseLong(request.getParameter("questionnaireId")));
		Set<Question> questionList = tempQuestionnaire.getQuestions();

		theModel.addAttribute("questions", questionList);
		
		return "show-quest";
	}
}
