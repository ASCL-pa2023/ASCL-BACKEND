package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.NewsImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsImageRepository extends JpaRepository<NewsImageEntity, Integer> {

    List<NewsImageEntity> findAllByNewsId(Long newsId);
    void removeAllByNewsId(Long newsId);
}
