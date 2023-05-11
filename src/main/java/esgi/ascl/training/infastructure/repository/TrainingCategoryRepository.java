package esgi.ascl.training.infastructure.repository;

import esgi.ascl.training.domain.entitie.TrainingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingCategoryRepository extends JpaRepository<TrainingCategory, Long> {
    TrainingCategory getById(long id);

    TrainingCategory getByName(String name);
}
