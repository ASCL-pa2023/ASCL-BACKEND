package esgi.ascl.news.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.news.domain.entities.NewsEntity;
import esgi.ascl.news.domain.entities.UserLikeEntity;
import esgi.ascl.news.infrastructure.repositories.UserLikeRepository;
import esgi.ascl.news.infrastructure.web.requests.UserLikeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLikeService {
    private final UserLikeRepository userLikeRepository;


    public UserLikeService(UserLikeRepository userLikeRepository) {
        this.userLikeRepository = userLikeRepository;
    }

    public List<UserLikeEntity> getAllByNewsId(Long newsId) {
        return userLikeRepository.findAllByNewsId(newsId);
    }

    public List<UserLikeEntity> getAllByUserId(Long userId) {
        return userLikeRepository.findAllByUserId(userId);
    }

    public UserLikeEntity getByUserIdAndNewsId(UserLikeRequest userLikeRequest) {
        return userLikeRepository.findByUserIdAndNewsId(userLikeRequest.userId, userLikeRequest.newsId);
    }

    public UserLikeEntity like(User user, NewsEntity newsEntity) {
        var like = new UserLikeEntity()
                .setUser(user)
                .setNews(newsEntity);
        return userLikeRepository.save(like);
    }

    public void dislike(UserLikeRequest userLikeRequest) {
        var userLikeEntity = userLikeRepository.findByUserIdAndNewsId(userLikeRequest.userId, userLikeRequest.newsId);
        userLikeEntity.setNews(null);
        userLikeEntity.setUser(null);
        userLikeRepository.delete(userLikeEntity);
    }
}
