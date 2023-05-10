package esgi.ascl.training.infastructure.web.controller;

import esgi.ascl.training.domain.mapper.TrainingMapper;
import esgi.ascl.training.domain.service.TrainingService;
import esgi.ascl.training.infastructure.web.request.TrainingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/training")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTraining(@RequestBody TrainingRequest trainingRequest) {
        System.out.println("/api/v1/training/create");

        if(trainingRequest == null)
            return ResponseEntity.badRequest().body("Missing Body");

        if(trainingRequest.getTimeSlot() == null || trainingRequest.getTimeSlot().isEmpty() ||
            trainingRequest.getTrainingCategoryId() <= 0 || trainingRequest.getDate() == null)
            return ResponseEntity.badRequest().body("Missing required fields");

        var training = trainingService.create(trainingRequest);
        if(training == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(TrainingMapper.entityToResponse(training));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTraining(@PathVariable long id) {
        System.out.println("/api/v1/training/" + id);

        if(id <= 0)
            return ResponseEntity.notFound().build();

        var training = trainingService.getById(id);

        if (training != null)
            return ResponseEntity.ok(
                    TrainingMapper.entityToResponse(
                            training
                    )
            );

        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateTraining(@PathVariable long id, @RequestBody TrainingRequest trainingRequest) {
        System.out.println("/api/v1/training/update/id"+ id);

        if(trainingRequest == null)
            return ResponseEntity.badRequest().body("Missing Body");

        if(trainingRequest.getId() <= 0)
            return ResponseEntity.badRequest().body("Missing required fields");

        var training = trainingService.update(trainingRequest ,id);
        if(training == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(TrainingMapper.entityToResponse(training));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTrainings() {
        System.out.println("/api/v1/training/all");

        try{
            var trainingList = trainingService.getAll();

            var trainingResponseList = TrainingMapper.entityListToResponseList(trainingList);

            return ResponseEntity.ok(trainingResponseList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
