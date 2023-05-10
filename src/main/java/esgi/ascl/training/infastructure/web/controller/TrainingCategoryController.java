package esgi.ascl.training.infastructure.web.controller;

import esgi.ascl.training.domain.mapper.TrainingCategoryMapper;
import esgi.ascl.training.domain.service.TrainingCategoryService;
import esgi.ascl.training.infastructure.web.request.TrainingCategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/training-category")
public class TrainingCategoryController {
    private final TrainingCategoryService trainingCategoryService;

    public TrainingCategoryController(TrainingCategoryService trainingCategoryService) {
        this.trainingCategoryService = trainingCategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTrainingCategory(@RequestBody TrainingCategoryRequest trainingCategoryRequest) {
        System.out.println("/api/v1/training-category/create");

        if(trainingCategoryRequest == null)
            return ResponseEntity.badRequest().body("Missing Body");

        if(trainingCategoryRequest.getName() == null || trainingCategoryRequest.getName().isEmpty())
            return ResponseEntity.badRequest().body("Training category name is required");

        return ResponseEntity.ok("Training category created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainingCategory(@PathVariable long id) {
        System.out.println("/api/v1/training-category/" + id);

        if(id <= 0)
            return ResponseEntity.notFound().build();

        var trainingCategory = trainingCategoryService.getById(id);

        if (trainingCategory != null)
            return ResponseEntity.ok(
                    TrainingCategoryMapper.entityToResponse(
                            trainingCategory
                    )
            );

        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTrainingCategories() {
        System.out.println("/api/v1/training-category/all");

        try{
            var trainingCategoryList = trainingCategoryService.getAll();

            var trainingCategoryResponseList = TrainingCategoryMapper.entityListToResponseList(trainingCategoryList);

            return ResponseEntity.ok(trainingCategoryResponseList);

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTrainingCategory(@PathVariable long id, @RequestBody TrainingCategoryRequest trainingCategoryRequest) {
        System.out.println("/api/v1/training-category/update/{id}" + id);

        if(id <= 0)
            return ResponseEntity.notFound().build();

        if(trainingCategoryRequest == null)
            return ResponseEntity.badRequest().body("Missing Body");

        if(trainingCategoryRequest.getName() == null || trainingCategoryRequest.getName().isEmpty())
            return ResponseEntity.badRequest().body("Training category name is required");

        try{
            if(trainingCategoryService.update(id, trainingCategoryRequest) != null)
                return ResponseEntity.ok("Training category updated");
            else return ResponseEntity.badRequest().body("same information as before");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTrainingCategory(@PathVariable long id) {
        System.out.println("/api/v1/training-category/delete/{id}" + id);

        if(id <= 0)
            return ResponseEntity.notFound().build();

        try{
            if(trainingCategoryService.getById(id) == null)
                return ResponseEntity.notFound().build();

            trainingCategoryService.delete(id);
            return ResponseEntity.ok("Training category deleted");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
