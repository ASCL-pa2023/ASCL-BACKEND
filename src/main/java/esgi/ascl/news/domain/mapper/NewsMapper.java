package esgi.ascl.news.domain.mapper;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.entities.TagEntity;
import esgi.ascl.news.infrastructure.web.requests.NewsRequest;
import esgi.ascl.news.infrastructure.web.responses.NewsResponse;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NewsMapper {

    private final UserService userService;

    public NewsMapper(UserService userService) {
        this.userService = userService;
    }

    public NewsEntity requestToEntity(NewsRequest newsRequest) {
        return new NewsEntity()
                .setUser(userService.getById(newsRequest.userId))
                .setTitle(newsRequest.title)
                .setContent(newsRequest.content)
                .setCreationDate(new Date());
    }

    public static NewsResponse entityToResponse(NewsEntity newsEntity) {
        return new NewsResponse()
                .setId(newsEntity.getId())
                .setUser(newsEntity.getUser())
                .setTitle(newsEntity.getTitle())
                .setContent(newsEntity.getContent())
                .setCreationDate(newsEntity.getCreationDate());
    }
}
