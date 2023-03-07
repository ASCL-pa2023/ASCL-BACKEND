package esgi.ascl.User.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.getUserById(id);
    }
    public User getByEmail(String userEmail) {
        return null;
    }
}
