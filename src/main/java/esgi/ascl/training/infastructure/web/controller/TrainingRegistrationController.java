package esgi.ascl.training.infastructure.web.controller;


import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.training.domain.mapper.TrainingRegistrationMapper;
import esgi.ascl.training.domain.service.TrainingRegistrationService;
import esgi.ascl.training.domain.service.TrainingService;
import esgi.ascl.training.infastructure.web.request.TrainingRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@Controller
@RequestMapping("/api/v1/training-registration")
public class TrainingRegistrationController {
    private final TrainingRegistrationService trainingRegistrationService;
    private final TrainingService trainingService;
    private final UserService userService;

    public TrainingRegistrationController(TrainingRegistrationService trainingRegistrationService, TrainingService trainingService, UserService userService) {
        this.trainingRegistrationService = trainingRegistrationService;
        this.trainingService = trainingService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> registration(@RequestBody @NonNull
                                          TrainingRegistrationRequest trainingRegistrationRequest) {
        try {
            var training = trainingService.getById(trainingRegistrationRequest.getTrainingId());
            var user = userService.getById(trainingRegistrationRequest.getUserId());
            var trainingRegistration = trainingRegistrationService.registration(training, user);

            return ResponseEntity.ok(TrainingRegistrationMapper.toResponse(trainingRegistration));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            var trainingRegistration = trainingRegistrationService.getById(id);
            return ResponseEntity.ok(TrainingRegistrationMapper.toResponse(trainingRegistration));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        var trainingRegistrations = trainingRegistrationService.getAll()
                .stream()
                .map(TrainingRegistrationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(trainingRegistrations);
    }

    @GetMapping("/training/{trainingId}")
    public ResponseEntity<?> getAllByTrainingId(@PathVariable Long trainingId) {
        try {
            trainingService.getById(trainingId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var trainingRegistrations = trainingRegistrationService.getAllByTrainingId(trainingId)
                .stream()
                .map(TrainingRegistrationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(trainingRegistrations);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<?> getAllByPlayerId(@PathVariable Long playerId) {
        try {
            userService.getById(playerId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        var trainingRegistrations = trainingRegistrationService.getAllByPlayerId(playerId)
                .stream()
                .map(TrainingRegistrationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(trainingRegistrations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            var trainingRegistration = trainingRegistrationService.getById(id);
            trainingRegistrationService.deleteById(trainingRegistration);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
