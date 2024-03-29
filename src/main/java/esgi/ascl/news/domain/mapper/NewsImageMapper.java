package esgi.ascl.news.domain.mapper;

import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.entities.NewsImageEntity;
import esgi.ascl.news.domain.services.NewsService;
import esgi.ascl.news.infrastructure.web.requests.NewsImageRequest;
import esgi.ascl.news.infrastructure.web.responses.ImageResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NewsImageMapper {

    public NewsImageEntity requestToEntity(NewsImageRequest newsImageRequest, NewsEntity newsEntity) {
        return new NewsImageEntity()
                .setNews(newsEntity)
                .setFilename(newsImageRequest.getFilename())
                .setUrl(newsImageRequest.getUrl())
                .setCreationDate(new Date());
    }

    public ImageResponse entityToResponse(NewsImageEntity newsImageEntity) {
        return new ImageResponse()
                .setId(newsImageEntity.getId())
                .setFilename(newsImageEntity.getFilename())
                .setNewsId(newsImageEntity.getNews().getId());
    }
}
