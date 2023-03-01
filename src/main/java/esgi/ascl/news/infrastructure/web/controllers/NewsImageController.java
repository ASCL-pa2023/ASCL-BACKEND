package esgi.ascl.news.infrastructure.web.controllers;

import esgi.ascl.news.domain.services.NewsImageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/news/image")
public class NewsImageController {
    private final NewsImageService newsImageService;

    public NewsImageController(NewsImageService newsImageService) {
        this.newsImageService = newsImageService;
    }
}
