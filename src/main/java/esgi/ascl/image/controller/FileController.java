package esgi.ascl.image.controller;

import esgi.ascl.image.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/buckets")
    public ResponseEntity<?> getAllBucket() {
        return ResponseEntity.ok(fileService.getAllBucket());
    }

    @GetMapping("/{filename}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        var file = fileService.OLDgetFile(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile file) {
        fileService.putFile(file);
        return ResponseEntity.ok().build();
    }
}
