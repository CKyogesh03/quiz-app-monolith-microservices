package com.yogesh.controller;

import com.yogesh.entity.Question;
import com.yogesh.entity.QuestionWrapper;
import com.yogesh.entity.Response;
import com.yogesh.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int noOfQuestions,@RequestParam String title){
        return new ResponseEntity<>(quizService.createQuiz(category,noOfQuestions,title), HttpStatus.CREATED);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return new ResponseEntity<>(quizService.getQuizQuestions(id),HttpStatus.OK);
    }

    // id - quiz id, which quiz set is answered by the user
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return new ResponseEntity<>(quizService.calculateResult(id,responses),HttpStatus.OK);
    }
}