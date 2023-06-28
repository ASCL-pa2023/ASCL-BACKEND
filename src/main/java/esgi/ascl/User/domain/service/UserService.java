package esgi.ascl.User.domain.service;

import esgi.ascl.User.domain.entities.DashboardUser;
import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.exceptions.UserNotFoundExceptions;
import esgi.ascl.User.infrastructure.repositories.FollowerRepository;
import esgi.ascl.User.infrastructure.repositories.UserRepository;
import esgi.ascl.User.infrastructure.web.request.UserRequest;
import esgi.ascl.license.infrastructure.repositories.LicenseRepository;
import esgi.ascl.news.infrastructure.repositories.NewsRepository;
import esgi.ascl.utils.Levenshtein;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final FollowerRepository followerRepository;
    private final LicenseRepository licenseRepository;
    private final Levenshtein levenshtein = new Levenshtein();

    public UserService(UserRepository userRepository, NewsRepository newsRepository, FollowerRepository followerRepository, LicenseRepository licenseRepository) {
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
        this.followerRepository = followerRepository;
        this.licenseRepository = licenseRepository;
    }

    public User getById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundExceptions("User not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public User update(UserRequest userRequest){
        var user = userRepository.getUserById(userRequest.getId());
        user
            .setEmail(
                    Objects.equals(userRequest.getEmail(), "") || userRequest.getEmail() == null?
                    user.getEmail()
                    : userRequest.getEmail()
            )
            .setPhone(
                    Objects.equals(userRequest.getPhone(), "") || userRequest.getPhone() == null ?
                            user.getPhone() :
                            userRequest.getPhone()
            )
            .setFirstname(
                    Objects.equals(userRequest.getFirstname(), "") || userRequest.getFirstname() == null ?
                            user.getFirstname() :
                            userRequest.getFirstname()
            )
            .setLastname(
                    Objects.equals(userRequest.getLastname(), "") || userRequest.getLastname() == null ?
                            user.getLastname() :
                            userRequest.getLastname()
            )
            .setBio(
                    Objects.equals(userRequest.getBio(), "") || userRequest.getBio() == null ?
                            user.getBio() :
                            userRequest.getBio()
            )
            .setBirthday(
                    userRequest.getBirthday() == null ?
                            user.getBirthday() :
                            userRequest.getBirthday()
            )
            .setProfilePicture(
                    Objects.equals(userRequest.getProfilePicture(), "") || userRequest.getProfilePicture() == null ?
                            user.getProfilePicture() :
                            userRequest.getProfilePicture()
            );

        return userRepository.save(user);
    }

    public List<DashboardUser> getUsersDashboard() {
        return  userRepository.findAll().stream().map(user ->
                new DashboardUser(
                    user.getId(),
                    user.getFirstname(),
                    user.getFirstname(),
                    user.getRole(),
                    followerRepository.findAllByUserId(user.getId()).size(),
                    newsRepository.findAllByUserId(user.getId()).size(),
                    licenseRepository.findByUserId(user.getId())
                )
        ).toList();
    }
}
