package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.UserLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLikeEntity, Long> {
    UserLikeEntity findByUserIdAndNewsId(Long userId, Long newsId);
    List<UserLikeEntity> findAllByNewsId(Long newsId);
    List<UserLikeEntity> findAllByUserId(Long userId);
}
