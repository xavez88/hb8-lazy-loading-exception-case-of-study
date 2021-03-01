package usr.ach.lazyloading.service;

import org.springframework.stereotype.Service;
import usr.ach.lazyloading.model.User;
import usr.ach.lazyloading.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
