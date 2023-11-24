package com.patel.quizservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.patel.quizservice.feign.Configuration;
import com.patel.quizservice.model.QuestionWrapper;
import com.patel.quizservice.model.Quiz;
import com.patel.quizservice.model.QuizDto;
import com.patel.quizservice.model.Response;
import com.patel.quizservice.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {
	final private QuizRepository quizRepo;

	final private Configuration config;
	
	public ResponseEntity<String> createQuiz(QuizDto quizDto) {
		List<Integer> questionsId = config.generateQuestionsForQuiz(quizDto.getCategory(), quizDto.getNumQue()).getBody();
		
		Quiz quiz = Quiz.builder()
						.title(quizDto.getTitle())
						.questionsIds(questionsId)
						.build();
		quizRepo.save(quiz);
		
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(Integer id) {
		try {
			Optional<Quiz> quiz = quizRepo.findById(id);
			if(quiz.isEmpty()) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
			}
			ResponseEntity<List<QuestionWrapper>> questions = config.getQuestionsForQuiz(quiz.get().getQuestionsIds());
			return questions;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Integer> getResult(Integer id, List<Response> responses) {
		ResponseEntity<Integer> result = config.getScoreOfQuiz(responses);
		return result;
	}
	
	
}
