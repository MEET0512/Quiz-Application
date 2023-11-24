package com.patel.questionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patel.questionservice.model.Question;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
	public List<Question> findByCategory(String Category);

	@Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numQue", nativeQuery = true)
	public List<Integer> findRandomQuestionsByCategory(String category, Integer numQue);
}
