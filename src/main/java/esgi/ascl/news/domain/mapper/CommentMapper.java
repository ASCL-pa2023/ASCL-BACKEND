package esgi.ascl.news.domain.mapper;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.entities.CommentEntity;
import esgi.ascl.news.domain.services.NewsService;
import esgi.ascl.news.infrastructure.web.requests.CommentRequest;
import esgi.ascl.news.infrastructure.web.responses.CommentResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentMapper {

    private final UserService userService;
    private final NewsService newsService;

    public CommentMapper(UserService userService, NewsService newsService) {
        this.userService = userService;
        this.newsService = newsService;
    }


    public CommentEntity requestToEntity(CommentRequest commentRequest){
        return new CommentEntity()
                .setUser(userService.getById(commentRequest.userId))
                .setNews(newsService.getById(commentRequest.newsId))
                .setContent(commentRequest.content)
                .setCreationDate(new Date());
    }


    public static CommentResponse entityToResponse(CommentEntity commentEntity){
        return new CommentResponse()
                .setId(commentEntity.getId())
                .setContent(commentEntity.getContent())
                .setUser(commentEntity.getUser())
                .setNewsId(commentEntity.getNews().getId())
                .setCreationDate(commentEntity.getCreationDate());
    }
}
