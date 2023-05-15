package esgi.ascl.news.infrastructure.web.controllers;

import esgi.ascl.news.domain.services.NewsImageService;
import esgi.ascl.news.domain.services.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/news/image")
public class NewsImageController {
    private final NewsService newsService;
    private final NewsImageService newsImageService;

    public NewsImageController(NewsService newsService, NewsImageService newsImageService) {
        this.newsService = newsService;
        this.newsImageService = newsImageService;
    }


    @PostMapping("/upload/{newsId}")
    public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile file,
                                        @PathVariable Long newsId) {
        if(newsService.getById(newsId) == null) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }

        newsImageService.uploadImage(newsId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/{newsId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long newsId) {
        if(newsService.getById(newsId) == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        var files = newsImageService.getImages(newsId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }
}
