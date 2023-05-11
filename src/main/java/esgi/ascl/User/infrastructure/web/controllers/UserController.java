package esgi.ascl.User.infrastructure.web.controllers;

import esgi.ascl.User.domain.mapper.ProfilePictureMapper;
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

    @GetMapping("mail/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        var user = userService.getByEmail(email);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(UserMapper.entityToResponse(user.get()));
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
