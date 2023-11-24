package com.patel.quizservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patel.quizservice.model.Quiz;



public interface QuizRepository extends JpaRepository<Quiz, Integer> {

}
