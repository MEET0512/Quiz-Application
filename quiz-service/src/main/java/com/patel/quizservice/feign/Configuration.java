package com.patel.quizservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.patel.quizservice.model.QuestionWrapper;
import com.patel.quizservice.model.Response;



@FeignClient("QUESTION-SERVICE")
public interface Configuration {

	//Generate questions for quiz
		@GetMapping("/question/generate")
		public ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam String Category,@RequestParam int numQuestions);
		
		//Get questions for particular quiz 
		@PostMapping("/question/getQuestions")
		public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds);
		
		//Calculate the quiz score
		@PostMapping("/question/getScore")
		public ResponseEntity<Integer> getScoreOfQuiz(@RequestBody List<Response> responses);
}
