package esgi.ascl.training.infastructure.repository;

import esgi.ascl.training.domain.entitie.Training;
import esgi.ascl.training.domain.entitie.TrainingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    @Query("SELECT t FROM Training t WHERE t.recurrenceTraining = :training AND t.date > :date")
    List<Training> findAllByRecurrenceTrainingAndDateAfter(@Param("training") Training training,
                                                           @Param("date")LocalDate date);
}
