package esgi.ascl.news.domain.mapper;

import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.infrastructure.web.requests.NewsRequest;
import esgi.ascl.news.infrastructure.web.responses.NewsResponse;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {
    public static NewsEntity requestToEntity(NewsRequest newsRequest) {
        throw new NotYetImplementedException();
    }

    public static NewsRequest entityToRequest(NewsEntity newsEntity) {
        throw new NotYetImplementedException();
    }

    public static NewsResponse entityToResponse(NewsEntity newsEntity) {
        throw new NotYetImplementedException();
    }
}
