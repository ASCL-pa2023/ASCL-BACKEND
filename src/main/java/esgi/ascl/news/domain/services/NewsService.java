package esgi.ascl.news.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.mapper.NewsMapper;
import esgi.ascl.news.infrastructure.repositories.NewsRepository;
import esgi.ascl.news.infrastructure.web.requests.NewsRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserLikeService userLikeService;
    private final UserService userService;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper, UserLikeService userLikeService, UserService userService) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.userLikeService = userLikeService;
        this.userService = userService;
    }

    public NewsEntity create(NewsRequest newsRequest) {
        var user = newsMapper.requestToEntity(newsRequest);
        return newsRepository.save(user);
    }

    public NewsEntity getById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public List<NewsEntity> getAll() {
        return newsRepository.findAll();
    }

    public List<NewsEntity> getAllByUserId(Long userId) {
        return newsRepository.findAllByUserId(userId);
    }

    public List<User> getUserLiked(Long newsId){
        var userLikes = userLikeService.getAllByNewsId(newsId);
        
        var users = new ArrayList<User>();
        userLikes.forEach(userLikeEntity -> {
            var user = userService.getById(userLikeEntity.getUser().getId());
            users.add(user);
        });
        return users;
    }

    public NewsEntity update(Long newsId, NewsRequest newsRequest) {
        var news = getById(newsId);
        var newsUpdated = getById(newsId)
                .setTitle(newsRequest.getTitle() == null ? news.getTitle() : newsRequest.getTitle())
                .setContent(newsRequest.getContent() == null ? news.getContent() : newsRequest.getContent());
        return newsRepository.save(newsUpdated);
    }

    @Transactional
    public void delete(NewsEntity newsEntity) {
        newsEntity.setUser(null);
        newsRepository.delete(newsEntity);
    }
}
