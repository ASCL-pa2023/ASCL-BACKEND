package esgi.ascl.news.domain.services;

import esgi.ascl.image.service.FileService;
import esgi.ascl.news.domain.entities.NewsImageEntity;
import esgi.ascl.news.domain.mapper.NewsImageMapper;
import esgi.ascl.news.infrastructure.repositories.NewsImageRepository;
import esgi.ascl.news.infrastructure.web.requests.NewsImageRequest;
import esgi.ascl.news.infrastructure.web.responses.ImageResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsImageService {

    private final NewsImageRepository newsImageRepository;
    private final NewsImageMapper newsImageMapper;
    private final FileService fileService;
    private final NewsService newsService;

    public NewsImageService(NewsImageRepository newsImageRepository, NewsImageMapper newsImageMapper, FileService fileService, NewsService newsService) {
        this.newsImageRepository = newsImageRepository;
        this.newsImageMapper = newsImageMapper;
        this.fileService = fileService;
        this.newsService = newsService;
    }

    private NewsImageEntity create(NewsImageRequest newsImageRequest) {
        var newsEntity = newsService.getById(newsImageRequest.newsId);
        return newsImageRepository.save(newsImageMapper.requestToEntity(newsImageRequest, newsEntity));
    }

    public List<NewsImageEntity> getAllByNewsId(Long newsId) {
        return newsImageRepository.findAllByNewsId(newsId);
    }

    public void deleteAllByNewsId(Long newsId) {
        var newsImageEntities = getAllByNewsId(newsId);
        if(newsImageEntities.isEmpty()) return;

        newsImageEntities.forEach(newsImage -> {
            newsImage.setNews(null);
            newsImageRepository.delete(newsImage);
            fileService.deleteFile(newsImage.getFilename());
        });
    }

    public void uploadImage(Long newsId, MultipartFile file) {
        var s3Object = fileService.putFile(file);

        //TODO : Une fois le bucket passer sur un compte payant ->
        //        Vérifier que l'URI mène bien à l'image

        var newsImageRequest = new NewsImageRequest()
                .setNewsId(newsId)
                .setFilename(s3Object.getKey())
                .setUrl(s3Object.getObjectContent().getHttpRequest().getURI().toString());

        create(newsImageRequest);
    }


    public List<ImageResponse> getImages(Long newsId){
        var newsImageEntities = newsImageRepository.findAllByNewsId(newsId);
        if(newsImageEntities.isEmpty()) return new ArrayList<>();

        var imageResponses = new ArrayList<ImageResponse>();

        newsImageEntities.forEach(newsImage -> {
            var imageResponse = newsImageMapper.entityToResponse(newsImage)
                    .setFile(fileService.getFile(newsImage.getFilename()));
            imageResponses.add(imageResponse);
        });

        return imageResponses;
    }
}
