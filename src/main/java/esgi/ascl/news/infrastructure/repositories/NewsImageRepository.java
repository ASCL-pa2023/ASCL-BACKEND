package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.NewsImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsImageRepository extends JpaRepository<NewsImageEntity, Integer> {
}
