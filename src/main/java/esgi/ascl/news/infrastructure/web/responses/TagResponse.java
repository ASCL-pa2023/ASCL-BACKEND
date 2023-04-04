package esgi.ascl.news.infrastructure.web.responses;

public class TagResponse {
    public Long id;
    private String name;
    public Long postId;

    public Long getId() {
        return id;
    }

    public TagResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TagResponse setName(String name) {
        this.name = name;
        return this;
    }

    public Long getPostId() {
        return postId;
    }

    public TagResponse setPostId(Long postId) {
        this.postId = postId;
        return this;
    }
}
