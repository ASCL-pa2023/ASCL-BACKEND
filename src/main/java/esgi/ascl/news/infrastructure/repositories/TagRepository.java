package esgi.ascl.news.infrastructure.repositories;

import esgi.ascl.news.domain.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    List<TagEntity> findAllByName(String name);
    List<TagEntity> findAllByNewsId(Long newsId);

    void deleteAllByNewsId (Long newsId);
}
