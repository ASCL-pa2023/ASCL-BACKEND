package esgi.ascl.news.domain.services;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.news.domain.entities.CommentEntity;
import esgi.ascl.news.domain.entities.UserLikeCommentEntity;
import esgi.ascl.news.infrastructure.repositories.UserLikeCommentRepository;
import esgi.ascl.news.infrastructure.web.requests.UserLikeCommentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLikeCommentService {

    private final UserLikeCommentRepository userLikeCommentRepository;

    public UserLikeCommentService(UserLikeCommentRepository userLikeCommentRepository) {
        this.userLikeCommentRepository = userLikeCommentRepository;
    }

    public List<UserLikeCommentEntity> getAllByCommentId(Long commentId) {
        return userLikeCommentRepository.findAllByCommentId(commentId);
    }

    public List<UserLikeCommentEntity> getAllByUserId(Long userId) {
        return userLikeCommentRepository.findAllByUserId(userId);
    }

    public UserLikeCommentEntity getByUserIdAndCommentId(UserLikeCommentRequest userLikeCommentRequest) {
        return userLikeCommentRepository.findByUserIdAndCommentId(userLikeCommentRequest.userId, userLikeCommentRequest.commentId);
    }

    public UserLikeCommentEntity like(User user, CommentEntity commentEntity) {
        var like = new UserLikeCommentEntity()
                .setUser(user)
                .setComment(commentEntity);
        return userLikeCommentRepository.save(like);
    }

    public void dislike(UserLikeCommentRequest userLikeCommentRequest) {
        var userLikeCommentEntity = userLikeCommentRepository.findByUserIdAndCommentId(userLikeCommentRequest.userId, userLikeCommentRequest.commentId);
        userLikeCommentEntity.setComment(null);
        userLikeCommentEntity.setUser(null);
        userLikeCommentRepository.delete(userLikeCommentEntity);
    }
}
