package esgi.ascl.news.domain.mapper;

import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.entities.UserLikeEntity;
import esgi.ascl.news.domain.services.NewsService;
import esgi.ascl.news.infrastructure.web.requests.UserLikeRequest;
import esgi.ascl.news.infrastructure.web.responses.UserLikeResponse;
import org.springframework.stereotype.Component;

@Component
public class UserLikeMapper {

    private final UserService userService;
    private final NewsService newsService;

    public UserLikeMapper(UserService userService, NewsService newsService) {
        this.userService = userService;
        this.newsService = newsService;
    }

    public UserLikeEntity requestToEntity(UserLikeRequest userLikeRequest) {
        return new UserLikeEntity()
                .setUser(userService.getById(userLikeRequest.getUserId()))
                .setNews(newsService.getById(userLikeRequest.getNewsId()));
    }

    public static UserLikeResponse entityToResponse(UserLikeEntity userLikeEntity) {
        return new UserLikeResponse()
                .setId(userLikeEntity.getId())
                .setNews(NewsMapper.entityToResponse(userLikeEntity.getNews())
                .setUser(userLikeEntity.getUser()));
    }
}
