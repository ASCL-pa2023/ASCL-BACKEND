package esgi.ascl.User.infrastructure.web.request;

public class ProfilePictureRequest {
    public Long userId;
    public String filename;

    public Long getUserId() {
        return userId;
    }

    public ProfilePictureRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public ProfilePictureRequest setFilename(String filename) {
        this.filename = filename;
        return this;
    }
}
