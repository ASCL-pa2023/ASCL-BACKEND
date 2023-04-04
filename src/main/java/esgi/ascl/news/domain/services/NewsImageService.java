package esgi.ascl.news.domain.services;

import esgi.ascl.image.service.FileService;
import esgi.ascl.news.domain.entities.NewsImageEntity;
import esgi.ascl.news.domain.mapper.NewsImageMapper;
import esgi.ascl.news.infrastructure.repositories.NewsImageRepository;
import esgi.ascl.news.infrastructure.web.requests.NewsImageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class NewsImageService {

    private final NewsImageRepository newsImageRepository;
    private final NewsImageMapper newsImageMapper;
    private final FileService fileService;

    public NewsImageService(NewsImageRepository newsImageRepository, NewsImageMapper newsImageMapper, FileService fileService) {
        this.newsImageRepository = newsImageRepository;
        this.newsImageMapper = newsImageMapper;
        this.fileService = fileService;
    }

    private NewsImageEntity create(NewsImageRequest newsImageRequest) {
        return newsImageRepository.save(newsImageMapper.requestToEntity(newsImageRequest));
    }

    public List<NewsImageEntity> getAllByNewsId(Long newsId) {
        return newsImageRepository.findAllByNewsId(newsId);
    }

    public void deleteAllByNewsId(Long newsId) {
        //TODO : Supprimer les images du bucket
        newsImageRepository.removeAllByNewsId(newsId);
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
}
