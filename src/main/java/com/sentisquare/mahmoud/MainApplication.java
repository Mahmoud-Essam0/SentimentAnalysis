package com.sentisquare.mahmoud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.sentisquare.mahmoud.NaiveBayesClassifier.naiveBayesClassifier;


@SpringBootApplication
@RestController
public class MainApplication {
    static final String filePath = System.getProperty("user.dir") + "\\src\\train.csv";

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @GetMapping("/api")
    public NaiveBayesClassifier getText(@RequestParam String text) {
        return new NaiveBayesClassifier(naiveBayesClassifier(filePath, text));
    }

}