package mate.amazon.service;

import mate.amazon.entity.User;

public interface UserService {
    User saveUser(User user);

    User getUserByName(String name);
}
