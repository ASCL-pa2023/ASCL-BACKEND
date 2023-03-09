package esgi.ascl.news.domain.services;

import esgi.ascl.news.domain.entities.NewsImageEntity;
import esgi.ascl.news.domain.mapper.NewsImageMapper;
import esgi.ascl.news.infrastructure.repositories.NewsImageRepository;
import esgi.ascl.news.infrastructure.web.requests.NewsImageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsImageService {

    private final NewsImageRepository newsImageRepository;
    private final NewsImageMapper newsImageMapper;

    public NewsImageService(NewsImageRepository newsImageRepository, NewsImageMapper newsImageMapper) {
        this.newsImageRepository = newsImageRepository;
        this.newsImageMapper = newsImageMapper;
    }

    public NewsImageEntity create(NewsImageRequest newsImageRequest) {
        return newsImageRepository.save(newsImageMapper.requestToEntity(newsImageRequest));
    }

    public List<NewsImageEntity> getAllByNewsId(Long newsId) {
        return newsImageRepository.findAllByNewsId(newsId);
    }

    public void deleteAllByNewsId(Long newsId) {
        newsImageRepository.removeAllByNewsId(newsId);
    }
}
