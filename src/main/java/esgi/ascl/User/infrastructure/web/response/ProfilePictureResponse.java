package esgi.ascl.User.infrastructure.web.response;

import java.util.Date;

public class ProfilePictureResponse {
    public Long id;
    public String filename;
    public Long userId;
    public byte[] file;
    public Date creationDate;

    public Long getId() {
        return id;
    }

    public ProfilePictureResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public ProfilePictureResponse setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ProfilePictureResponse setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public ProfilePictureResponse setFile(byte[] file) {
        this.file = file;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public ProfilePictureResponse setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
