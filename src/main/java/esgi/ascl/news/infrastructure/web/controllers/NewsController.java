package esgi.ascl.news.infrastructure.web.controllers;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.mapper.NewsMapper;
import esgi.ascl.news.domain.services.NewsService;
import esgi.ascl.news.infrastructure.web.requests.NewsRequest;
import esgi.ascl.news.infrastructure.web.responses.NewsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;

    public NewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody NewsRequest newsRequest) {
        var user = userService.getById(newsRequest.userId);
        if(user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        var news = newsService.create(newsRequest);
        return new ResponseEntity<>(NewsMapper.entityToResponse(news), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        var news = newsService.getById(id);
        if(news == null) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(NewsMapper.entityToResponse(news), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<NewsResponse>> getAll() {
        var newsResponses = newsService.getAll()
                .stream()
                .map(NewsMapper::entityToResponse)
                .collect(toList());
        return new ResponseEntity<>(newsResponses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable Long userId) {
        var user = userService.getById(userId);
        if(user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        var newsResponses = newsService.getAllByUserId(userId)
                .stream()
                .map(NewsMapper::entityToResponse)
                .collect(toList());
        return new ResponseEntity<>(newsResponses, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long newsId, @RequestBody NewsRequest newsRequest, @PathVariable String id) {
        var news = newsService.getById(newsId);
        if(news == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        var user = userService.getById(newsRequest.userId);
        if(user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var newsUpdated = newsService.update(newsId, newsRequest);
        return new ResponseEntity<>(NewsMapper.entityToResponse(newsUpdated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var news = newsService.getById(id);
        if(news == null) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }
        newsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
