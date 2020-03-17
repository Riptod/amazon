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
import org.springframework.stereotype.Component;

@Component
public class InjectController {
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
