package mate.amazon.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;

import mate.amazon.entity.AmazonReviewEntity;
import mate.amazon.repository.AmazonRepository;
import mate.amazon.utils.CustomCsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
    @Autowired
    private AmazonRepository amazonRepository;

    @Autowired
    private CustomCsvParser customCsvParser;

    @GetMapping("/users")
    public List<String> getMostActiveUsers() {
        return amazonRepository.findActiveUsers(1000);
    }

    @GetMapping("/mostComments")
    public List<String> getMostCommentedGoods() {
        return amazonRepository.findMostCommentedGoods(1000);
    }

    @PostConstruct
    public void inject() {
        try {
            List<AmazonReviewEntity> reviews = customCsvParser.parseCsvFile("Reviews.csv");
            long startSaving = System.currentTimeMillis();
            amazonRepository.saveAll(reviews);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file", e);
        }
    }
}
