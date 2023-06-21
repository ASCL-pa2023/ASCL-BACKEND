package esgi.ascl.training.infastructure.repository;

import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    Training getById(long id);
    List<Training> findAllByDate(Date date);
    List<Training> findAllByTrainingCategoryId(Long trainingCategoryId);
    List<Training> findAllByTrainingCategoryName(String name);
    List<Training> findAllByDayOfRecurrence(DayOfWeek dayOfWeek);
    List<Training> getByTrainingCategoryAndDate(TrainingCategory trainingCategory, Date date);
    List<Training> findAllByRecurrenceTraining(Training training);
}
