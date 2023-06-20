package esgi.ascl.training.infastructure.web.controller;

import esgi.ascl.training.domain.mapper.TrainingMapper;
import esgi.ascl.training.domain.service.TrainingService;
import esgi.ascl.training.infastructure.web.request.TrainingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            trainingService.getById(id);
            trainingService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
