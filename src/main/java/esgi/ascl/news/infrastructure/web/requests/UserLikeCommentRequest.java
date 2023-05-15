package esgi.ascl.news.infrastructure.web.requests;

public class UserLikeCommentRequest {
    public Long userId;
    public Long commentId;

    public Long getUserId() {
        return userId;
    }

    public UserLikeCommentRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getCommentId() {
        return commentId;
    }

    public UserLikeCommentRequest setCommentId(Long commentId) {
        this.commentId = commentId;
        return this;
    }
}
