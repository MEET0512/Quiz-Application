package com.patel.quizservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patel.quizservice.model.QuestionWrapper;
import com.patel.quizservice.model.QuizDto;
import com.patel.quizservice.model.Response;
import com.patel.quizservice.service.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

	final private QuizService quizService;
	
	@PostMapping("/create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
		return quizService.createQuiz(quizDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuestionForQuiz(@PathVariable Integer id){
		return quizService.getQuestionsForQuiz(id);
	}
	
	@PostMapping("/submit/{id}")
	public ResponseEntity<Integer> SubmitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
		return quizService.getResult(id, responses);
	}
}
