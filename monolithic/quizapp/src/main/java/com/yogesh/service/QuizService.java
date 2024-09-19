package com.yogesh.service;

import com.yogesh.entity.Question;
import com.yogesh.entity.QuestionWrapper;
import com.yogesh.entity.Quiz;
import com.yogesh.entity.Response;
import com.yogesh.repository.QuestionRepository;
import com.yogesh.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public String createQuiz(String category,int noOfQuestions,String title){
        List<Question> questions= questionRepository.findRandomQuestionsByCategory(category,noOfQuestions);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
        return "Success";

    }

    public List<QuestionWrapper> getQuizQuestions(Integer id) {

        Optional<Quiz> quiz= quizRepository.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q: questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(), q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }
        return questionsForUser;
    }

    public Integer calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizRepository.findById(id).get(); // get specific quiz object by using quiz id
        List<Question> questions = quiz.getQuestions(); //get question objects present in selected quiz id
        int correctAnswer=0;

        //note: List follows insertion order so question set and answer set are present in same index
        //list of responses->response obj ->response property
        //list of questions->question object ->rightAnswer property
        // check response == rightAnswer
        for (int i=0;i<responses.size();i++)
            if (responses.get(i).getResponse().equals(questions.get(i).getRightAnswer())) {
                correctAnswer++;
            }
        return correctAnswer;
    }
}