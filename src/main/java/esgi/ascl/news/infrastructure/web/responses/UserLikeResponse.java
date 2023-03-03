package esgi.ascl.news.infrastructure.web.responses;

import esgi.ascl.User.domain.entities.User;

public class UserLikeResponse {

    public Long id;
    public NewsResponse news;
    public User user;

    public Long getId() {
        return id;
    }
    public UserLikeResponse setId(Long id) {
        this.id = id;
        return this;
    }
    public NewsResponse getNews() {
        return news;
    }
    public UserLikeResponse setNews(NewsResponse news) {
        this.news = news;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserLikeResponse setUser(User user) {
        this.user = user;
        return this;
    }
}
