package mate.amazon.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;

import mate.amazon.entity.AmazonReviewEntity;
import mate.amazon.repository.AmazonRepository;
import mate.amazon.service.FindMostFrequentWordFromComments;
import mate.amazon.utils.CustomCsvParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@RestController
public class ReviewController {
    private final AmazonRepository amazonRepository;
    private final CustomCsvParser customCsvParser;
    private final FindMostFrequentWordFromComments findMostFrequentWordFromComments;

    public ReviewController(AmazonRepository amazonRepository, CustomCsvParser customCsvParser,
                            FindMostFrequentWordFromComments findMostFrequentWordFromComments) {
        this.amazonRepository = amazonRepository;
        this.customCsvParser = customCsvParser;
        this.findMostFrequentWordFromComments = findMostFrequentWordFromComments;
    }

    @GetMapping("/users")
    public List<String> getMostActiveUsers() {
        return amazonRepository.findActiveUsers(1000);
    }

    @GetMapping("/mostComments")
    public List<String> getMostCommentedGoods() {
        return amazonRepository.findMostCommentedGoods(1000);
    }

    @GetMapping("/popularWords")
    public List<String> getPopularWords() {
        return findMostFrequentWordFromComments.countWordsInString(
                amazonRepository.getAllComments());
    }

    @PostConstruct
    public void inject() {
        try {
            long startReading = System.currentTimeMillis();
            List<AmazonReviewEntity> reviews = customCsvParser.readCsvFile("Reviews.csv");
            LOGGER.info((System.currentTimeMillis() - startReading) * 0.001);
            long startSaving = System.currentTimeMillis();
            amazonRepository.saveAll(reviews);
            LOGGER.info((System.currentTimeMillis() - startSaving) * 0.001);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file", e);
        }
    }
}
