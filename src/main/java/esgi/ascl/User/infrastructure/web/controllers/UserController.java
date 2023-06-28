package esgi.ascl.User.infrastructure.web.controllers;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.exceptions.UserNotFoundExceptions;
import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.User.domain.service.ProfilePictureService;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.User.infrastructure.web.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final ProfilePictureService profilePictureService;

    public UserController(UserService userService, ProfilePictureService profilePictureService) {
        this.userService = userService;
        this.profilePictureService = profilePictureService;
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<?> getById(@PathVariable Long userId) {
        User user;
        try {
            user = userService.getById(userId);
        } catch (UserNotFoundExceptions e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(UserMapper.entityToResponse(user));
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        var users = userService.getAll()
                .stream()
                .map(UserMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("firstname/{firstname}")
    public ResponseEntity<?> getByFirstname(@PathVariable String firstname) {
        var users = userService.getByUsernameLevenshtein(firstname)
                .stream()
                .map(UserMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("mail/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        var user = userService.getByEmail(email);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(UserMapper.entityToResponse(user.get()));
    }

    @GetMapping("dashboard")
    public ResponseEntity<?> getUsersDashboard() {
        var users = userService.getUsersDashboard()
                .stream()
                .map(UserMapper::dashboardUserResponse)
                .toList();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("dashboard/firstname/{firstname}")
    public ResponseEntity<?> getUsersDashboardByFirstname(@PathVariable String firstname) {
        var users = userService.getDashboardByUsernameLevenshtein(firstname)
                .stream()
                .map(UserMapper::dashboardUserResponse)
                .toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
        if(userService.getById(userRequest.getId()) == null) {
            return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
        }

        var userResponse = userService.update(userRequest);
        return new ResponseEntity<>(UserMapper.entityToResponse(userResponse), HttpStatus.OK);
    }


    @PostMapping("{userId}/profilePicture/upload")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Long userId, @RequestParam("image") MultipartFile file) {
        if(userService.getById(userId) == null) {
            return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
        }

        profilePictureService.upload(userId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{userId}/profilePicture/download")
    public ResponseEntity<?> downloadProfilePicture(@PathVariable Long userId) {
        if(userService.getById(userId) == null) {
            return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
        }

        var profilePictureResponse = profilePictureService.download(userId);
        if (profilePictureResponse == null) return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(profilePictureService.download(userId), HttpStatus.OK);
    }

    @DeleteMapping("{userId}/profilePicture/delete")
    public ResponseEntity<?> deleteProfilePicture(@PathVariable Long userId) {
        if(userService.getById(userId) == null) {
            return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
        }

        if(profilePictureService.getByUserId(userId) == null) {
            return new ResponseEntity<>("User " + userId + " don't have profile picture", HttpStatus.NO_CONTENT);
        }

        profilePictureService.delete(userId);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }

}
