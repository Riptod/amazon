package mate.amazon.controller;

import java.util.List;
import mate.amazon.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    private static final Logger LOGGER = LogManager.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

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
        return reviewService.findMostUsedWords(1000);
    }

}
