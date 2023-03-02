package esgi.ascl.news.domain.services;

import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.infrastructure.repositories.NewsRepository;
import esgi.ascl.news.infrastructure.web.requests.NewsRequest;
import jdk.jshell.spi.ExecutionControl;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public NewsEntity create(NewsRequest newsRequest) {
        throw new NotYetImplementedException();
    }
}
