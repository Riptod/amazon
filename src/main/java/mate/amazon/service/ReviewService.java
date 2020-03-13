package mate.amazon.service;

import java.util.List;

import mate.amazon.entity.AmazonReviewEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    List<String> findMostUsedWords(Integer count);

    List<String> findActiveUsers(Integer count);

    List<String> findMostCommentedGoods(Integer count);

    List<String> getAllComments();

    void saveAll(List<AmazonReviewEntity> reviewEntities);
}
