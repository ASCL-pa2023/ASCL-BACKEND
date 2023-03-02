package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByUserId(Long id);
    List<CommentEntity> findByNewsId(Long id);
}
