package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    List<NewsEntity> findAllByUserId(Long userId);
}
