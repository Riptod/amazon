package mate.amazon.repository;

import mate.amazon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByName(String name);
}
