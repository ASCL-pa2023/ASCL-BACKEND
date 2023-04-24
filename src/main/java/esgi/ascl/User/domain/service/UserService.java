package esgi.ascl.User.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.getUserById(id);
    }
    public Optional<User> getByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }
}
