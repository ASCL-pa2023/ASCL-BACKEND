package esgi.ascl.news.infrastructure.web.controllers;

import esgi.ascl.User.domain.mapper.UserMapper;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.exceptions.TagExceptions;
import esgi.ascl.news.domain.mapper.NewsMapper;
import esgi.ascl.news.domain.services.NewsImageService;
import esgi.ascl.news.domain.services.NewsService;
import esgi.ascl.news.domain.services.UserLikeService;
import esgi.ascl.news.infrastructure.web.requests.NewsRequest;
import esgi.ascl.news.infrastructure.web.requests.UserLikeRequest;
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
    private final NewsImageService newsImageService;
    private final UserService userService;
    private final UserLikeService userLikeService;
    private final NewsMapper newsMapper;

    public NewsController(NewsService newsService, NewsImageService newsImageService, UserService userService, UserLikeService userLikeService, NewsMapper newsMapper) {
        this.newsService = newsService;
        this.newsImageService = newsImageService;
        this.userService = userService;
        this.userLikeService = userLikeService;
        this.newsMapper = newsMapper;
    }


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody NewsRequest newsRequest) throws TagExceptions {
        var user = userService.getById(newsRequest.userId);
        if(user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        var news = newsService.create(newsRequest);
        return new ResponseEntity<>(newsMapper.entityToResponse(news), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        var news = newsService.getById(id);
        if(news == null) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsMapper.entityToResponse(news), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<NewsResponse>> getAll() {
        var newsResponses = newsService.getAll()
                .stream()
                .map(newsMapper::entityToResponse)
                .toList();
        return new ResponseEntity<>(newsResponses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable Long userId) {
        var user = userService.getById(userId);
        if(user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        var newsResponses = newsService.getAllByUserId(userId)
                .stream().map(newsMapper::entityToResponse);
        return new ResponseEntity<>(newsResponses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/liked")
    public ResponseEntity<?> getAllLikedByUserId(@PathVariable Long userId) {
        var user = userService.getById(userId);
        if(user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        var newsResponses = newsService.getAllLikedByUserId(userId)
                .stream()
                .map(newsMapper::entityToResponse);
        return new ResponseEntity<>(newsResponses, HttpStatus.OK);
    }


    @PutMapping("/{newsId}")
    public ResponseEntity<?> update(@PathVariable Long newsId, @RequestBody NewsRequest newsRequest) {
        //TODO : update tags
        var news = newsService.getById(newsId);
        if(news == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        var user = userService.getById(newsRequest.userId);
        if(user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var newsUpdated = newsService.update(newsId, newsRequest);
        return new ResponseEntity<>(newsMapper.entityToResponse(newsUpdated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var news = newsService.getById(id);
        if(news == null) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }
        newsService.delete(news);
        newsImageService.deleteAllByNewsId(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody UserLikeRequest userLikeRequest) {
        if(userLikeRequest.getUserId() == null || userLikeRequest.getNewsId() == null) {
            return ResponseEntity.badRequest().build();
        }

        var user = userService.getById(userLikeRequest.userId);
        if (user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var news = newsService.getById(userLikeRequest.newsId);
        if (news == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        var userAlreadyLike = userLikeService.getByUserIdAndNewsId(userLikeRequest);
        if(userAlreadyLike != null) return new ResponseEntity<>("User already like this news", HttpStatus.BAD_REQUEST);

        userLikeService.like(user, news);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(@RequestBody UserLikeRequest userLikeRequest) {
        if(userLikeRequest.getUserId() == null || userLikeRequest.getNewsId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (userService.getById(userLikeRequest.userId) == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        if (newsService.getById(userLikeRequest.newsId) == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        userLikeService.dislike(userLikeRequest);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{newsId}/userLiked")
    public ResponseEntity<?> userLiked(@PathVariable Long newsId) {
        var news = newsService.getById(newsId);
        if(news == null) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }
        var userLiked = newsService.getUserLiked(newsId)
                .stream()
                .map(UserMapper::entityToResponse)
                .collect(toList());
        return new ResponseEntity<>(userLiked, HttpStatus.OK);
    }

}
