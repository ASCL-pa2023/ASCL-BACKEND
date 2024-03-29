package esgi.ascl.news.domain.services;

import esgi.ascl.news.domain.entities.CommentEntity;
import esgi.ascl.news.domain.mapper.CommentMapper;
import esgi.ascl.news.infrastructure.repositories.CommentRepository;
import esgi.ascl.news.infrastructure.web.requests.CommentRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserLikeCommentService userLikeCommentService;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, UserLikeCommentService userLikeCommentService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userLikeCommentService = userLikeCommentService;
    }

    public CommentEntity create(CommentRequest commentRequest){
        var comment = commentMapper.requestToEntity(commentRequest);
        return commentRepository.save(comment);
    }

    public CommentEntity update(Long commentId, String content) {
        var commentToUpdate = commentRepository.findById(commentId).orElse(null);
        if(commentToUpdate == null) return null;

        commentToUpdate.setContent(content);

        return commentRepository.save(commentToUpdate);
    }

    public CommentEntity getById(Long id){
        return commentRepository.findById(id).orElse(null);
    }

    public List<CommentEntity> getAll(){
        return commentRepository.findAll();
    }

    public List<CommentEntity> getAllByUserId(Long userId){
        return commentRepository.findAllByUserId(userId);
    }
    public List<CommentEntity> getAllByNewsId(Long newsId){
        return commentRepository.findByNewsId(newsId);
    }

    public List<CommentEntity> getAllLikedByUserId(Long userId){
        var userLikeCommentEntities = userLikeCommentService.getAllByUserId(userId);
        var comments = new ArrayList<CommentEntity>();
        userLikeCommentEntities.forEach(userLike -> {
            var comment = commentRepository.findById(userLike.getComment().getId());
            comment.ifPresent(comments::add);
        });
        return comments;
    }
    @Transactional
    public void delete(CommentEntity commentEntity){
        commentEntity.setNews(null);
        commentEntity.setUser(null);
        userLikeCommentService.deleteAllByCommentId(commentEntity.getId());
        commentRepository.delete(commentEntity);
    }

    @Transactional
    public void deleteAllByNewsId(Long newsId){
        commentRepository.deleteAll(getAllByNewsId(newsId));
    }
}
