package esgi.ascl.training.infastructure.web.controller;

import esgi.ascl.training.domain.mapper.TrainingMapper;
import esgi.ascl.training.domain.service.TrainingService;
import esgi.ascl.training.infastructure.web.request.TrainingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/training")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping()
    public ResponseEntity<?> createTraining(@RequestBody @NonNull TrainingRequest trainingRequest) {
        try {
            var training = trainingService.create(trainingRequest);
            return ResponseEntity.ok(TrainingMapper.entityToResponse(training));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTraining(@PathVariable Long id) {
        try {
            var training = trainingService.getById(id);
            return ResponseEntity.ok(
                    TrainingMapper.entityToResponse(
                            training
                    )
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTraining(@PathVariable Long id,
                                            @RequestBody @NonNull
                                            TrainingRequest trainingRequest) {

        try {
            trainingService.getById(id);
            var training = trainingService.update(trainingRequest ,id);
            return ResponseEntity.ok(TrainingMapper.entityToResponse(training));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllTrainings() {
        var trainingList = trainingService.getAll()
                .stream()
                .map(TrainingMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(trainingList);
    }


    @GetMapping("/category/id/{id}")
    public ResponseEntity<?> getAllTrainingsByCategoryId(@PathVariable Long id) {
        var trainingList = trainingService.getAllByCategoryId(id)
                .stream()
                .map(TrainingMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(trainingList);
    }
    @GetMapping("/category/name/{name}")
    public ResponseEntity<?> getAllTrainingsByCategoryName(@PathVariable String name) {
        var trainingList = trainingService.getAllByCategoryName(name)
                .stream()
                .map(TrainingMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(trainingList);
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<?> getAllTrainingsByDayOfRecurrence(@PathVariable String day) {
        var trainingList = trainingService.getAllByDayOfRecurrenceLevenshtein(day)
                .stream()
                .map(TrainingMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(trainingList);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            var training = trainingService.getById(id);
            trainingService.delete(training);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
