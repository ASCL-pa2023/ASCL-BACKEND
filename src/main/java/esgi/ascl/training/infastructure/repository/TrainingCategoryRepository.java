package esgi.ascl.training.infastructure.repository;

import esgi.ascl.training.domain.entitie.TrainingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingCategoryRepository extends JpaRepository<TrainingCategory, Long> {
    Optional<TrainingCategory> findByName(String name);
}
