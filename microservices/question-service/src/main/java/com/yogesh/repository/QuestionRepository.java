package com.yogesh.repository;

import com.yogesh.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByCategory(String category);

    //getting question id's instead of whole record(object)
    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :noOfQuestions",nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer noOfQuestions);
}
