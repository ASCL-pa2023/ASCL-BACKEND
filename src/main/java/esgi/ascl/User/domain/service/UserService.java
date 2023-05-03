package esgi.ascl.User.domain.service;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.infrastructure.repositories.UserRepository;
import esgi.ascl.User.infrastructure.web.request.UserRequest;
import esgi.ascl.User.infrastructure.web.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    public User update(UserRequest userRequest){
        var user = userRepository.getUserById(userRequest.getId());
        user
            .setEmail(Objects.equals(userRequest.getEmail(), "") ? user.getEmail() : userRequest.getEmail())
            .setPhone(Objects.equals(userRequest.getPhone(), "") ? user.getPhone() : userRequest.getPhone())
            .setFirstname(Objects.equals(userRequest.getFirstname(), "") ? user.getFirstname() : userRequest.getFirstname())
            .setLastname(Objects.equals(userRequest.getLastname(), "") ? user.getLastname() : userRequest.getLastname())
            .setBio(Objects.equals(userRequest.getBio(), "") ? user.getBio() : userRequest.getBio())
            .setLicense(Objects.equals(userRequest.getLicense(), "") ? user.getLicense() : userRequest.getLicense())
            .setBirthday(userRequest.getBirthday() == null ? user.getBirthday() : userRequest.getBirthday())
            .setProfilePicture(Objects.equals(userRequest.getProfilePicture(), "") ? user.getProfilePicture() : userRequest.getProfilePicture());

        return userRepository.save(user);
    }
}
