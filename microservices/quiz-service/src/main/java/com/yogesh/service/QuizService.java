package com.yogesh.service;

import com.yogesh.entity.QuestionWrapper;
import com.yogesh.entity.Quiz;
import com.yogesh.entity.Response;
import com.yogesh.feign.QuizInterface;
import com.yogesh.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

    //RestTemplate -  used to interact endpoint present in same system
    //open feign -
    //eureka discover(netflix) - to discover/search one microservice for another microservice
    //one microservice call the api present in another microservice using the service registry of EUREKA SERVER
    //all microservices are registered in EUREKA SERVER
    //SO no need ip address, port number
    public ResponseEntity<String> createQuiz(String category,Integer noOfQuestions,String title){
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,noOfQuestions).getBody();
        Quiz quiz= new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Quiz quiz= quizRepository.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionsForUser = quizInterface.getQuestionsFromId(questionIds);
        return questionsForUser;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
       ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}