package esgi.ascl.news.infrastructure.web.responses;

public class ImageResponse {
    public Long id;
    public String filename;
    public Long newsId;
    public byte[] file;

    public Long getId() {
        return id;
    }

    public ImageResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public ImageResponse setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Long getNewsId() {
        return newsId;
    }

    public ImageResponse setNewsId(Long newsId) {
        this.newsId = newsId;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public ImageResponse setFile(byte[] file) {
        this.file = file;
        return this;
    }
}
