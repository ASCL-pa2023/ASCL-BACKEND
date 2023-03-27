package esgi.ascl.news.infrastructure.web.controllers;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.mapper.CommentMapper;
import esgi.ascl.news.domain.mapper.NewsMapper;
import esgi.ascl.news.domain.services.CommentService;
import esgi.ascl.news.domain.services.NewsService;
import esgi.ascl.news.infrastructure.web.requests.CommentRequest;
import esgi.ascl.news.infrastructure.web.responses.CommentResponse;
import esgi.ascl.news.infrastructure.web.responses.NewsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final NewsService newsService;
    private final UserService userService;

    public CommentController(CommentService commentService, CommentMapper commentMapper, NewsService newsService, UserService userService) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.newsService = newsService;
        this.userService = userService;
    }


    @PostMapping()
    public ResponseEntity<?> comment(@RequestBody CommentRequest commentRequest){
        var user = userService.getById(commentRequest.getUserId());
        if (user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var news = newsService.getById(commentRequest.getNewsId());
        if (news == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        var comment = commentService.create(commentRequest);
        if(comment == null) return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(CommentMapper.entityToResponse(comment), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        var comment = commentService.getById(id);
        if(comment == null) {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(CommentMapper.entityToResponse(comment), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<CommentResponse>> getAll() {
        var commentResponses = commentService.getAll()
                .stream()
                .map(CommentMapper::entityToResponse)
                .collect(toList());
        return new ResponseEntity<>(commentResponses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable Long userId) {
        var user = commentService.getById(userId);
        if(user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        var commentResponses = commentService.getAllByUserId(userId)
                .stream()
                .map(CommentMapper::entityToResponse)
                .collect(toList());
        return new ResponseEntity<>(commentResponses, HttpStatus.OK);
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<?> getAllByNewsId(@PathVariable Long newsId) {
        var news = newsService.getById(newsId);
        if(news == null) return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);

        var commentResponses = commentService.getAllByNewsId(newsId)
                .stream()
                .map(CommentMapper::entityToResponse)
                .collect(toList());
        return new ResponseEntity<>(commentResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var comment = commentService.getById(id);
        if(comment == null) return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);

        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
