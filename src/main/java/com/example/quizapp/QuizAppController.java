package com.example.quizapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuizAppController {
    private List<Quiz> quizzes = new ArrayList<>();
    private  QuizFileDao quizFileDao = new QuizFileDao();

    @GetMapping("/show")
    public List<Quiz> show() {
        return quizzes;
    }

    @PostMapping("/create")
    public void create(@RequestParam String question, @RequestParam boolean answer) {
        Quiz quiz  = new Quiz(question, answer);
        quizzes.add(quiz);
    }
    @GetMapping("/check")
    public String check(String question, boolean answer) {
        for (Quiz quiz: quizzes) {
            if (quiz.getQuestion().equals(question)) {
                if (quiz.isAnswer() == answer) {
                    return "正解";
                } else {
                    return "不正解";
                }
            }
        }
        return  "Not Found";
    }
    @PostMapping
    public String save() {
        try {
            quizFileDao.write(quizzes);
            return "OK!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failer";
        }
    }
}
