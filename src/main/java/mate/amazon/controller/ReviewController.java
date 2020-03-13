package mate.amazon.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;

import mate.amazon.entity.AmazonReviewEntity;
import mate.amazon.service.ReviewService;
import mate.amazon.utils.CustomCsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CustomCsvParser customCsvParser;

    @GetMapping("/users")
    public List<String> getMostActiveUsers() {
        return reviewService.findActiveUsers(1000);
    }

    @GetMapping("/mostComments")
    public List<String> getMostCommentedGoods() {
        return reviewService.findMostCommentedGoods(1000);
    }

    @GetMapping("/popularWords")
    public List<String> getPopularWords() {
        return reviewService.countWordsInString(reviewService.getAllComments());
    }

    @PostConstruct
    public void inject() {
        try {
            long startReading = System.currentTimeMillis();
            List<AmazonReviewEntity> reviews = customCsvParser.readCsvFile("Reviews.csv");
            LOGGER.info((System.currentTimeMillis() - startReading) * 0.001);
            long startSaving = System.currentTimeMillis();
            reviewService.saveAll(reviews);
            LOGGER.info((System.currentTimeMillis() - startSaving) * 0.001);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file", e);
        }
    }
}
