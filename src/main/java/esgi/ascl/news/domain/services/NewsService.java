package esgi.ascl.news.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.User.domain.service.FollowerService;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.exceptions.TagExceptions;
import esgi.ascl.news.domain.mapper.NewsMapper;
import esgi.ascl.news.infrastructure.repositories.CommentRepository;
import esgi.ascl.news.infrastructure.repositories.NewsRepository;
import esgi.ascl.news.infrastructure.web.requests.NewsRequest;
import esgi.ascl.utils.Levenshtein;
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
    private final TagService tagService;
    private final FollowerService followerService;
    private final CommentRepository commentRepository;
    private final Levenshtein levenshtein = new Levenshtein();

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper, UserLikeService userLikeService, UserService userService, TagService tagService, FollowerService followerService, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.userLikeService = userLikeService;
        this.userService = userService;
        this.tagService = tagService;
        this.followerService = followerService;
        this.commentRepository = commentRepository;
    }

    public NewsEntity create(NewsRequest newsRequest) throws TagExceptions {
        var news = new NewsEntity();

        var tagNames = newsRequest.tagNames;

        if(!tagNames.isEmpty()){
            if (tagNames.size() > 3) throw new TagExceptions("You can't add more than 3 tags");
            tagNames.forEach(tagName -> {
                if (tagName.length() > 20) try {
                    throw new TagExceptions("Tag name can't be longer than 20 characters");
                } catch (TagExceptions e) {
                    throw new RuntimeException(e);
                }
            });

            news = newsRepository.save(newsMapper.requestToEntity(newsRequest));
            tagService.createAll(tagNames, news);
        } else {
            news = newsRepository.save(newsMapper.requestToEntity(newsRequest));
        }
        return news;
    }

    public NewsEntity getById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public List<NewsEntity> getAllByTitleLevenshtein(String title) {
        return getAll().stream()
                .filter(newsEntity -> levenshtein.calculate(newsEntity.getTitle().toUpperCase(), title.toUpperCase()) <= 3)
                .toList();
    }

    public List<NewsEntity> getAll() {
        return newsRepository.findAll();
    }

    public List<NewsEntity> getAllSubscribed(Long userId) {
        var subscriptions = followerService.getSubscriptionsByUserId(userId);
        var news = new ArrayList<NewsEntity>();
        subscriptions.forEach(s -> news.addAll(getAllByUserId(s.getUser().getId())));
        return news;
    }

    public List<NewsEntity> getAllByUserId(Long userId) {
        return newsRepository.findAllByUserId(userId);
    }

    public List<NewsEntity> getAllLikedByUserId(Long userId) {
        var userLikes = userLikeService.getAllByUserId(userId);
        var news = new ArrayList<NewsEntity>();
        userLikes.forEach(userLikeEntity -> {
            var newsEntity = getById(userLikeEntity.getNews().getId());
            news.add(newsEntity);
        });
        return news;
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
        commentRepository.findByNewsId(newsEntity.getId()).forEach(commentEntity -> {
            commentEntity.setUser(null);
            commentEntity.setNews(null);
            commentRepository.delete(commentEntity);
        });
        tagService.deleteAllByNewsId(newsEntity.getId());
        userLikeService.deleteAllByNewsId(newsEntity.getId());
        newsRepository.delete(newsEntity);
    }
}
