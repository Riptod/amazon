package mate.amazon.repository;

import java.util.List;

import mate.amazon.entity.AmazonReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AmazonRepository extends JpaRepository<AmazonReviewEntity, Long> {

    @Query(value = "SELECT profile_name FROM REVIEWS group by profile_name "
            + "order by count(profile_name) desc limit ?1", nativeQuery = true)
    List<String> findActiveUsers(Integer count);

    @Query(value = "SELECT product_id FROM REVIEWS group by product_id "
            + "order by count(product_id) desc limit ?1", nativeQuery = true)
    List<String> findMostCommentedGoods(Integer count);
}
