package esgi.ascl.User.infrastructure.repositories;


import esgi.ascl.User.domain.entities.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {

    List<Follower> findAllByUserId(Long userId);

    List<Follower> findAllByFollowerId(Long followerId);
    Follower findByFollowerIdAndUserId(Long followerId, Long userId);
    Integer countAllByUserId(Long userId);
    Integer countAllByFollowerId(Long userId);
    void deleteByFollowerIdAndAndUserId(Long followerId, Long userId);



}
