package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.UserLikeCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikeCommentRepository extends JpaRepository<UserLikeCommentEntity, Long> {

    UserLikeCommentEntity findByUserIdAndCommentId(Long userId, Long commentId);
    List<UserLikeCommentEntity> findAllByCommentId(Long commentId);
    List<UserLikeCommentEntity> findAllByUserId(Long userId);
}
