package esgi.ascl.tournament.infrastructure.web.controller;

import esgi.ascl.tournament.domain.mapper.TournamentTypeMapper;
import esgi.ascl.tournament.domain.service.TournamentTypeService;
import esgi.ascl.tournament.infrastructure.web.request.TournamentTypeRequest;
import esgi.ascl.tournament.infrastructure.web.response.TournamentTypeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/tournament-types")
public class TournamentTypeController {
    private final TournamentTypeService tournamentTypeService;

    public TournamentTypeController(TournamentTypeService tournamentTypeService) {
        this.tournamentTypeService = tournamentTypeService;
    }

    @PostMapping()
    public ResponseEntity<TournamentTypeResponse> createTournamentType(@RequestBody TournamentTypeRequest tournamentTypeRequest) {
        System.out.println("/api/v1/tournament-category/create");

        if (tournamentTypeRequest.getName() == null || tournamentTypeRequest.getName().isEmpty())
            return ResponseEntity.badRequest().build();

        try {
            var createdTournamentType = tournamentTypeService.createTournamentType(tournamentTypeRequest);
            if (createdTournamentType == null)
                return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(
                    TournamentTypeMapper.entityToResponse(
                            createdTournamentType
                    )
            );
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentTypeResponse> getTournamentType(@PathVariable long id) {
        System.out.println("/api/v1/tournament-category/get/{id}" + id);

        if (id <= 0)
            return ResponseEntity.badRequest().build();
        try {
            var tournamentType = tournamentTypeService.getById(id);
            if (tournamentType == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(
                    TournamentTypeMapper.entityToResponse(
                            tournamentType
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTournamentType(@PathVariable long id, @RequestBody TournamentTypeRequest tournamentTypeRequest) {
        System.out.println("/api/v1/tournament-category/update/{id}" + id);

        if (id <= 0)
            return ResponseEntity.badRequest().build();

        try {
            var tournamentType = tournamentTypeService.getById(id);
            if (tournamentType == null)
                return ResponseEntity.notFound().build();
            var updatedValue =  tournamentTypeService.updateTournamentType(tournamentTypeRequest, id);
            return ResponseEntity.ok(TournamentTypeMapper.entityToResponse(updatedValue));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllTournamentType() {
        System.out.println("/api/v1/tournament-category/get/all");

        try {
            var tournamentTypeList = tournamentTypeService.getAll();
            if (tournamentTypeList == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(tournamentTypeList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTournamentType(@PathVariable long id) {
        System.out.println("/api/v1/tournament-category/delete/{id}" + id);

        if (id <= 0)
            return ResponseEntity.badRequest().build();

        try {
            var tournamentType = tournamentTypeService.getById(id);
            if (tournamentType == null)
                return ResponseEntity.notFound().build();
            tournamentTypeService.deleteTournamentType(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
