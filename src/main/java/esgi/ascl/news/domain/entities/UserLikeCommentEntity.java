package esgi.ascl.news.domain.entities;

import esgi.ascl.User.domain.entities.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_like_comment")
public class UserLikeCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CommentEntity comment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    public Long getId() {
        return id;
    }

    public UserLikeCommentEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public CommentEntity getComment() {
        return comment;
    }

    public UserLikeCommentEntity setComment(CommentEntity comment) {
        this.comment = comment;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserLikeCommentEntity setUser(User user) {
        this.user = user;
        return this;
    }
}
