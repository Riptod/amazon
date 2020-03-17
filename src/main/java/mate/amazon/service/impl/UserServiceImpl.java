package mate.amazon.service.impl;

import mate.amazon.entity.User;
import mate.amazon.repository.UserRepository;
import mate.amazon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findUserByName(name);
    }
}
