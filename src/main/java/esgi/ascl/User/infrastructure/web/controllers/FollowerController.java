package esgi.ascl.User.infrastructure.web.controllers;

import esgi.ascl.User.domain.entities.Follower;
import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.User.domain.service.FollowerService;
import esgi.ascl.User.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/followers")
public class FollowerController {

    private final UserService userService;
    private final FollowerService followerService;

    public FollowerController(UserService userService, FollowerService followerService) {
        this.userService = userService;
        this.followerService = followerService;
    }

    /**
     * User1 follow the user2
     * @param idUser1 : Id of the user who wants to follow the user 2
     * @param idUser2 : id user
     */
    @PostMapping("{idUser1}/follow/{idUser2}")
    public ResponseEntity<?> follow(@PathVariable Long idUser1, @PathVariable Long idUser2){
        var user1 = userService.getById(idUser1);

        var user2 = userService.getById(idUser2);


        var alreadyFollow = followerService.getByFollowerAndUser(user1, user2);
        if(alreadyFollow != null) return new ResponseEntity<>("You already follow this user", HttpStatus.FORBIDDEN);

        followerService.follow(user1, user2);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * User1 unfollow the user2
     * @param idUser1 : Id of the user who wants to unfollow the user 2
     * @param idUser2 : id user
     */
    @PostMapping("{idUser1}/unfollow/{idUser2}")
    public ResponseEntity<?> unfollow(@PathVariable Long idUser1, @PathVariable Long idUser2){
        try {
            var user1 = userService.getById(idUser1);

            var user2 = userService.getById(idUser2);

            followerService.unfollow(user1, user2);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("{userId}/followers")
    public ResponseEntity<?> getAllFollowers(@PathVariable Long userId){

        System.out.println("Dans la route {userId}/followers");
        try {
            userService.getById(userId);

            var followers = followerService.getAllByUserId(userId)
                    .stream()
                    .map(Follower::getFollower)
                    .map(UserMapper::entityToResponse)
                    .toList();
            return new ResponseEntity<>(followers, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("{userId}/subscriptions")
    public ResponseEntity<?> getAllSubscriptions(@PathVariable Long userId){
        try {
            userService.getById(userId);

            var subscriptions = followerService.getSubscriptionsByUserId(userId)
                    .stream()
                    .map(Follower::getUser)
                    .map(UserMapper::entityToResponse)
                    .toList();

            return new ResponseEntity<>(subscriptions, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("{userId}/isFollowed/{userId2}")
    public ResponseEntity<?> isFollow(@PathVariable Long userId, @PathVariable Long userId2){
        try {
            userService.getById(userId);
            userService.getById(userId2);

            var isFollow = followerService.getByFollowerAndUser(userService.getById(userId), userService.getById(userId2));

            return new ResponseEntity<>(isFollow != null, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
