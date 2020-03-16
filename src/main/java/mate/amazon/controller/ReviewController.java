package mate.amazon.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import mate.amazon.entity.AmazonReviewEntity;
import mate.amazon.entity.Role;
import mate.amazon.entity.User;
import mate.amazon.service.ReviewService;
import mate.amazon.service.RoleService;
import mate.amazon.service.UserService;
import mate.amazon.utils.CustomCsvParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    private static final Logger LOGGER = LogManager.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CustomCsvParser customCsvParser;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleService.saveRole(adminRole);
        roleService.saveRole(userRole);

        User newUser = new User();
        newUser.setName("Bob");
        newUser.setPassword(passwordEncoder.encode("123"));
        newUser.getRoles().add(userRole);
        userService.saveUser(newUser);

        User newAdminUser = new User();
        newAdminUser.setName("Admin");
        newAdminUser.setPassword(passwordEncoder.encode("123"));
        newAdminUser.getRoles().add(adminRole);
        userService.saveUser(newAdminUser);
    }
}
