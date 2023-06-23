package esgi.ascl.User.domain.service;

import esgi.ascl.User.domain.entities.Follower;
import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.infrastructure.repositories.FollowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class FollowerService {

    private final FollowerRepository followerRepository;

    public FollowerService(FollowerRepository followerRepository, UserService userService) {
        this.followerRepository = followerRepository;
    }

    public List<Follower> getAllByUserId(Long userId){
        return followerRepository.findAllByUserId(userId);
    }

    public Follower getByFollowerAndUser(User follower, User user){
        return followerRepository.findByFollowerIdAndUserId(follower.getId(), user.getId());
    }

    public List<Follower> getSubscriptionsByUserId(Long userId){
        return followerRepository.findAllByFollowerId(userId);
    }

    public void follow(User follower, User user){
        var followLink = new Follower()
                .setUser(user)
                .setFollower(follower);
        followerRepository.save(followLink);
    }

    @Transactional
    public void unfollow(User follower, User user){
        followerRepository.deleteByFollowerIdAndAndUserId(follower.getId(), user.getId());
    }

}
