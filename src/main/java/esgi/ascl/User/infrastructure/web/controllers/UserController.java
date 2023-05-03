package esgi.ascl.User.infrastructure.web.controllers;

import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.User.infrastructure.web.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
        if(userService.getById(userRequest.getId()) == null) {
            return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
        }

        var userResponse = userService.update(userRequest);
        return new ResponseEntity<>(UserMapper.entityToResponse(userResponse), HttpStatus.OK);
    }
}
