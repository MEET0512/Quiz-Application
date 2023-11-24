package com.patel.questionservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.patel.questionservice.model.Question;
import com.patel.questionservice.model.QuestionWrapper;
import com.patel.questionservice.model.Response;
import com.patel.questionservice.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepo;
	
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionRepo.findAll(),HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	
	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
			return new ResponseEntity<>(questionRepo.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	
	public ResponseEntity<String> addQuestion(List<Question> questions) {
		try {
			for(Question que: questions) {
				questionRepo.save(que);
			}
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("There is some problem.", HttpStatus.PARTIAL_CONTENT);
	}

	
	public ResponseEntity<List<Integer>> generateQuizQuestions(String category, int numQuestions) {
		List<Integer> questionsId = questionRepo.findRandomQuestionsByCategory(category, numQuestions);
		return new ResponseEntity<>(questionsId, HttpStatus.OK);
	}
	
	

	public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers = new ArrayList<>();
		
		for(Integer id : questionIds) {
			Question q = questionRepo.findById(id).get();
			wrappers.add(QuestionWrapper.builder()
							.id(q.getId())
							.questionTitle(q.getQuestionTitle())
							.option1(q.getOption1())
							.option2(q.getOption2())
							.option3(q.getOption3())
							.option4(q.getOption4())
							.difficultyLevel(q.getDifficultyLevel())
							.build()
							);
		}
		
		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}


	//Calculate the score for particular quiz by checking the answers are right in response or not
	public ResponseEntity<Integer> getScore(List<Response > responses) {
		
		int right = 0;

		for(Response res : responses) {
			Question que = questionRepo.findById(res.getId()).get();
			if(res.getResponse().equals(que.getRightAnswer())) {
				right++;
			}
			
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}

}
