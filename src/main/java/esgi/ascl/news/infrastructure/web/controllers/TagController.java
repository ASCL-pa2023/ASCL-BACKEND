package esgi.ascl.news.infrastructure.web.controllers;

import esgi.ascl.news.domain.mapper.TagMapper;
import esgi.ascl.news.domain.services.NewsService;
import esgi.ascl.news.domain.services.TagService;
import esgi.ascl.news.infrastructure.web.requests.TagRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    private final TagService tagService;
    private final NewsService newsService;

    public TagController(TagService tagService, NewsService newsService) {
        this.tagService = tagService;
        this.newsService = newsService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TagRequest tagRequest) {
        var news = newsService.getById(tagRequest.newsId);
        if(news == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        var tag = tagService.create(tagRequest.name, news);
        return new ResponseEntity<>(TagMapper.entityToResponse(tag), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        var tags = tagService.getAll()
                .stream()
                .map(TagMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        var tag = tagService.getById(id);
        if(tag == null) return new ResponseEntity<>("Tag not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(TagMapper.entityToResponse(tag), HttpStatus.OK);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<?> getAllByName(@PathVariable String name) {
        var tags = tagService.getAllByName(name)
                .stream()
                .map(TagMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }


    @GetMapping("/news/{id}")
    public ResponseEntity<?> getAllByNewsId(@PathVariable Long id) {
        var news = newsService.getById(id);
        if(news == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        var tags = tagService.getAllByNewsId(id)
                .stream()
                .map(TagMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteAllByNewsId(@PathVariable Long id) {
        var news = newsService.getById(id);
        if(news == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        tagService.deleteAllByNewsId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
