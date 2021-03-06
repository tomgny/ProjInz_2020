package com.tognyp.springsecurity.demo.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tognyp.springsecurity.demo.entity.Answer;
import com.tognyp.springsecurity.demo.service.AnswerService;

/**
* Controller responsible for Answer operation
* 
*
* 
* @version 1.0
* @since   2020-06-03
*/

@Controller
@RequestMapping("/answer")
public class AsnwerController {
	
	@Autowired
	private AnswerService answerService;
	
	/**
	* Saving answer to database
	* 
	*
	* @param theModel Model passed to view
	* @param request Http Request to get parameters from view
	* @param questionnaireId Parameter received from previous view
	* @param theAnswer Model include necessary data of Answer
	* @param result Check is valid of binding
	* @param redirectAttributes Redirecting attributes to view
	* @return If binding has errors, return to previous view with errors, otherwise return Questionnaires view
	* @version 1.0
	* @since   2020-06-03
	*/
	
	@PostMapping("/saveAnswer/{questionnaireId}")
	public String saveAnswer(HttpServletRequest request, @PathVariable("questionnaireId") String questionnaireId, 
							 @ModelAttribute("answer") Answer theAnswer, BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			result.getAllErrors();
		}
		System.out.println("*******request answer********** " + theAnswer.getId());
		System.out.println("*******request********** " + request.getParameter("title"));
		System.out.println("*******question id********** " + request.getParameter("question"));
		System.out.println("*******pathVariable********** =  " + questionnaireId);
		
		Long questionId = Long.parseLong(request.getParameter("question"));
		
		answerService.save(theAnswer, questionId);
		
		redirectAttributes.addAttribute("questionnaireId", questionnaireId);
		
		return "redirect:/question/showQuestions";
	}
	
	/**
	* Getting add answer view
	* 
	*
	* @param theModel Model passed to view
	* @return View used to add answer
	* @version 1.0
	* @since   2020-06-03
	*/
	
	@GetMapping("/addAnswer")
	public String addAnswer(Model theModel) {
		
		Answer theAnswer = new Answer();
		theModel.addAttribute("answer", theAnswer);
		
		return "add-answer";
	}

}
