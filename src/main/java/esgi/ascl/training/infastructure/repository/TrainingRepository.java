package esgi.ascl.training.infastructure.repository;

import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    Training getById(long id);

    List<Training> getByDate(Date date);

    List<Training> getByTrainingCategory(TrainingCategory trainingCategory);

    List<Training> getByTrainingCategoryAndDate(TrainingCategory trainingCategory, Date date);

    List<Training> findAllByRecurrenceTraining(Training training);
}
