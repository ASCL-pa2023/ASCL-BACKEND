package esgi.ascl.news.domain.mapper;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.services.TagService;
import esgi.ascl.news.infrastructure.web.requests.NewsRequest;
import esgi.ascl.news.infrastructure.web.responses.NewsResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NewsMapper {

    private final UserService userService;
    private final TagService tagService;

    public NewsMapper(UserService userService, TagService tagService) {
        this.userService = userService;
        this.tagService = tagService;
    }

    public NewsEntity requestToEntity(NewsRequest newsRequest) {
        return new NewsEntity()
                .setUser(userService.getById(newsRequest.userId))
                .setTitle(newsRequest.title)
                .setContent(newsRequest.content)
                .setCreationDate(new Date());
    }

    public NewsResponse entityToResponse(NewsEntity newsEntity) {
        var tags = tagService.getAllByNewsId(newsEntity.getId());
        return new NewsResponse()
                .setId(newsEntity.getId())
                .setUser(newsEntity.getUser())
                .setTitle(newsEntity.getTitle())
                .setContent(newsEntity.getContent())
                .setTags(
                        tags == null ?
                                new ArrayList<>() :
                                tags.stream().map(TagMapper::entityToResponse).toList()
                )
                .setCreationDate(newsEntity.getCreationDate());
    }

}
