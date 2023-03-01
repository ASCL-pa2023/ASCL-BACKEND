package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.UserLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLikeEntity, Integer> {
}
