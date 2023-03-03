package esgi.ascl.news.domain.services;

import esgi.ascl.news.domain.entities.UserLikeEntity;
import esgi.ascl.news.domain.mapper.UserLikeMapper;
import esgi.ascl.news.infrastructure.repositories.UserLikeRepository;
import esgi.ascl.news.infrastructure.web.requests.UserLikeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLikeService {
    private final UserLikeRepository userLikeRepository;
    private final UserLikeMapper userLikeMapper;


    public UserLikeService(UserLikeRepository userLikeRepository, UserLikeMapper userLikeMapper) {
        this.userLikeRepository = userLikeRepository;
        this.userLikeMapper = userLikeMapper;
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

    public UserLikeEntity like(UserLikeRequest userLikeRequest) {
        return userLikeRepository.save(
                userLikeMapper.requestToEntity(userLikeRequest)
        );
    }

    public void dislike(UserLikeRequest userLikeRequest) {
        var userLikeEntity = userLikeRepository.findByUserIdAndNewsId(userLikeRequest.userId, userLikeRequest.newsId);
        userLikeRepository.delete(userLikeEntity);
    }
}
