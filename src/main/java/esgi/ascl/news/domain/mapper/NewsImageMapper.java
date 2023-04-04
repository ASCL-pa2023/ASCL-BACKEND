package esgi.ascl.news.domain.mapper;

import esgi.ascl.news.domain.entities.NewsImageEntity;
import esgi.ascl.news.domain.services.NewsService;
import esgi.ascl.news.infrastructure.web.requests.NewsImageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NewsImageMapper {

    private final NewsService newsService;

    public NewsImageMapper(NewsService newsService) {
        this.newsService = newsService;
    }

    public NewsImageEntity requestToEntity(NewsImageRequest newsImageRequest) {
        return new NewsImageEntity()
                .setNews(newsService.getById(newsImageRequest.getNewsId()))
                .setFilename(newsImageRequest.getFilename())
                .setUrl(newsImageRequest.getUrl())
                .setCreationDate(new Date());
    }
}
